import Sidebar from '../components/Sidebar'
import React, { Component } from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as juniorsAction from '../../../stores/modules/juniorState'
import store from '../../../stores'

class SidebarContainer extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            users:this.props.users,
            depts:[],
           // deptName:[]
        };
    }
    componentWillMount(){
        const {userState} = store.getState()
        const {userInfo} = userState
        const {memberInfo} = userInfo
        const {juniorsAction} = this.props;

        juniorsAction.getJuniors(memberInfo).then(res=> {
            const {juniorState} =store.getState()
            this.setState({
                //map은 get
               users: juniorState.get('users')
           })
        })
        .then(res=>{
            const deptMap={}
            //const deptName=[]
            this.state.users && this.state.users.forEach(user => {
                //!deptName.includes(user.dept) && deptName.push(user.dept)
                const Careers = user.career;
                Careers.forEach(car =>{
                    if(car.active === true){
                        deptMap[car.dept] = deptMap[car.dept] || [];
                        deptMap[car.dept].push(user);
                    }
                })
                //dict처럼
                //deptMap[user.dept] = deptMap[user.dept] || [];
                //deptMap[user.dept].push(user);
                /*
                if(!(deptList.includes(user.dept)))
                {
                    
                }*/
            });

            this.setState({
                depts: deptMap,
                //deptName: deptName
            })
        })
    }
    render() {
        const {select} = this.props
        const users=this.state.users
        const depts=this.state.depts
        const {userState} = store.getState()
        const {userInfo} = userState
        const {memberInfo} = userInfo
        //const deptName=this.state.deptName
        return(
            <Sidebar
                users={users}
                depts={depts}
                select={select}
                userInfo={memberInfo}
                //deptName={deptName}
            />
        );
    }
}
export default connect(     
    //state를 비구조화
    (state) => ({
        users: state.users
    }),
    //함수들
    (dispatch) => ({
        juniorsAction: bindActionCreators(juniorsAction, dispatch),
        //userActions: bindActionCreators(userActions, dispatch),
    })
)(SidebarContainer);