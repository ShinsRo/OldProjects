import React from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';


class CurrectForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = { value: 'select', password: '', passwordConfirm: '', username: '' };

        this.modifyPasswordAPI = this.modifyPasswordAPI.bind(this);
    }

    handleChangeInput = (e, target) => {
        //인풋 값 변경
        // console.log(e.target.value)
        this.setState({ [target]: e.target.value });
    }

    modifyPasswordAPI(e) {
        e.preventDefault();
        if (!this.state.password) {
            alert("변경할 비밀번호를 입력하세요");
            return;
        }
        if (this.state.password !== this.state.passwordConfirm) {
            alert("변경 비밀번호가 서로 일치하지 않습니다.");
            return;
        }
        const auth = {
            password: this.state.password,
            mid: this.props.memberInfo.mid
        }

        return axios.post(`${BASE_URL}/api/users/modify`,
            {
                mem: this.props.memberInfo,
                auth: auth
            }
        ).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if (!response.data) alert("오류 입니다")
                else {
                    alert("변경 되었습니다.")
                    window.location.href = "/";
                }
            }
        )
    }

    modifyIdAPI() {
        const auth = {
            username: this.state.username,
            mid: this.props.memberInfo.mid
        }

        if (auth.username === '') return alert("변경할 아이디를 입력하세요")

        return axios.post(`${BASE_URL}/api/users/modify`,
            {
                mem: this.props.memberInfo,
                auth: auth
            }
        ).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if (!response.data) alert("오류 입니다")
                else {
                    alert("변경 되었습니다.")
                    window.location.href = "/profile";
                }
            }
        )
    }

    render() {
        const { closeModal } = this.props;

        return (
            <div className="card">
                <div className="card-header">
                <h4 className="text-dark-1 pt-1">비밀번호 변경</h4>
                </div>
                <div className="card-body text-dark-1">
                    <div className="row">
                        <div className="col-4"><small>비밀번호</small></div>
                        <div className="col-8 text-right">
                            <input type="password" style={{ height: '25px' }} className="form-control" onChange={e => this.handleChangeInput(e, 'password')}/>
                        </div>
                    </div>
                    <div className="row mt-2">
                        <div className="col-4"><small>비밀번호 확인</small></div>
                        <div className="col-8 text-right">
                            <input type="password" style={{ height: '25px' }} className="form-control" onChange={e => this.handleChangeInput(e, 'passwordConfirm')}/>
                        </div>
                    </div>

                    <div className="row mt-4">
                        <div className="col-12 text-right">
                        <button className="btn btn-secondary mr-2" onClick={() => closeModal('visible')}>닫기</button>      
                        <button className="btn btn-dark-2" onClick={this.modifyPasswordAPI}>변경하기</button>      
                        </div>          
                    </div>
                </div>
            </div>
        );
    }
}

export default CurrectForm;