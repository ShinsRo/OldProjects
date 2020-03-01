/** 
 * 디렉토리 수정 폼
 * 
 * 2019.05.22
 * @file CorrectDirForm 정의
 * @author 김승신
 */
import React, { Component } from 'react'
import store from '../../../../stores';
import "react-datepicker/dist/react-datepicker.css";
import { pdir_api } from '../../../../stores/modules/projectState';

class CorrectDirForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: '',
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidUpdate(prevProps, prevState) {
        
        if (this.props.selectedDirId !== prevProps.selectedDirId) {
            const {selectedDirId} = this.props;
            const { projectState } = store.getState();
            
            const dirContainer = projectState.get('dirContainer');
            const title = dirContainer.treeMap[selectedDirId].title;

            this.setState({title});
        }
    }

    handleSubmit(e) {
        e.preventDefault();
        const data = new FormData(e.target);
        const dDto = {};
        data.forEach((value, key) => {
            if (key === 'title') {
                dDto['dname'] = value;
            } else {
                dDto[key] = value;
            }
        });
        
        pdir_api.correct(dDto, 'rename').then((res) => {
            if (this.props.modalId) {
                const $ = window.$;
                const { projectState } = store.getState();
                const dirContainer = projectState.get("dirContainer");
                const dir = res.data;
                
                dirContainer.addOne(dir);
                dirContainer.buildDirTrees();
                $(`#${this.props.modalId}`).modal('hide');

                this.props.reload();
            } else {
                window.location.href = "/";
            }
        });
    }

    onTitleChange(e) {
        this.setState({ title: e.target.value });
    }

    render() {
        const { memberInfo, selectedDirId } = this.props;
        const { projectState } = store.getState();
        const dirContainer = projectState.get('dirContainer');

        if(!dirContainer.treeMap[selectedDirId]) {
            return (
                <div className="col-12">
                    경로를 선택하고 다시 시도해 주세요.
                </div>
            )
        }

        let pathQueue = [];
        let cwd = dirContainer.treeMap[selectedDirId];
        const pid = dirContainer.treeMap[selectedDirId].pid;

        pathQueue.push(cwd.title);
        while ( cwd.parent !== 'root' ) {
            cwd = dirContainer.treeMap[cwd.parent];
            pathQueue.push(cwd.title);
        }
        let fullPathStr = pathQueue.reduce((prev, curr, idx) => {
            if (prev.length > 20 || idx > 3) return prev;
            return curr + " > " + prev;
        });

        return (
        <form className="user" onSubmit={this.handleSubmit}>
        <input name="did" type="hidden" value={selectedDirId}/>
        <input name="mid" type="hidden" value={memberInfo.mid}/>
        <input name="mname" type="hidden" value={memberInfo.mname}/>
        <input name="pid" type="hidden" value={pid}/>
        <div className="form-group row">
            <div className="col-12">
                <input type="text" className="form-control" value={fullPathStr} readOnly/>
            </div>
        </div>
        <div className="form-group row">
            <div className="col-12">
                <input 
                    name="dname" 
                    value={this.state.title} 
                    type="text" 
                    className="form-control" 
                    placeholder="디렉토리 명" 
                    onChange={this.onTitleChange.bind(this)}
                    required/>
            </div>
        </div>
        <div className="modal-footer">
            <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
            <input className="btn btn-dark-1" type="submit" value="수정하기"></input>
        </div>
        </form>
        );
    }
}

export default CorrectDirForm;