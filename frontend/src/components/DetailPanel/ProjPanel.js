import React, { Component } from 'react';
import { correct, disable } from '../../stores/modules/projectState';
import stores from '../../stores';
import Collaborators from '../Collaborators';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class ProjPanel extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            stDate: new Date(),
            edDate: new Date(),
            progress: 0,
            gubun: 'project',
        }
        this.onStartDateChange = this.onStartDateChange.bind(this);
        this.onEndDateChange = this.onEndDateChange.bind(this);
        this.onProgressChange = this.onProgressChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.disable = this.disable.bind(this);
        // this.progressColor = this.progressColor.bind(this);
    }

    disable(e, project) {
        if (!window.confirm("정말 삭제하시겠습니까?")) {
            return;
        }
        disable(project).then(res => {
            
        }).catch(err => {

        });
        window.location.href = "/";
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

    onFormFieldChange(e, project) {
        if (project.isOrigin) {
            const { projectState } = this.props;
            const dirContainer = projectState.get("dirContainer");
            dirContainer.tempProjectData = {...project};
            project.isOrigin = false;
        }
        project[e.target.name] = e.target.value;
        this.setState({progress: this.state.progress + 1})
    }

    handleSubmit(e) {
        e.preventDefault();
        const data = new FormData(e.target);
        const pDto = {};
        data.forEach((value, key) => {
            if (key === 'stDate' || key === 'edDate') {
                pDto[key] = new Date(value).toISOString();
            } else {
                pDto[key] = value;
            }
        });

        correct(pDto).then((res) => {
            window.location.href = "/";
        });
    }

    dateFormater(arrDate) {
        if (arrDate === null) {
            return '';
        }
        const year = arrDate[0];
        const month = (arrDate[1] < 10) ? '0' + arrDate[1]:arrDate[1];
        const day = (arrDate[2] < 10) ? '0' + arrDate[2]:arrDate[2];

        return `${year}년 ${month}월 ${day}일`;
    }

    /**
     * 진행률은 사장님 요구사항이나, 그 기준이 모호하고 추상적이므로
     * 정확한 의미가 다시 부여되지 않는 한 포함하지 않는다.
     * 단, 추후 지시가 있을 수 있으므로 코드를 남겨놓는다.
     */
    // progressColor(progress) {
    //     const toInt = Number(progress);

    //     if (toInt === 100) {
    //         return 'bg-darkblue';
    //     } else if (toInt >= 70){
    //         return 'bg-success';
    //     } else if (toInt >= 40){
    //         return 'bg-info';
    //     } else if (toInt >= 20){
    //         return 'bg-warning';
    //     } else {
    //         return 'bg-danger';
    //     }
    // }

    render() {
        console.log("Rendering: ProjPanel");

        const { projectState } = this.props;
        const selectedDirId = projectState.get('selectedDirId');
        const dirContainer = projectState.get("dirContainer");
        const pid = dirContainer.treeMap[selectedDirId].pid;
        const project = dirContainer.projectMap[pid];
        console.log(dirContainer.tempProjectData);
        
        if (dirContainer.tempProjectData.pid !== project.pid) {
            const tempProjectData = dirContainer.tempProjectData;
            dirContainer.projectMap[tempProjectData.pid] = {...tempProjectData};
        }

        const userInfo = stores.getState().userState.userInfo;
        const strStDate = this.dateFormater(project.stDate);
        const strEdDate = this.dateFormater(project.edDate);
        // const isEditable = prole === '관리자' || prole === '책임자' || prole === '';

        return (<>
            <form onSubmit={this.handleSubmit}>
                <div className="card-header py-3">
                    <div className="row">
                        <div className="m-0 font-weight-bold text-darkblue col-10">
                            <input
                                name="pname" 
                                className="text-darkblue font-weight-bold"
                                // suppressContentEditableWarning={true} 
                                // contentEditable="true"
                                value={project.pname}
                                onChange={(e) => { this.onFormFieldChange(e, project); }}
                                style={{border: 'none', backgroundColor: 'none'}}
                            ></input>
                            {/* <input name="pname" type="hidden" value={project.pname}/> */}
                            <input name="pid" type="hidden" value={project.pid}/>
                            <input name="mid" type="hidden" value={userInfo.memberInfo.mid}/>
                            <input name="prole" type="hidden" value={project.prole}/>
                        </div>
                        <div className="text-right col-2 pt-1">
                            <div 
                                className="dropdown"
                                data-toggle="dropdown" role="button" 
                                aria-haspopup="true" 
                                aria-expanded="false"><span className="fas fa-ellipsis-h"></span></div>
                            <div className="dropdown-menu">
                            <div className="dropdown-item cusor-pointer" onClick={(e) => { this.disable(e, project) }}>삭제하기</div>
                            <div className="dropdown-divider"></div>
                            <div className="dropdown-item cusor-pointer">임시비고</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="card-body">
                    <div className="form-group row mb-3">
                    <div className="col-auto"><Collaborators collaborators={[{ mid: '임시 데이터', name: '김승신', prole: project.prole }]}/></div>
                    </div>
                    <hr></hr>
                    <div className="form-group row mb-3">

                        <div className="col-3">
                            <div className="row">
                                <div className="col-2 text-right pr-0">
                                    {/* <span className="pr-1 pb-1" style={{ fontSize: '.8rem' }}>시작일</span> */}
                                    <span className="fas fa-calendar-week pr-1" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                                </div>
                                <div className="col-10">
                                    <DatePicker
                                        className=" form-control sm-input text-right"
                                        selected={this.state.stDate}
                                        dateFormat="yyyy년 MM월 dd일"
                                        selectsStart
                                        startDate={this.state.stDate}
                                        endDate={this.state.edDate}
                                        onChange={this.onStartDateChange}
                                        placeholderText={strStDate}
                                    />
                                    <input name="stDate" type="hidden" value={this.state.stDate}/>
                                </div>
                            </div>
                        </div>
                        <div className="col-3">
                            <div className="row">
                                <div className="col-2 text-right pr-0">
                                {/* <span className="pr-1 pb-1" style={{ fontSize: '.8rem' }}>마감일</span> */}
                                    <span className="fas fa-calendar-check pr-1" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                                </div>
                                <div className="col-10">
                                    <DatePicker
                                        className="sm-input form-control text-right" 
                                        dateFormat="yyyy년 MM월 dd일"
                                        selected={this.state.edDate}
                                        selectsEnd
                                        startDate={this.state.stDate}
                                        endDate={this.state.edDate}
                                        onChange={this.onEndDateChange}
                                        placeholderText={strEdDate}
                                    />
                                    <input name="edDate" type="hidden" value={this.state.edDate}/>
                                </div>
                            </div>

                        </div>
                        <div className="col-4">
                                {/* 진행률 표시 */}
                                {/* <div className="col-12">
                                    <div className="progress mt-2">
                                        <div 
                                            className={`${this.progressColor(project.progress)}`} 
                                            role="progressbar" style={{width: `${project.progress}%`}} 
                                            aria-valuemin="0" aria-valuemax="100"
                                            >
                                        </div>
                                    </div>
                                </div> */}
                        </div>
                        <div className="col-2">
                            <select 
                                name="pstat" 
                                value={project.pstat} 
                                className="form-control sm-text" 
                                style={{ width: '100%' }}
                                onChange={(e) => { this.onFormFieldChange(e, project);}}
                                >
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
                    </div>
                    <hr></hr>
                    <div className="row mt-3">
                        <div className="col">
                            <textarea 
                                name="description" 
                                className="form-control" 
                                value={project.description}
                                onChange={(e) => { this.onFormFieldChange(e, project); }}
                            ></textarea>
                        </div>
                    </div>
                    <br></br>
                    { !project.isOrigin && (<div className="modal-footer">
                        <input className="btn btn-darkblue" type="submit" value="수정하기"></input>
                    </div>)}
                </div>
            </form>
        </>);
    }
}
export default ProjPanel;