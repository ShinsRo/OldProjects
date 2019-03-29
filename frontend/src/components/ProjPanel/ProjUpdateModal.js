import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import store from '../../stores';
import * as projectActions from '../../stores/modules/projectState';

class ProjUpdateModal extends React.Component {
    constructor(props) {
        super(props);
        this.onStartDateChange = this.onStartDateChange.bind(this);
        this.onEndDateChange = this.onEndDateChange.bind(this);
        this.onProjProgressChange = this.onProjProgressChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        const project = this.props.project;
        
        this.state = {
            ...this.props.project,
            startDate: new Date(project.startDate),
            endDate: new Date(project.endDate),
        }
    }

    onStartDateChange(date) {
        this.setState({ startDate: date });
    }
    onEndDateChange(date) {
        this.setState({ endDate: date });
    }
    onProjProgressChange(e) {
        this.setState({ projProgress: e.target.value });
    }

    handleSubmit(e) {
        const data = new FormData(e.target);
        const jsonObj = {};
        data.forEach((value, key) => {
            jsonObj[key] = value;
        });

        const URL = projectActions.BASE_URL + projectActions.END_POINT.PROJ_REGISTER;
        fetch(URL, {
            method: 'POST',
            body: JSON.stringify(jsonObj),
            headers: {
                'Content-Type': 'application/json',
                'accept': 'application/json'
            }
        });
    }

    render() {
        console.log("Rendering: ProjUpdateModal");
        const { userState } = store.getState();

        return (
            <div>
                {/* 디렉토리 추가 모달 */}
                <div className="modal fade" id="ProjUpdateModal" tabIndex="-1" role="dialog" aria-labelledby="ProjUpdateModal" aria-hidden="true">
                <div className="modal-dialog" role="document">
                <div className="modal-content">
                        <div className="modal-header">
                        <h5 className="modal-title font-weight-bold" id="exampleModalLabel">프로젝트를 추가합니다.</h5>
                        <button className="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        </div>
                        <div className="modal-body">
                        <form className="user" onSubmit={this.handleSubmit}>
                            <div className="form-group row">
                                <div className="col-xl-12">
                                    <input defaultValue={this.state.projName} name="projName" id="projName" type="text" className="form-control" placeholder="프로젝트 명" required/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col-8">
                                    <input defaultValue={this.state.projSubject} name="projSubject" type="text" className="form-control" placeholder="업무 제목" required/>
                                </div>
                                <div className="col">
                                    <select defaultValue={this.state.projCaleGubun} name="projCaleGubun" id="projCaleGubun" className="form-control">
                                        <option value="없음">일정 구분</option>
                                        <option value="주기성">주기성</option>
                                        <option value="비주기성">비주기성</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col">
                                    <textarea defaultValue={this.state.projDesc} name="projDesc" id="projDesc" cols="30" rows="10" className="form-control" placeholder="업무 설명">
                                    </textarea>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col">
                                    <DatePicker
                                        className="form-control" 
                                        selected={this.state.startDate}
                                        dateFormat="yyyy년 MM월 dd일"
                                        selectsStart
                                        startDate={this.state.startDate}
                                        endDate={this.state.endDate}
                                        onChange={this.onStartDateChange}
                                        readOnly={true}
                                    />
                                    <input name="startDate" type="hidden" value={this.state.startDate}/>
                                </div>
                                <div className="col">
                                    <DatePicker
                                        className="form-control" 
                                        dateFormat="yyyy년 MM월 dd일"
                                        selected={this.state.endDate}
                                        selectsEnd
                                        startDate={this.state.startDate}
                                        endDate={this.state.endDate}
                                        onChange={this.onEndDateChange}
                                        readOnly={true}
                                    />
                                    <input name="endDate" type="hidden" value={this.state.endDate}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col-3">
                                    <input type="text" className="form-control" placeholder="진행률 :" readOnly/>
                                </div>
                                <div className="col-7">
                                <input name="projProgress" type="range" className="form-control" 
                                    min="0" max="100"
                                    value={this.state.projProgress}
                                    onChange={this.onProjProgressChange}/>
                                </div>
                                <div className="col-2">
                                    <input type="text" className="form-control" defaultValue={`${this.state.projProgress}%`} readOnly/>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col">
                                    <select name="projStat" id="projStat" className="form-control">
                                        <option value={this.state.projStat}>{this.state.projStat}</option>
                                        <option value="대기">대기</option>
                                        <option value="접수">접수</option>
                                        <option value="진행">진행</option>
                                        <option value="할당">할당</option>
                                        <option value="완료">완료</option>
                                        <option value="보류">보류</option>
                                        <option value="폐기">폐기</option>
                                    </select>
                                </div>
                                <div className="col-8">
                                    <input type="text" className="form-control" readOnly
                                        value={`${userState.userInfo.userName}님이 프로젝트를 등록합니다.`} />
                                    <input name="userId" type="hidden" value={userState.userInfo.userId} />
                                </div>
                            </div>
                            <br></br>
                            <div className="modal-footer">
                                <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
                                <input className="btn btn-primary" type="submit" value="추가하기"></input>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        );
    }
};

export default ProjUpdateModal;