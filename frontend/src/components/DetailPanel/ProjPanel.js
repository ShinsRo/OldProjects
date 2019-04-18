import React, { Component } from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class ProjPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stDate: null,
            edDate: null,
            progress: 0,
            gubun: 'project',
        }
        this.onStartDateChange = this.onStartDateChange.bind(this);
        this.onEndDateChange = this.onEndDateChange.bind(this);
        this.onProgressChange = this.onProgressChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    onStartDateChange(date) {
        this.setState({ stDate: new Date(date) });
    }
    onEndDateChange(date) {
        this.setState({ edDate: new Date(date) });
    }
    onProgressChange(e) {
        this.setState({ progress: e.target.value });
    }

    handleSubmit(e) {
        e.preventDefault();
        // const data = new FormData(e.target);
        // const pDto = {};
        // data.forEach((value, key) => {
        //     if (key === 'stDate' || key === 'edDate') {
        //         pDto[key] = new Date(value).toISOString();
        //         alert("key: " + key + " /// value :" + pDto[key])
        //     } else {
        //         pDto[key] = value;
        //     }
        // });

        // register(pDto).then((res) => {
        //     console.log(JSON.parse(res));
            
        //     alert('ok');
        // });
    }

    dateFormater(arrDate) {
        const year = arrDate[0];
        const month = (arrDate[1] < 10) ? '0' + arrDate[1]:arrDate[1];
        const day = (arrDate[2] < 10) ? '0' + arrDate[2]:arrDate[2];

        return `${year}년 ${month}월 ${day}일`;
    }

    render() {
        const { projectState } = this.props;
        const selectedDirId = projectState.get('selectedDirId');
        const dirContainer = projectState.get("dirContainer");
        const pid = dirContainer.treeMap[selectedDirId].pid;
        const project = dirContainer.projectMap[pid];
        console.log('>>>', project);
        const strStDate = this.dateFormater(project.stDate);
        const strEdDate = this.dateFormater(project.edDate);
        return (<>
            <form>
                <div className="card-header py-3">
                    <div className="row">
                        <div className="m-0 font-weight-bold text-darkblue col-10">
                            <span 
                                name="pname" 
                                suppressContentEditableWarning={true} 
                                contentEditable="true"
                            >{project.pname}</span>
                            
                        </div>
                        <div className="text-right col-2 pt-1">
                            <div 
                                className="dropdown"
                                data-toggle="dropdown" role="button" 
                                aria-haspopup="true" 
                                aria-expanded="false"><span className="fas fa-ellipsis-h"></span></div>
                            <div className="dropdown-menu">
                            <div className="dropdown-item" href="#">삭제하기</div>
                            <div className="dropdown-divider"></div>
                            <div className="dropdown-item" href="#">팀원 추가하기</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="card-body">
                    <div className="form-group row mb-3">
                        <div className="col-2">
                        <input type="text" class="form-control sm-text" value={`권한 : ${project.prole}`} disabled/>
                        </div>
                        <div className="col-2">
                            <select name="pstat" id="pstat" className="form-control sm-text" style={{ width: '100%' }}>
                                <option value="">상태</option>
                                <option value="대기">대기</option>
                                <option value="접수">접수</option>
                                <option value="진행">진행</option>
                                <option value="할당">할당</option>
                                <option value="완료">완료</option>
                                <option value="보류">보류</option>
                                <option value="폐기">폐기</option>
                            </select>
                        </div>
                        <div className="col-2"></div>
                        <div className="col-1 text-right pr-0">
                            <span className="fas fa-calendar-week pr-1" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                        </div>
                        <div className="col-2">
                            <DatePicker
                                className=" form-control sm-input sm-text text-right"
                                selected={this.state.stDate}
                                dateFormat="yyyy년 MM월 dd일"
                                selectsStart
                                startDate={this.state.stDate}
                                endDate={this.state.edDate}
                                onChange={this.onStartDateChange}
                                placeholderText={strStDate}
                            />
                        </div>
                
                        <div className="col-1 text-right pr-0">
                            <span className="fas fa-calendar-check pr-1" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                        </div>
                        <div className="col-2">
                            <DatePicker
                                className="sm-input form-control te`xt-right" 
                                dateFormat="yyyy년 MM월 dd일"
                                selected={this.state.edDate}
                                selectsEnd
                                startDate={this.state.stDate}
                                endDate={this.state.edDate}
                                onChange={this.onEndDateChange}
                                placeholderText={strEdDate}
                            />
                        </div>
                    </div>
                    <hr></hr>
                    <div className="row mt-3">
                        <div className="col">
                            {((prole) => {
                                if (
                                    prole === '관리자' ||
                                    prole === '책임자' ||
                                    prole === ''
                                ) 
                                    return (<textarea name="description" id={project.pid} className="form-control" placeholder={project.description} style = {{ minHeight: '200px' }}></textarea>)
                                else
                                    return (<textarea name="description" id={project.pid} className="form-control" placeholder={project.description} disabled></textarea>)

                            })(project.prole)}
                        </div>
                    </div>
                    <div className="row mt-3">
                        <div className="col text-right">
                            <input className="btn btn-primary" type="submit" value="수정하기"></input>
                        </div>
                    </div>
                </div>
            </form>
        </>);
    }
}
export default ProjPanel;