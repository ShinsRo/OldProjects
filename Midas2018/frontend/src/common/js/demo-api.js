/* eslint-disable */
import Constants from './constants';

const USER_API_URL_PATH = '/api/user';

// Mapping Class with UserController
class UserApi {
  static getUserById(id) {
    return $.get({
      url: `${USER_API_URL_PATH}/getUserById?id=${id}`,
      timeOut: 1000
    })
  }
  static insertUser(user) {
    return $.post({
      url: `${USER_API_URL_PATH}/insertUser`,
      data: JSON.stringify(user),
      headers: Constants.JSON_HEADERS
    });
  }
}

export default UserApi;
