import React, { Component } from 'react'
import store from '../../stores';
import DatePicker from "react-datepicker";
import Collaborators from '../Collaborators';
import "react-datepicker/dist/react-datepicker.css";
import { register } from '../../stores/modules/projectState';

class NewProjectForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stDate: null,
            edDate: null,
            progress: 0,
            gubun: 'project',
            show: true,
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
        const data = new FormData(e.target);
        const pDto = {};
        data.forEach((value, key) => {
            if (key === 'stDate' || key === 'edDate') {
                pDto[key] = new Date(value).toISOString();
            } else {
                pDto[key] = value;
            }
        });
        
        register(pDto).then((res) => {
            if (this.props.modalId) {
                const $ = window.$;
                const { projectState } = store.getState();
                const dirContainer = projectState.get("dirContainer");
                const project = res.data;

                $(`#${this.props.modalId}`).modal('hide');
                

                dirContainer.addProject(project);
                dirContainer.buildDirTrees();
                this.props.reload();
            } else {
                window.location.href = "/";
            }
        });
    }

    render() {
        const { memberInfo } = this.props;
        return (
        <form className="user" onSubmit={this.handleSubmit}>
        
        <hr></hr>
        <h6 className="font-weight-bold">프로젝트 참여자</h6>
        <div className="form-group row">
            <div className="col">
                <Collaborators collaborators={[{ name: memberInfo.name, mid: memberInfo.mid, prole: '관리자' }]}/>
            </div>
        </div>
        <hr></hr>
        <div className="form-group row">
            <div className="col-xl-12">
                <input name="pname" id="pname" type="text" className="form-control" placeholder="프로젝트 명" required/>
            </div>
        </div>
        {/* <div className="form-group row">
            <div className="col-8">
                <input name="projSubject" type="text" className="form-control" placeholder="업무 제목" required/>
            </div>
            <div className="col">
                <select name="projCaleGubun" id="projCaleGubun" className="form-control">
                    <option value="없음">일정 구분</option>
                    <option value="주기성">주기성</option>
                    <option value="비주기성">비주기성</option>
                </select>
            </div>
        </div> */}
        <div className="form-group row">
            <div className="col">
                <DatePicker
                    className="form-control" 
                    selected={this.state.stDate}
                    dateFormat="yyyy년 MM월 dd일"
                    selectsStart
                    startDate={this.state.stDate}
                    endDate={this.state.edDate}
                    onChange={this.onStartDateChange}
                    placeholderText="프로젝트 시작일"
                />
                <input name="stDate" type="hidden" value={this.state.stDate || "null"}/>
            </div>
            <div className="col">
                <DatePicker
                    className="form-control" 
                    dateFormat="yyyy년 MM월 dd일"
                    selected={this.state.edDate}
                    selectsEnd
                    startDate={this.state.stDate}
                    endDate={this.state.edDate}
                    onChange={this.onEndDateChange}
                    placeholderText="프로젝트 마감일"
                />
                <input name="edDate" type="hidden" value={this.state.edDate || "null"}/>
            </div>
        </div>
        <div className="form-group row">
            <div className="col">
                <textarea name="description" id="description" cols="30" rows="10" className="form-control" placeholder="프로젝트 설명">
                </textarea>
            </div>
        </div>
        {/* 진행률 row */}
        {/* <div className="form-group row">
            <div className="col-3">
                <input type="text" className="form-control" placeholder="진행률 :" readOnly/>
            </div>
            <div className="col-7">
            <input name="progress" type="range" className="form-control" 
                min="0" max="100"
                value={this.state.progress}
                onChange={this.onProgressChange}/>
            </div>
            <div className="col-2">
                <input type="text" className="form-control" value={`${this.state.progress}%`} readOnly/>
            </div>
        </div> */}
        <div className="row mb-3">
            <div className="col">
                <select name="pstat" id="pstat" className="form-control">
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
            <div className="col-8">
                <input type="text" className="form-control" readOnly
                    value={`${memberInfo.name}님이 프로젝트를 등록합니다.`} />
                <input name="mid" type="hidden" value={memberInfo.mid} />
            </div>
        </div>
        <div className="modal-footer">
            <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
            <input className="btn btn-primary" type="submit" value="추가하기"></input>
        </div>
        </form>
        );
    }
}

export default NewProjectForm;