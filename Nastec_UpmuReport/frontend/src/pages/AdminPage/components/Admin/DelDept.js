import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import {MDBBtn,MDBIcon} from 'mdbreact'

class DelDept extends Component {
    constructor(props) {
        super(props);
        this.state = { deptInput: '' ,value:this.props.deptList[0].deptName};
    }
    onChange(e, target) {
        this.setState({
            [target]: e.target.value
        })
    }

    handleChangeInput = (e, target) => {
        //인풋 값 변경
        this.setState({ [target]: e.target.value });
    }

    addDeptAPI() {
        const deptName = {
            deptName: this.state.deptInput
        }
        // console.log("보낸다 가라아아앗", deptName)
        if (deptName.deptName === '') return alert("추가할 부서명을 쓰세요")

        return axios.post(`${BASE_URL}/api/career/adddept`, deptName).then(
            (response) => {
                //js 는 빈 문자열 빈오브젝트 false 
                if(!response.data) alert("이미 존재하는 부서입니다")
                else {
                    alert(this.state.deptInput+" 추가 되었습니다")
                    window.location.href="/adminpage";
                }
            }
        )
    }
    delDeptAPI() {
        const deptName = {
            deptName: this.state.value
        }
        //console.log("보낸다 가라아아앗", deptName)
        if (deptName.deptName === '') return alert("추가할 부서명을 쓰세요")

        return axios.post(`${BASE_URL}/api/career/deldept`, deptName).then(
            (response) => {
                if(! response.data) alert("잘못 된 요청입니다")
                else {
                    alert(this.state.value+" 삭제 되었습니다")
                    window.location.href="/adminpage";
                }
            }
        )
    }
    render() {
        const { deptList } = this.props
        let i = 0
        return (
            <div>
                <div className="row text-gray-900 p-3 mt-3 ml-1"><b>부서 제거: </b>
                    <select className="col ml-1" value={this.state.value} onChange={e => this.onChange(e, 'value')}>
                        {
                            deptList && deptList.map(dept => {
                                return (
                                    <option key={++i} value={dept.deptName}>{dept.deptName}</option>
                                )
                            })
                        }
                    </select>
                    <div className="col"></div>
                    {/* <input type="button" value="delete" name="dept" onClick={this.delDeptAPI.bind(this)} className="col align-self-end btn btn-success btn-icon-split"></input> */}
                    <MDBBtn outline rounded size="sm" className="ml-3" color="primary" onClick={this.delDeptAPI.bind(this)} ><MDBIcon icon="trash" className="mr-2"/>제거</MDBBtn>
                </div>
            </div>
        );
    }
}

export default DelDept;