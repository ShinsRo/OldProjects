import React from "react";
import "react-datepicker/dist/react-datepicker.css";
import store from '../../../../stores';
import NewProjectForm from './NewProjectForm';
import NewDirForm from './NewDirForm';

// import * as projectActions from '../../stores/modules/projectState';

class AddModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            gubun: 'project',
            show: true,
        }
    }

    onTabClick(gubun) {
        this.setState({ gubun });
    }

    render() {
        const { userState, projectState } = store.getState();
        const selectedDirId = projectState.get('selectedDirId');
        const memberInfo = userState.userInfo.memberInfo;
        let contentForm;
        let projectBtnClass;
        let dirBtnClass;
        let header;

        if (this.state.gubun === 'project') {
            projectBtnClass = "btn btn-dark-1";
            dirBtnClass = "btn btn-secondary";
            contentForm = (<NewProjectForm reload={this.props.reload} memberInfo={memberInfo} modalId="addModal"/>);
            header = "새로운 프로젝트를 추가합니다.";
        } else {
            projectBtnClass = "btn btn-secondary";
            dirBtnClass = "btn btn-dark-1";
            contentForm = (<NewDirForm reload={this.props.reload} memberInfo={memberInfo} selectedDirId={selectedDirId} modalId="addModal"/>);
            header = "선택한 경로에 디렉토리를 추가합니다.";
        }

        return (
            <div>
                {/* 디렉토리 추가 모달 */}
                <div className="modal fade" id="addModal" tabIndex="-1" role="dialog">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                        <h5 className="modal-title font-weight-bold text-dark-2" id="exampleModalLabel">{header}</h5>
                        <button className="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        </div>
                        <div className="modal-body">
                        <div className="row mb-3">
                            <div className="col">
                                <button type="button" className={projectBtnClass} style={{ width: "100%" }} onClick={(e) => {this.onTabClick("project")}}>프로젝트 추가하기</button>
                            </div>
                            <div className="col">
                                <button type="button" className={dirBtnClass} style={{ width: "100%" }} onClick={(e) => {this.onTabClick("dir")}}>디렉토리 추가하기</button>
                            </div>
                        </div>
                            {contentForm}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        );
    }
};

export default AddModal;
