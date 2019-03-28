import Sidebar from '../components/Sidebar/Sidebar'
import React, { Component } from 'react';
import {List,Map} from 'immutable';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as juniorsAction from '../stores/modules/juniorList'
import * as userActions from '../stores/modules/userState'
import store from '../stores'
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
        const {juniorsAction} = this.props;
        console.log("사이드바 api",userInfo);
        juniorsAction.getJuniors(userInfo).then(res=> {
            const {juniorList} =store.getState()
            this.setState({
               users: juniorList.get('users')
           })
        })
        .then(res=>{
            const deptMap={}
            //const deptName=[]
            let idx=0
            this.state.users && this.state.users.forEach(user => {
                //!deptName.includes(user.dept) && deptName.push(user.dept)
 
                //dict처럼
                deptMap[user.dept] = deptMap[user.dept] || [];
                deptMap[user.dept].push(user);
                /*
                if(!(deptList.includes(user.dept)))
                {
                    
                }*/
            });

            this.setState({
                depts: deptMap,
                //deptName: deptName
            })
            console.log("부서들",this.state)
        })
    }
    render() {
        const {userActions} = this.props
        console.log('사이드바컨테이너 확인',this.state.users)
        const users=this.state.users
        const depts=this.state.depts
        //const deptName=this.state.deptName
        return(
            <Sidebar
                users={users}
                depts={depts}
                select={userActions.select}
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
        userActions: bindActionCreators(userActions, dispatch),
    })
)(SidebarContainer);