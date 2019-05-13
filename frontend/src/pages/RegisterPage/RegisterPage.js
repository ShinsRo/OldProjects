import { Map } from 'immutable';
import axios from 'axios';
import React, { Component } from 'react';
import { BASE_URL } from '../../supports/API_CONSTANT'
import {MDBBtn,MDBIcon} from 'mdbreact'
var fullScreen = {
    // height: '100%',
    // width: '100%',
    position: 'relative',
    // top: 0, left: 0
};

class RegisterPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '', password: '', eid: '', name: '', birth: '', phoneNum: ''
            , checked: false
        };
    }
    handleChangeInput(e, target) {
        //인풋 값 변경
        // console.log(target,e.target.value)
        this.setState({ [target]: e.target.value });
    }
    checkIdAPI() {
        const auth = {
            username: this.state.username,
        }
        if (this.state.username === "") return alert("ID를 입력하세요")

        return axios.post(`${BASE_URL}/api/users/idcheck`, auth).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if (!response.data) alert("이미 존재하는 아이디입니다")
                else {
                    alert("사용 가능한 아이디입니다")
                    this.setState({ checked: true });
                }
            }
        )
    }
    click() {   //재입력 하는 버튼 이벤트

        this.setState({ ...this.state, checked: false, username: '' });
    }

    registUserAPI() {
        const auth = {
            username: this.state.username,
            password: this.state.password,
        }
        const mem = {
            eid: this.state.eid,
            name: this.state.name,
            birth: this.state.birth,
            phoneNum: this.state.phoneNum,
        }
        console.log("auth:", auth)
        console.log("mem:", mem)
        if (!this.state.checked) return alert("ID중복 검사를 먼저 하세요")

        return axios.post(`${BASE_URL}/api/users/register`,
            {
                auth: auth,
                mem: mem
            }
        ).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if (!response.data) alert("등록에 에러가 생겼습니다.")
                else {
                    alert(response.data.name + " 등록 되었습니다")
                    window.location.href = "/adminpage";
                }
            }
        )
    }


    render() {

        return (
                <div className="card o-hidden border-0 shadow-lg">
                    <div className="card-body">
                        {/* <!-- Nested Row within Card Body --> */}
                        <div className="row">
                            {/* <div className="col-lg-6 d-none d-lg-block bg-login-image"></div> */}
                            <div className="col">
                                <div className="">
                                    <hr />
                                    <div className="text-center">
                                        <h1 className="h4 text-gray-700 ">신규 사원 등록</h1>
                                    </div>
                                    <form className="User" action="" method="post">
                                        <div className="form-group">
                                            <div className="form-group">
                                                <input type='text' disabled={this.state.checked} value={this.state.username} onChange={e => this.handleChangeInput(e, 'username')} type="text" className="form-control form-control-user" name="username" placeholder="아이디" />
                                                <input type='button' className=" btn btn-primary mt-2" value="중복검사" onClick={() => this.checkIdAPI()}></input>
                                                <input type='button' className=" btn btn-primary mt-2 ml-2" value="재입력" onClick={() => this.click()}></input>
                                            </div>
                                        </div>
                                        <div className="form-group">
                                            <input value={this.state.password} onChange={e => this.handleChangeInput(e, 'password')} type="password" className="form-control form-control-user" name="password" placeholder="비밀번호" />
                                        </div>
                                        <div className="form-group">
                                            <input value={this.state.eid} onChange={e => this.handleChangeInput(e, 'eid')} type="text" className="form-control form-control-user" name="eid" placeholder="사번" />
                                        </div>
                                        <div className="form-group">
                                            <input value={this.state.name} onChange={e => this.handleChangeInput(e, 'name')} type="text" className="form-control form-control-user" name="name" placeholder="이름" />
                                        </div>
                                        <div className="form-group">
                                            <input value={this.state.birth} onChange={e => this.handleChangeInput(e, 'birth')} type="text" className="form-control form-control-user" name="birth" placeholder="생일" />
                                        </div>
                                        <div className="form-group">
                                            <input value={this.state.phoneNum} onChange={e => this.handleChangeInput(e, 'phoneNum')} type="text" className="form-control form-control-user" name="phoneNum" placeholder="핸드폰" />
                                        </div>
                                        {/* <input type="button" onClick={this.registUserAPI.bind(this)} className="btn btn-darkblue btn-user btn-block" value="등록" /> */}
                                        <MDBBtn outline rounded size="sm" className="col" color="primary" onClick={this.registUserAPI.bind(this)}><MDBIcon icon="user-plus" className="mr-2"/>사원 등록</MDBBtn>
                                        <hr />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        );
    }
}
export default RegisterPage;