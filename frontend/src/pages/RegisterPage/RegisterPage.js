import {Map} from 'immutable';
import axios from 'axios';
import React, { Component } from 'react';
var fullScreen = {
    height: '100%',
    width: '100%',
    position: 'absolute',
    top: 0, left: 0
};

class RegisterPage extends Component {
    constructor(props) {
        super(props);
        this.state = { username: '', password:'', eid:'', name:'' , birth:'' , phoneNum:''};
    }
    handleChangeInput(e, target) {
        //인풋 값 변경
        // console.log(target,e.target.value)
        this.setState({ [target]: e.target.value });
    }

    registUserAPI() {
        const auth={
            username:this.state.username,
            password:this.state.password,
        }
        const mem={
            eid:this.state.eid,
            name:this.state.name,
            birth:this.state.birth,
            phoneNum:this.state.phoneNum,
        }
        console.log("auth:",auth)
        console.log("mem:",mem)
    
        return axios.post('http://localhost:8080/upmureport/api/users/register',
        {
            auth:auth,
            mem:mem
        }
        )
      }



    render() {
        
        return (
            <div className="bg-gradient-darkblue" style={fullScreen}>
    
                <div className="container">
    
                    {/* <!-- Outer Row --> */}
                    <div className="row justify-content-center">
    
                    {/* <div className="col-xl-10 col-lg-12 col-md-9"> */}
                    <div className="col-xl-6">
    
                        <div className="card o-hidden border-0 shadow-lg my-5">
                        <div className="card-body p-0">
                            {/* <!-- Nested Row within Card Body --> */}
                            <div className="row">
                            {/* <div className="col-lg-6 d-none d-lg-block bg-login-image"></div> */}
                            <div className="col-xl-12">
                                <div className="p-5">
                                <hr />
                                <div className="text-center">
                                    <h1 className="h4 text-gray-700 mb-4">신규 사원 등록</h1>
                                </div>
                                <form className="User" action="" method="post">
                                    <div className="form-group">
                                    <input type='text' value={this.state.username} onChange = {e => this.handleChangeInput(e,'username')} type="text" className="form-control form-control-user" name="username" placeholder="아이디" />
                                    </div>
                                    <div className="form-group">
                                    <input value={this.state.password} onChange = {e => this.handleChangeInput(e,'password')} type="text" className="form-control form-control-user" name="password" placeholder="비밀번호" />
                                    </div>
                                    <div className="form-group">
                                    <input value={this.state.eid} onChange = {e => this.handleChangeInput(e,'eid')} type="text" className="form-control form-control-user" name="eid" placeholder="사번" />
                                    </div>
                                    <div className="form-group">
                                    <input value={this.state.name} onChange = {e => this.handleChangeInput(e,'name')} type="text" className="form-control form-control-user" name="name" placeholder="이름" />
                                    </div>
                                    <div className="form-group">
                                    <input value={this.state.birth} onChange = {e => this.handleChangeInput(e,'birth')} type="text" className="form-control form-control-user" name="birth" placeholder="생일" />
                                    </div>
                                    <div className="form-group">
                                    <input value={this.state.phoneNum} onChange = {e => this.handleChangeInput(e,'phoneNum')} type="text" className="form-control form-control-user" name="phoneNum" placeholder="핸드폰" />
                                    </div>
                                
                                    <input type="button" onClick={this.registUserAPI.bind(this)} className="btn btn-darkblue btn-user btn-block" value="등록" />
                                    <hr />
                                </form>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
    
                    </div>
    
                    </div>
    
                </div>
    
            </div>
        );    
    }
}
export default RegisterPage;