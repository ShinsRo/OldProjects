import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import {MDBBtn,MDBIcon} from 'mdbreact'
class AddPosi extends Component {
    constructor(props) {
        super(props);
        this.state = { posi: '' ,value:''};
    }
    // select option 용
    onChange(e, target) {
        this.setState({
            [target]: e.target.value
        })
    }

    //일반 input 용 
    handleChangeInput = (e, target) => {
        //인풋 값 변경
        this.setState({ [target]: e.target.value });
    }

    addPosiAPI() {
        const posi = {
            posiName: this.state.posi
        }
        //console.log("보낸다 가라아아앗", Posi)
        if (posi.posiName === '') return alert("추가할 직책명을 쓰세요")

        return axios.post(`${BASE_URL}/api/career/addposi`, posi).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if(!response.data) alert("이미 존재하는 직책입니다")
                else {
                    alert(this.state.posi+" 추가 되었습니다")
                    window.location.href="/adminpage";
                }
            }
        )
    }
    delPosiAPI() {
        const posiName = {
            posiName: this.state.value
        }
        // console.log("보낸다 가라아아앗", posiName)
        return axios.post(`${BASE_URL}/api/career/delposi`, posiName).then(
            (response) => {
                if (!response.data) alert("잘못 된 요청입니다")
                else {
                    alert(this.state.value + " 삭제 되었습니다")
                    window.location.href = "/adminpage";
                }
            }
        )
    }
    render() {
        return (
            <div>
                <div className="row text-gray-900 p-3 mt-3 ml-1"> <b>직책 추가:  </b>
                    <input type="text" className="ml-1" value={this.state.posi} name="PosiName" onChange={e => this.handleChangeInput(e, 'posi')} placeholder=" 직책명" ></input>
                    <MDBBtn outline rounded size="sm" className="ml-3" color="primary" onClick={this.addPosiAPI.bind(this)} ><MDBIcon icon="plus" className="mr-1"/>추가</MDBBtn>
                    {/* <input type="button" value="  ADD  " name="authInfo" onClick={this.addPosiAPI.bind(this)} class="btn btn-success btn-icon-split"></input> */}
                </div>
            </div>
        );
    }
}

export default AddPosi;