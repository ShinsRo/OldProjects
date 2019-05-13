import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';

class Posi extends Component {
    constructor(props) {
        super(props);
        this.state = { posi: '' ,value:''};
    }
    onChange(e, target) {
        this.setState({
            [target]: e.target.value
        })
        console.log(e.target.value)
    }

    handleChangeInput = (e, target) => {
        //인풋 값 변경
        console.log(e.target.value)
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
        console.log("보낸다 가라아아앗", posiName)
        // if (deptName.deptName === '') return alert("추가할 부서명을 쓰세요")

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
        const{posiList}= this.props
        return (
            <div>
                <div className="text-gray-900 p-3 m-0"> <b>직책 추가:  </b>
                    <input type="text" value={this.state.posi} name="PosiName" onChange={e => this.handleChangeInput(e, 'posi')} ></input>
                    <input type="button" value="  ADD  " name="authInfo" onClick={this.addPosiAPI.bind(this)} class="btn btn-success btn-icon-split"></input>
                </div>
                <form action="" className="text-gray-900 p-3 m-0"><b>직책 제거: </b>
                    <select value={this.state.value} onChange={e => this.onChange(e, 'value')}>
                        {
                            posiList && posiList.map(posi => {
                                return (
                                    <option value={posi.posiName}>{posi.posiName}</option>
                                )
                            })
                        }
                    </select>
                    <input type="button" value=" delete " name="dept"  onClick={this.delPosiAPI.bind(this)} className="ml-5 btn btn-success btn-icon-split"></input>

                </form>
            </div>
        );
    }
}

export default Posi;