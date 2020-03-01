import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import {MDBBtn,MDBIcon} from 'mdbreact'
class DelPosi extends Component {
    constructor(props) {
        super(props);
        this.state = { posi: this.props.posiList[0].posiName ,value:''};
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

    delPosiAPI() {
        const posiName = {
            posiName: this.state.posi
        }
        // console.log("보낸다 가라아아앗", posiName)
        return axios.post(`${BASE_URL}/api/career/delposi`, posiName).then(
            (response) => {
                if (!response.data) alert("잘못 된 요청입니다")
                else {
                    alert(this.state.posi + " 삭제 되었습니다")
                    window.location.href = "/adminpage";
                }
            }
        )
    }
    render() {
        let i = 0
        const{posiList}= this.props
        return (
            <div>
                <div className="row text-gray-900 p-3 mt-3 ml-1"><b>직책 제거: </b>
                    <select className="col ml-1" value={this.state.posi}  onChange={e => this.onChange(e, 'posi')}>
                        {
                            posiList && posiList.map(posi => {
                                return (
                                    <option key={++i} className="col-2" value={posi.posiName}>{posi.posiName}</option>
                                )
                            })
                        }
                    </select>
                    <div className="col"></div>
                    {/* <input type="button" value=" delete " name="dept"  onClick={this.delPosiAPI.bind(this)} className="ml-5 btn btn-success btn-icon-split"></input> */}
                    <MDBBtn outline rounded size="sm" className="ml-3" color="primary" onClick={this.delPosiAPI.bind(this)} ><MDBIcon icon="trash" className="mr-2"/>제거</MDBBtn>
                </div>
            </div>
        );
    }
}

export default DelPosi;