/** 
 * 새로운 프로젝트 추가 시 나타날 폼
 * 
 * 2019.05.22
 * @file NewProjectForm 정의
 * @author 김승신
 */

import React, { Component } from 'react'
import DatePicker from "react-datepicker";
import Collaborators from '../Collaborators';
import "react-datepicker/dist/react-datepicker.css";
import store from '../../../../stores';
import { register } from '../../../../stores/modules/projectState';

class NewProjectForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stDate: new Date(),
            edDate: new Date(),
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
        if ( date.getTime() > this.state.edDate.getTime() ) { alert('마감일이 시작일보다 미래일 수 없습니다.'); return; }
        this.setState({ stDate: new Date(date) });
    }
    onEndDateChange(date) {
        if ( date.getTime() < this.state.stDate.getTime() ) { alert('마감일이 시작일보다 미래일 수 없습니다.'); return; }
        this.setState({ edDate: new Date(date) });
    }
    onProgressChange(e) {
        this.setState({ progress: e.target.value });
    }

    handleSubmit(e) {
        e.preventDefault();
        
        if (!window.confirm("프로젝트를 이대로 등록할까요?")) return;

        // const data = new FormData(e.target);
        const data = new FormData(document.getElementById('newProjectForm'));
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
        <form id="newProjectForm" className="user" onSubmit={this.handleSubmit}>
        
        <hr></hr>
        <h6 className="font-weight-bold">프로젝트 참여자</h6>
        <div className="form-group row">
            <div className="col">
                <Collaborators
                    key={Date.now().toString()}
                    type="NEW_PROJECT"
                    collaborators={ [{ mid: memberInfo.mid, name: memberInfo.name, prole: '관리자' }] }
                    reload={this.props.reload}
                    memberInfo={memberInfo}
                />
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
                    required
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
                    required
                />
                <input name="edDate" type="hidden" value={this.state.edDate || "null"}/>
            </div>
        </div>
        <div className="form-group row">
                <div className="col-3">
                    <input type="text" className="form-control" placeholder="기존진행률" readOnly/>
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
        </div>
        <div className="form-group row">
            <div className="col">
                <textarea name="description" id="description" cols="30" rows="10" className="form-control" placeholder="프로젝트 설명" required>
                </textarea>
            </div>
        </div>
        {/* 진행률 row */}
        {/* <div className="form-group row">
        </div> */}
        <div className="row mb-3">
            <div className="col">
                <select name="pstat" id="pstat" className="form-control" required>
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
            <button className="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
            <button className="btn btn-dark-1" type="submit">추가하기</button>
            {/* <div className="btn btn-dark-1" onClick={this.handleSubmit}>추가하기</div> */}
        </div>
        </form>
        );
    }
}

export default NewProjectForm;