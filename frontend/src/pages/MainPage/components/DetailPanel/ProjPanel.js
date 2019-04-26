import React, { Component } from 'react';
import { correct, disable } from '../../../../stores/modules/projectState';
import stores from '../../../../stores';
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
            reload: false,
            isCorrected: false,
            loadCnt: 0,
        }
        this.onDateChange = this.onDateChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.disable = this.disable.bind(this);
        this.reload = this.reload.bind(this);
        // this.progressColor = this.progressColor.bind(this);
    }

    componentDidUpdate(prevProps, prevState) {
        const { projectState } = this.props;
        const selectedDirId = projectState.get('selectedDirId');
        const dirContainer = projectState.get("dirContainer");
        const pid = dirContainer.treeMap[selectedDirId].pid;
        const project = dirContainer.projectMap[pid];
        const tempProjectData = dirContainer.tempProjectData;
        
        const prevPid = dirContainer.tempProjectData.pid;
        
        if (prevPid && project.isOrigin && prevPid !== project.pid) {
            if (!dirContainer.projectMap[prevPid].isOrigin) {
                dirContainer.projectMap[prevPid] = {...tempProjectData};
            }
            dirContainer.tempProjectData = {};
        }
    }

    reload() {
        this.setState({ reload: !this.state.reload });
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

    onDateChange(name, date, project) {
        if (project.isOrigin) {
            const { projectState } = this.props;
            const dirContainer = projectState.get("dirContainer");
            dirContainer.tempProjectData = {...project};
            project.isOrigin = false;
        }
        project[name] = new Date(date);
        this.setState({isCorrected: true});
    }

    onFormFieldChange(e, project) {
        if (project.isOrigin) {
            const { projectState } = this.props;
            const dirContainer = projectState.get("dirContainer");
            dirContainer.tempProjectData = {...project};
            project.isOrigin = false;
        }
        project[e.target.name] = e.target.value;
        this.setState({isCorrected: true});
    }

    handleSubmit(e) {
        e.preventDefault();
        const data = new FormData(e.target);
        const pDto = {};
        
        data.forEach((value, key) => {
            if (key === 'stDate' || key === 'edDate') {
                pDto[key] = new Date(value).toISOString();
            } else if (key === 'deletedCollaborators' || key === 'collaborators') {
                pDto[key] = JSON.parse(value);
            } else {
                pDto[key] = value;
            }
        });

        correct(pDto).then((res) => {
            const project = res.data;
            const { projectState } = this.props;
            const dirContainer = projectState.get("dirContainer");
            
            dirContainer.correctProject(project);
            this.props.reload();
        });
    }

    progressColor(progress) {
        return 'bg-dark-1';
        // const toInt = Number(progress);
        // if (toInt === 100) {
        //     return 'bg-bright-2';
        // } else if (toInt >= 70){
        //     return 'bg-success';
        // } else if (toInt >= 40){
        //     return 'bg-info';
        // } else if (toInt >= 20){
        //     return 'bg-warning';
        // } else {
        //     return 'bg-danger';
        // }
    }

    render() {
        const { memberInfo } = stores.getState().userState.userInfo;
        
        const { projectState } = this.props;
        const selectedDirId = projectState.get('selectedDirId');
        const dirContainer = projectState.get("dirContainer");
        const pid = dirContainer.treeMap[selectedDirId].pid;
        const project = dirContainer.projectMap[pid];

        return (<>
            <form onSubmit={this.handleSubmit}>
                <div className="card-header py-3">
                    <div className="row">
                        <div className="m-0 col-10">
                            <input
                                name="pname" 
                                className="text-dark-1 font-weight-bold"
                                value={project.pname}
                                onChange={(e) => { this.onFormFieldChange(e, project); }}
                                style={{border: 'none', backgroundColor: '#F8F9FC', fontSize: '25px'}}
                            ></input>
                            <input name="pid" type="hidden" value={project.pid}/>
                            <input name="mid" type="hidden" value={memberInfo.mid}/>
                            <input name="prole" type="hidden" value={project.prole}/>
                        </div>
                        <div className="text-right col-2">
                            <div 
                                className="dropdown"
                                data-toggle="dropdown" role="button" 
                                aria-haspopup="true" 
                                aria-expanded="false"
                            >
                                <span className="fas fa-ellipsis-h" style={{ marginTop: "12px", cursor: "pointer" }}></span>
                            </div>
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
                        <div className="col-auto">
                            <Collaborators
                                type="CURRECT_PROJECT"
                                project={project}
                                collaborators={project.collaborators}
                                memberInfo={memberInfo}
                                reload={this.reload}
                            />
                        </div>
                    </div>
                    <div className="form-group row mb-2 pb-1 bg-dark-1 rounded">

                        <div className="col-3">
                            <div className="row">
                                <div className="col-12 text-right">
                                    <span className="text-white md-text">프로젝트 시작일</span>
                                </div>
                            </div>
                        </div>
                        <div className="col-3">
                            <div className="row">
                                <div className="col-12 text-right">
                                    <span className="text-white md-text">프로젝트 마감일</span>
                                </div>
                            </div>

                        </div>
                        <div className="col-4">
                            <div className="row">
                                <div className="col-12 text-right">
                                <input 
                                    type="text" 
                                    name="progress"
                                    className="text-white md-text text-right bg-dark-1 mr-1" 
                                    maxLength="3"
                                    style={{ width: '10%', border: 'none' }}
                                    value={ project.progress }
                                    onChange={(e) => { 
                                        if(Number(e.target.value) > 100) e.target.value = 100;
                                        this.onFormFieldChange(e, project); 
                                    }}
                                />
                                <span className="text-white md-text mr-2">%</span>
                                    <span className="text-white md-text">프로젝트 진행률</span>
                                </div>
                            </div>
                        </div>
                        <div className="col-2">
                            <div className="row">
                                <div className="col-12 text-right">
                                    <span className="text-white md-text">프로젝트 상태</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="form-group row mb-3">

                        <div className="col-3">
                            <div className="row">
                                <div className="col-2 text-right pr-0">
                                    <span className="fas fa-calendar-week pr-1 text-dark-2" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                                </div>
                                <div className="col-10">
                                    <DatePicker
                                        className=" form-control sm-input text-right text-black"
                                        selected={project.stDate}
                                        dateFormat="yyyy년 MM월 dd일"
                                        selectsStart
                                        startDate={project.stDate}
                                        endDate={project.edDate}
                                        onChange={(date) => {this.onDateChange('stDate', date, project)}}
                                        defalutValue={project.stDate}
                                    />
                                    <input name="stDate" type="hidden" value={project.stDate}/>
                                </div>
                            </div>
                        </div>
                        <div className="col-3">
                            <div className="row">
                                <div className="col-2 text-right pr-0">
                                    <span className="fas fa-calendar-check pr-1 text-dark-2" style={{ fontSize: '1rem', paddingTop: '6px' }}></span>
                                </div>
                                <div className="col-10">
                                    <DatePicker
                                        className="sm-input form-control text-right text-black" 
                                        dateFormat="yyyy년 MM월 dd일"
                                        selected={project.edDate}
                                        selectsEnd
                                        startDate={project.stDate}
                                        endDate={project.edDate}
                                        onChange={(date) => {this.onDateChange('edDate', date, project)}}
                                        defalutValue={project.stDate}
                                    />
                                    <input name="edDate" type="hidden" value={project.edDate}/>
                                </div>
                            </div>

                        </div>
                        <div className="col-4">
                                <div className="col-12 text-right pr-0">
                                    <div className="progress mt-2">
                                        <div 
                                            className={`${this.progressColor(project.progress)}`} 
                                            role="progressbar" style={{width: `${project.progress}%`}} 
                                            aria-valuemin="0" aria-valuemax="100"
                                            >
                                        </div>
                                    </div>
                                </div>
                        </div>
                        <div className="col-2">
                            <select 
                                name="pstat" 
                                value={project.pstat} 
                                className="form-control sm-text text-black" 
                                style={{ width: '100%' }}
                                onChange={(e) => { this.onFormFieldChange(e, project);}}
                                >
                                <option value="">상태</option>
                                <option value="대기">대기</option>
                                <option value="진행">진행</option>
                                <option value="완료">완료</option>
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
                                style={{ minHeight: "150px", maxHeight: "150px" }}
                            ></textarea>
                        </div>
                    </div>
                    <br></br>
                    { !project.isOrigin && (
                        <div className="text-right">
                            <button className="btn btn-dark mr-2" onClick={(e) => {e.preventDefault(); alert("구현중");}}>취소</button>
                            <input className="btn btn-dark-2" type="submit" value="수정하기"></input>
                        </div>
                    )}
                </div>
            </form>
        </>);
    }
}
export default ProjPanel;