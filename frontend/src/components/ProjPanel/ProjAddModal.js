import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class ProjAddModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate: '',
            endDate: '',
        }
    }

    onStartDateChange(date) {
        alert(date);
        this.setState({ startDate: date })
    }

    onEndDateChange(date) {
        alert(date);
        this.setState({ endDate: date })
    }

    render() {

        return (
            <div>
                {/* 디렉토리 추가 모달 */}
                <div className="modal fade" id="projAddModal" tabIndex="-1" role="dialog" aria-labelledby="projAddModal" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                        <h5 className="modal-title font-weight-bold" id="exampleModalLabel">프로젝트를 추가합니다.</h5>
                        <button className="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        </div>
                        <div className="modal-body">
                        <form className="user">
                        <div className="form-group row">
                            <div className="col-xl-12">
                                <input type="text" className="form-control" placeholder="프로젝트 명"/>
                            </div>
                        </div>
                        <div className="form-group row">
                        
                        </div>
                        <div className="form-group row">
                            <div className="col-xl-6">
                                <input type="text" className="form-control" placeholder="시작일" />
                            </div>
                            <div className="col-xl-6">
                                <input type="text" className="form-control" placeholder="종료일"/>
                            </div>
                        </div>
                        </form>
                        
                        </div>
                        <div className="modal-footer">
                        <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
                        <a className="btn btn-primary" href="login.html">추가하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        );
    }
};

// class DataPickInput extends React.Component {

//     render () {
        
//     }
// }

export default ProjAddModal;