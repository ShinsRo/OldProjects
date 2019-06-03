import React, { Component } from "react";
import HeaderContainer from './containers/HeaderContainer';
import DatePicker from "react-datepicker";
import { Card } from "./components/Card";
import Chart from 'chart.js';
import axios from 'axios';
import { BASE_URL } from "../../supports/API_CONSTANT";

const QUERY_OPS = {
  ON_STDATE: 0x01,
  GROUPED: 0x02,
}

class ProjectInfoPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      polarLabel: [ '폐기', '대기', '접수', '진행', '보류', '완료'],

      queryType: '',
      dateType: '',

      from: new Date(),
      to: new Date(),

      infoHeader: [],
      infos: [],
      queryOps: 0,
    }
    
    this.onFromChange = this.onFromChange.bind(this);
    this.onToChange = this.onToChange.bind(this);
    this.onQuery = this.onQuery.bind(this);
    this.onTypeChange = this.onTypeChange.bind(this);
    this.getQueryOps = this.getQueryOps.bind(this);
    this.progressBar = this.progressBar.bind(this);
    this.deleteOne = this.deleteOne.bind(this);
  }

  onFromChange(date) {
    const { to } = this.state;
    if ( date.getTime() > to.getTime() ) { alert('조회일 형식이 올바르지 않습니다.'); return; }
    this.setState({ from: date });
  }
  onToChange(date) {
    const { from } = this.state;
    
    if ( date.getTime() < from.getTime() ) { alert('조회일 형식이 올바르지 않습니다.'); return; }
    this.setState({ to: date });
  }

  onTypeChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  getQueryOps() {
    const strQueryOps = this.state.queryType + this.state.dateType;
    return strQueryOps;
  }

  deleteOne(info, idx) {
    axios.post(`${BASE_URL}/api/projectInfo/deleteOne`, {
      queryOps: this.state.queryOps,
      id: info.pid || info.mpid
    }).then((res) => {
      const { infos } = this.state;
      infos.remove(idx);
      this.setState({ infos });
    }).catch((err) => {
      
    });
  }

  onQuery(e) {
    const lineChartCtx = window.$("#lineChart");
    const polarChartCtx = window.$("#polarChart");
    const queryOps = this.getQueryOps();
    const complexity = {};
    const { from, to } = this.state;

    if (queryOps.length < 2) {
      return alert("옵션을 선택하세요.");
    }

    this.setState({ queryOps });

    axios.post(`${BASE_URL}/api/projectInfo/fetch`, {
      queryOps,
      from,
      to,
    }).then(res => {
      let infoHeader = [];
      let { projectInfoDtos, queryOps } = res.data;
      
      const pstatCnt = {"폐기": 0, "대기": 0, "접수": 0, "진행": 0, "보류": 0, "완료": 0};
      
      let infosCnt = 0;
      let totalAvg = 0;
      const infos = projectInfoDtos.map(dto => {
        const info = { ...dto, ...dto.pdto, dflag: `${(dto.dflag)? 'Y': 'N'}` };
        pstatCnt[dto.pdto.pstat] += 1;
        totalAvg += Number(dto.progressAvg);
        
        infosCnt += 1;
        let key = "";
        if (queryOps & QUERY_OPS.ON_STDATE) {
          key = info.stDate.split("T")[0];
        } else {
          key = info.edDate.split("T")[0];
        }
        complexity[key] = (complexity[key])? complexity[key] + 1 : 1;
        
        return info;
      });
      
      if (infosCnt === 0) { return alert("조회 결과가 없습니다."); }
      if (queryOps & QUERY_OPS.GROUPED) {   // 프로젝트 기준 조회
        const percent = totalAvg / infosCnt;
        // const percent = 70;
        infoHeader = [
          ["ID", "pid", 1],
          ["프로젝트 명", "pname", 4],
          ["평균 진행도", "progressAvg", 2],
          ["참여인 수", "mcnt", 1],
          ["시작일", "stDate", 2],
          ["마감일", "edDate", 2],
          ["생성일", "cdate", 2],
          ["최근수정", "udate", 2],
          ["삭제 여부", "dflag", 1],
        ];

        new Chart(polarChartCtx, {
          data: {
            datasets: [{
              data: [percent, 100 - percent],
              backgroundColor: [
                'rgba(255, 159, 64, 0.8)',
                'rgba(0, 0, 0, 0.2)',
              ],
            }],
            labels: [
              '전체 평균 진행율',
              '나머지',
            ],
          },
          type: 'pie',
          options: {}
        })

      } else {                              // 개인별 조회
        infoHeader = [
          ["ID", "mpid", 1],
          ["프로젝트 명", "pname", 4],
          ["진행도", "progress", 2],
          ["담당자", "mname", 1],
          ["권한", "prole", 1],
          ["상태", "pstat", 1],
          ["시작일", "stDate", 2],
          ["마감일", "edDate", 2],
          ["생성일", "cdate", 2],
          ["최근수정", "udate", 2],
        ];
        
        const pstatData = {
          labels: this.state.polarLabel,
          datasets: [{
            data: [pstatCnt.폐기, pstatCnt.대기, pstatCnt.접수, pstatCnt.진행, pstatCnt.보류, pstatCnt.완료],
            backgroundColor: [
              'rgba(0, 0, 0, 0.4)',
              'rgba(75, 192, 192, 0.8)',
              'rgba(54, 162, 235, 0.8)',
              'rgba(255, 159, 64, 0.8)',
              'rgba(153, 102, 255, 0.8)',
              'rgba(80, 255, 80, 0.8)',
            ],
          }],
        }
        
        new Chart(polarChartCtx, {
          data: pstatData,
          type: 'polarArea',
          options: {}
        })
      }
      
      const complexityDataOps = {
        showLines: false,
        maintainAspectRatio: false,
        layout: {
          padding: {
            left: 10,
            right: 25,
            top: 25,
            bottom: 0
          }
        },
        scales: {
          xAxes: [{
              type: 'time',
              time: {
                  unit: 'day'
              }
          }]
        }
      }
  
      const complexityData = [];
      Object.keys(complexity).forEach(key => {
        complexityData.push({x: new Date(key), y: complexity[key]});
      });
      console.log(complexityData);
      
  
      new Chart(lineChartCtx, {
        data: {
          datasets: [{
            data: complexityData.sort(),
            label: "중첩도",
            backgroundColor: 'rgba(255, 159, 64, 0.8)',
            pointBackgroundColor: 'rgba(255, 159, 64, 0.8)',
          }]
        },
        type: 'line',
        options: complexityDataOps
      });


      this.setState({ infoHeader });
      this.setState({ infos });
      // table.DataTable();
    });
    // table.DataTable();
  }

  progressBar(progress) {
    return (<>
      <div className="progress">
        <div className="progress-bar progress-bar-striped" role="progressbar" style={{ width: `${progress}%` }} aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
      </div>
    </>);
  }

  componentDidMount() {
  }

  componentDidUpdate() {
    const { infos } = this.state;
    const $table = window.$("#dataTable");
    
    if($table && infos.length) {
      console.log($table);
      
      console.log($table);
    }
    
  }

  render() {
    const { infos, infoHeader } = this.state;
    
    let chartPanel1 = (
      <Card
        shadow = {true}
        header = "기간별 프로젝트 중첩도"
        content = {(
          <div className="chart-area">
            <canvas id="lineChart"></canvas>
          </div>
        )}
      />
    );
    let chartPanel2 = (
      <Card
        shadow = {true}
        header = "프로젝트 상태 비율"
        content = {(<>
          <div className="chart-pie pt-4 pb-2">
            <canvas id="polarChart"></canvas>
          </div>
          <div className="mt-4 text-center small">
              &nbsp;
          </div>
        </>)}
      />
    );

    if (this.state.queryOps & QUERY_OPS.GROUPED) {
      chartPanel1 = (
        <Card
          shadow = {true}
          header = "기간별 프로젝트 중첩도"
          content = {(
            <div className="chart-area">
              <canvas id="lineChart"></canvas>
            </div>
          )}
        />
      );

      chartPanel2 = (
        <Card
          shadow = {true}
          header = "전체 평균 진행율"
          content = {(<>
            <div className="chart-pie pt-4 pb-2">
              <canvas id="pieChart"></canvas>
            </div>
            <div className="mt-4 text-center small">
                &nbsp;
            </div>
          </>)}
        />
      );
    }

    return (
      <div id="wrapper">
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            {/* Page Content  */}
            <HeaderContainer history={this.props.history} />
            <div className="container-fluid" style={{ minHeight: '90vh' }}>
          
              {/* Row-main 0번 */}
              <div className="row mb-4">
                <div className="col">
                  <Card
                    shadow = {true}
                    header = "조회 옵션"
                    content = {(<>
                      <div className="row">
                        <div className="col">
                          <Card
                            content = 
                            {(<div className="row">
                              <div className="col-3 text-right font-weight-bold text-dark-1">기준일<br/>제한옵션</div>
                              <div className="col">
                              <input name="dateType" type="radio" value="0" onChange={this.onTypeChange}/> 프로젝트 마감일 기준 조회<br/>
                              <input name="dateType" type="radio" value="1" onChange={this.onTypeChange}/> 프로젝트 시작일 기준 조회<br/>
                              </div>
                            </div>)}
                          />
                        </div>
                        <div className="col">
                          <Card
                            content = 
                            {(<div className="row">
                              <div className="col-3 text-right font-weight-bold text-dark-1" style={{ overflowY: "visible" }}>조회<br/>기준일</div>
                              <div className="col">
                                <div className="row">
                                  <div className="col-8">
                                    <DatePicker
                                      className="form-control date-picker mb-1"
                                      selected={this.state.from}
                                      dateFormat="yyyy년 MM월 dd일"
                                      selectsStart
                                      startDate={this.state.from}
                                      endDate={this.state.to}
                                      onChange={this.onFromChange}
                                      placeholderText="날짜를"
                                      required
                                    />
                                  </div>
                                  &nbsp; <div className="text-black"> ~</div>
                                </div>
                                <div className="row">
                                  <div className="col-8">
                                    <DatePicker
                                      className="form-control date-picker" 
                                      selected={this.state.to}
                                      dateFormat="yyyy년 MM월 dd일"
                                      selectsEnd
                                      startDate={this.state.from}
                                      endDate={this.state.to}
                                      onChange={this.onToChange}
                                      placeholderText="선택하세요."
                                      required
                                    />
                                  </div>
                                </div>
                              </div>
                            </div>)}
                          />
                        </div>
                        <div className="col">
                          <Card
                            content = 
                            {(<div className="row">
                              <div className="col-3 text-right font-weight-bold text-dark-1">조회<br/>구분옵션</div>
                              <div className="col">
                                <input name="queryType" type="radio" value="0" onChange={this.onTypeChange}/> 개인 기준 조회<br/>
                                <input name="queryType" type="radio" value="1" onChange={this.onTypeChange}/> 프로젝트 기준 조회<br/>
                              </div>
                            </div>)}
                          />
                        </div>
                        <div className="col">
                          <Card
                            content = 
                            {(<div className="row">
                              <div className="col-3 text-right font-weight-bold text-dark-1">기타<br/>제한옵션</div>
                              <div className="col">
                                <div className="row">
                                  -
                                </div>
                              </div>
                            </div>)}
                          />
                        </div>
                        <div className="col-1 text-right">
                          <button className="btn btn-dark-1" style={{ height: "100%" }} onClick={this.onQuery}>조회하기</button>
                        </div>
                      </div>
                    </>)}
                  />
                </div>
              </div>

              {/* Row-main 1번 */}
              <div className="row mb-4">

                {/* <!-- Area Chart --> */}
                <div className="col-xl-8 col-lg-7">
                  {chartPanel1}
                </div>

                {/* <!-- Pie Chart --> */}
                <div className="col-xl-4 col-lg-5">
                  {chartPanel2}
                </div>

              </div>

              {/* Row-main 2번 */}
              <div className="row">
                <div className="col-auto">
                  <Card
                    shadow = {true}
                    header = "프로젝트 리스트"
                    content = {(<>
                      <div className="table-responsive" style={{ overflowX: "hidden" }}>
                        <table className="table text-black" id="dataTable" width="100%" cellSpacing="0">
                          <thead className="text-dark-1">
                            <tr>
                              {infoHeader.map((hd, idx) => {
                                
                                return (<th key={idx} colSpan={`${hd[2]}`}>{hd[0]}</th>);
                              })}
                              <th>완전삭제</th>
                            </tr>
                          </thead>
                          <tbody>
                            {infos.map((info, idx) => {
                              return (
                                <tr key={idx}>
                                  {infoHeader.map((hd, jdx) => {
                                    if(hd[1] === 'progressAvg' || hd[1] === 'progress') {
                                      return (
                                      <td key={jdx} colSpan={`${hd[2]}`}>
                                        {this.progressBar(info[hd[1]])}
                                      </td>);
                                    }

                                    return (<td key={jdx} colSpan={`${hd[2]}`}>{info[hd[1]]}</td>);
                                  })}
                                  <td><button className="btn-sm btn-danger sm-text">삭제하기</button></td>
                                </tr>
                              );
                            })}
                          </tbody>
                        </table>
                      </div>
                    </>)}
                  />
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    );
  }
};
export default ProjectInfoPage;