import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import { MDBBtn, MDBIcon, } from 'mdbreact'

class ProfileModify extends Component {
    constructor(props) {
        super(props);
        this.state = { value: 'select', password: '', username: '' };
    }

    handleChangeInput = (e, target) => {
        //인풋 값 변경
        // console.log(e.target.value)
        this.setState({ [target]: e.target.value });
    }

    modifyPasswordAPI() {
        const auth = {
            password: this.state.password,
            mid: this.props.selectUser.mid
        }
        if (auth.password === '') return alert("변경할 비밀번호를 입력하세요")

        return axios.post(`${BASE_URL}/api/users/modify`,
            {
                mem: this.props.selectUser,
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
            mid: this.props.selectUser.mid
        }

        if (auth.username === '') return alert("변경할 아이디를 입력하세요")

        return axios.post(`${BASE_URL}/api/users/modify`,
            {
                mem: this.props.selectUser,
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
        return (
            <div>
                {/* <div className="text-gray-900 p-3 m-0"> <b>아이디  변경:  </b>
                    <input type="text" value={this.state.posi} name="posi" onChange={e => this.handleChangeInput(e, 'username')} ></input>
                    <input type="button" value=" Change " name="authInfo" onClick={this.modifyPasswordAPI.bind(this)} class="btn btn-success btn-icon-split"></input>
                    <MDBBtn outline rounded size="sm" className="ml-1" color="primary" onClick={this.modifyIdAPI.bind(this)} ><MDBIcon icon="check" className="" />  변경  </MDBBtn>
                </div> */}
                <div className="text-gray-900 p-3 m-0"> <b>비밀번호 변경:  </b>
                    <input type="password" value={this.state.posi} name="posi" onChange={e => this.handleChangeInput(e, 'password')} ></input>
                    {/* <input type="button" value=" Change " name="authInfo" onClick={this.modifyPasswordAPI.bind(this)} class="btn btn-success btn-icon-split"></input> */}
                    <MDBBtn outline rounded size="sm" className="ml-1" color="primary" onClick={this.modifyPasswordAPI.bind(this)} ><MDBIcon icon="check" className="" />  변경  </MDBBtn>
                </div>
            </div>
        );
    }
}

export default ProfileModify;