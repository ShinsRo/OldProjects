/* eslint-disable */
import Constants from './constants';
import axios from 'axios'
import Form from 'form-data'

const MEMBER_API_URL_PATH = 'http://localhost:8888/api/member'

// Mapping Class with MemberController
class MemberApi {
  static getMemberByEmail(email) {
    return $.get({
      url: `${MEMBER_API_URL_PATH}/getEmailByEmail?id=${email}`,
      timeOut: 1000
    })
  }

  static isExist(email) {
    return $.get({
      url: `${MEMBER_API_URL_PATH}/isExistEmail?email=${email}`,
      timeOut: 1000
    })
  }

  // static userResiger(user) {
  //   let bodyFormData = new Form()
  //
  //   bodyFormData.append('email', user.email)
  //   bodyFormData.append('password', user.password)
  //   bodyFormData.append('name', user.name)
  //   return axios({
  //     method: 'post',
  //     url: `${MEMBER_API_URL_PATH}/userRegister`,
  //     data: bodyFormData
  //   })
  // }
}

export default MemberApi;
