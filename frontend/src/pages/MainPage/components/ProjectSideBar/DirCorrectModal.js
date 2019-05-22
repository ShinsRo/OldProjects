/** 
 * 디렉토리 수정 모달
 * 
 * 2019.05.22
 * @file DirCorrectModal 정의
 * @author 김승신
 */
import React from "react";
import "react-datepicker/dist/react-datepicker.css";
import store from '../../../../stores';
import CorrectDirForm from './CorrectDirForm';

class DirCorrectModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            gubun: 'project',
            show: true,
        }
    }

    render() {
        const { userState, projectState } = store.getState();
        const selectedDirId = projectState.get('selectedDirId');
        const memberInfo = userState.userInfo.memberInfo;
        let contentForm;
        let header;

        contentForm = (<CorrectDirForm reload={this.props.reload} memberInfo={memberInfo} selectedDirId={selectedDirId} modalId="dirCorrectModal"/>);
        header = "선택한 경로의 디렉토리명을 수정합니다.";

        return (
            <div>
                {/* 디렉토리 추가 모달 */}
                <div className="modal fade" id="dirCorrectModal" tabIndex="-1" role="dialog">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                        <h5 className="modal-title font-weight-bold" id="exampleModalLabel">{header}</h5>
                        <button className="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        </div>
                        <div className="modal-body">
                            {contentForm}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        );
    }
};

export default DirCorrectModal;
