import { Map } from 'immutable';
import axios from 'axios';
import React, { Component } from 'react';
import { BASE_URL } from '../../supports/API_CONSTANT'
import { MDBBtn, MDBIcon } from 'mdbreact'
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
            username: '', password: '', password2: '', eid: '', name: '', birth: '', phoneNum: ''
            , checked: false, checked2: false, confirm: true, posi: '미등록', dept: '미등록',
        };  //checked id중복체크 checked2 사번중복체크
    }
    handleChangeInput(e, target) {
        //인풋 값 변경
        // console.log(target,e.target.value)
        if (target === "password") {

        } else if (target === "password2") {

            if (this.state.password !== e.target.value) {
                //jquery 사용 직접 DOM에 접근 
                //node.js 안의 window에 있음 jquery     # => id 
                const $ = window.$;
                $("#validation").html("비밀번호가 일치하지 않습니다.");
                this.setState({ confirm: true })
                // this.setState({ });
                /*
                 변경 버튼 비활성화
                */
            } else {
                const $ = window.$;
                $("#validation").html("비밀번호가 일치합니다.");
                this.setState({ confirm: false })
                // this.setState({ });
                /**
                 * 활성화
                 */
            }
        } else {

        }

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
    checkEidAPI() {
        const mem = {
            eid: this.state.eid,
        }
        if (this.state.eid === "") return alert("사번을 입력하세요")

        return axios.post(`${BASE_URL}/api/users/eidcheck`, mem).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if (!response.data) alert("이미 존재하는 사번입니다")
                else {
                    alert("사용 가능한 사번입니다")
                    this.setState({ checked2: true });
                }
            }
        )
    }
    click(target) {   //재입력 하는 버튼 이벤트
        if (target === "username")
            this.setState({ ...this.state, checked: false, username: '' });
        else if (target === "eid")
            this.setState({ ...this.state, checked2: false, eid: '' });
    }
    onChange(e, target) {   //select option change event
        this.setState({
            [target]: e.target.value
        })
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
        const newCar = {
            dept: this.state.dept,
            posi: this.state.posi
        }
        console.log("auth:", auth)
        console.log("mem:", mem)
        console.log("car", newCar)
        if (!this.state.checked) return alert("ID중복 검사를 먼저 하세요")
        if (!this.state.checked2) return alert("사번중복 검사를 먼저 하세요")
        if (this.state.password != this.state.password2) return alert("비밀번호를 올바르게 입력하세요")

        return axios.post(`${BASE_URL}/api/users/register`,
            {
                auth: auth,
                mem: mem,
                newCar: newCar
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
        let i = 0
        let j = 0
        const { deptList, posiList } = this.props;

        return (
            <div className="card o-hidden border-0 shadow-lg">
                <div className="card-header text-center">
                    <h4 align="center" className="card-header text-primary"><MDBIcon icon="user-edit" className="mr-2" />신규 사원 등록</h4>
                </div>
                <div className="card-body text-dark-2">
                    {/* <!-- Nested Row within Card Body --> */}
                    <div className="row">
                        {/* <div className="col-lg-6 d-none d-lg-block bg-login-image"></div> */}
                        <div className="col">
                            <div className="">
                                {/* <form className="User" action="" method="post"> */}
                                <div className="form-group row">
                                    <div className="col-7">
                                        <input type='text' disabled={this.state.checked} value={this.state.username} onChange={e => this.handleChangeInput(e, 'username')} type="text" className="form-control form-control-user" name="username" maxLength="10" placeholder="아이디" />
                                    </div>
                                    <div className="col-2">
                                        <input type='button' className=" btn-sm btn-primary mt-1 ml-1" value="중복검사" onClick={() => this.checkIdAPI()}></input>
                                    </div>
                                    <div className="col-2">
                                        <input type='button' className=" btn-sm btn-primary mt-1 ml-3" value="재입력" onClick={() => this.click('username')}></input>
                                    </div>
                                </div>
                                <div className="form-group">
                                    <input value={this.state.password} onChange={e => this.handleChangeInput(e, 'password')} type="password" className="form-control form-control-user" name="password" placeholder="비밀번호" />
                                </div>
                                <div className="form-group">
                                    <input value={this.state.password2} onChange={e => this.handleChangeInput(e, 'password2')} type="password" className="form-control form-control-user" name="password" placeholder="비밀번호 확인" />
                                </div>
                                <span id="validation"></span>
                                <div className="form-group row">
                                    <div className="col-7">
                                        <input value={this.state.eid} disabled={this.state.checked2} onChange={e => this.handleChangeInput(e, 'eid')} type="text" className="form-control form-control-user" name="eid" placeholder="사번" />
                                    </div>
                                    <div className="col-2">
                                        <input type='button' className=" btn-sm btn-primary mt-1 ml-1" value="중복검사" onClick={() => this.checkEidAPI()}></input>
                                    </div>
                                    <div className="col-2">
                                        <input type='button' className=" btn-sm btn-primary mt-1 ml-3" value="재입력" onClick={() => this.click('eid')}></input>
                                    </div>
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

                                <div className="form-group">
                                </div>
                                <div className="form-group row">
                                    <div className="col">부서
                                            <select className="ml-2 col" value={this.state.dept} onChange={e => this.onChange(e, 'dept')}>
                                            {
                                                deptList && deptList.map(dept => {
                                                    return (
                                                        this.state.dept == dept.posiName ?
                                                            <option key={++i} value={this.state.dept}>{this.state.dept}</option> : <option key={++i} value={dept.deptName}>{dept.deptName}</option>
                                                    )
                                                })
                                            }
                                        </select>
                                    </div>
                                    <div className="col">직책
                                        <select className="ml-2 col" value={this.state.posi} onChange={e => this.onChange(e, 'posi')}>
                                            {
                                                posiList && posiList.map(posi => {
                                                    return (
                                                        this.state.posi == posi.posiName ?
                                                            <option key={++j} value={this.state.posi}>{this.state.posi}</option> : <option key={++j} value={posi.posiName}>{posi.posiName}</option>
                                                    )
                                                })
                                            }
                                        </select>

                                    </div>
                                </div>


                                {/* <input type="button" onClick={this.registUserAPI.bind(this)} className="btn btn-darkblue btn-user btn-block" value="등록" /> */}
                                <MDBBtn outline rounded size="sm" className="col" color="primary" disabled={this.state.confirm} onClick={this.registUserAPI.bind(this)}><MDBIcon icon="user-plus" className="mr-2" />사원 등록</MDBBtn>

                                {/* </form> */}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default RegisterPage;