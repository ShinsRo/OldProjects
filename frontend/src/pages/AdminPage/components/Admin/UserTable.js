import React, { Component } from 'react';
import { MDBDataTable } from 'mdbreact';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import axios from 'axios';
class UserTable extends Component {
  constructor(props) {
    super(props);
    // const { select } = this.props
    this.getUsersAPI()
  }
  click(user) {
    //console.log("로그",user)
    //alert(user.name)
    this.props.select(user);
  }
  getUsersAPI() {
    axios.get(`${BASE_URL}/api/users/listAll`).then(
      (response) => {
        //js 는 빈 문자열 빈오브젝트 false 
        this.setState({ users: response.data })
      }
    )
  }
  render() {
    const sta = this.state && this.state.users
    let rowsData = []
    let infos = []
    if (sta) {
      sta.map((user, idx) => {
        // console.log("한명", user)
        user.career.map((car) => {
          if (car.active === true) {
            // console.log("커리어", car)
            const temp = {
              eid: user.eid,
              name: user.name,
              birth: user.birth,
              phoneNum: user.phoneNum,
              joinDate: user.joinDate,
              currentDept:car.dept,
              currentPosi:car.posi,
              // retireDate: user.retireDate,
              // check: user.dflag && <MDBInput label=" " checked disabled type="checkbox" id="checkbox2" />,
              clickEvent: () => this.click(user),
            }
            rowsData.push(temp)
            infos.push(user)
          }
        })
        
      })
    }

    const data = {
      columns: [
        {
          label: '사번',
          field: 'eid',
          sort: 'asc',
          width: 200
        },
        {
          label: '이름',
          field: 'name',
          //sort: 'asc',
          width: 150
        },

        {
          label: '생일',
          field: 'birth',
          //sort: 'asc',
          width: 150
        },
        {
          label: '핸드폰',
          field: 'phoneNum',
          //sort: 'asc',
          width: 100
        },
        {
          label: '입사일',
          field: 'joinDate',
          //sort: 'asc',
          width: 180
        },
        {
          label: '부서',
          field: 'currentDept',
          //sort: 'asc',
          width: 180
        },
        {
          label: '직책',
          field: 'currentPosi',
          //sort: 'asc',
          width: 180
        },
        // {
        //   label: '퇴사일',
        //   field: 'retireDate',
        //   //sort: 'asc',
        //   width: 180
        // },
        // {
        //   label: '퇴사',
        //   field: 'check',
        //   //sort: 'asc',
        //   width: 100
        // },
      ],
      // rows: [
      //   {
      //     name: 'Tiger Nixon',
      //     position: 'System Architect',
      //     office: 'Edinburgh',
      //     age: '61',
      //     date: '2011/04/25',
      //     salary: '$320'
      //   }, 
      // ]
      //rows: this.state && this.state.users
      rows: rowsData,
      // info:infos
    }

    return (
      <MDBDataTable
        striped
        bordered
        hover
        //theadColor="indigo"
        entriesOptions={[5, 10, 15]}
        entries={15}
        searchLabel="찾기"
        // order={['eid','asc']}
        order={['name', 'asc']}
        data={data}
        maxHeight="400px"
      />
    );
  }
}

export default UserTable;