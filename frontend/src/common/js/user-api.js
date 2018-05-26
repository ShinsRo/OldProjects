/* eslint-disable */
import Constants from './constants';

const USER_URL_PATH = '/api/user';

// Mapping Class with UserController
class UserApi {

  static updateUser(user) {
    console.log(JSON.stringify(user));
    return $.post({
      url: `${USER_URL_PATH}/updateUser`,
      data: JSON.stringify(user),
      headers: Constants.JSON_HEADERS
    });
  }

  static getUserListAll() {
    return $.get({
      url: `${USER_URL_PATH}/getUserListAll`,
    });
  }

  static insertUser(user) {
    console.log(JSON.stringify(user));
    return $.post({
      url: `${USER_URL_PATH}/insertUser`,
      data: JSON.stringify(user),
      headers: Constants.JSON_HEADERS
    });
  }

  static deleteUser(user) {
    console.log(JSON.stringify(user));
    return $.post({
      url: `${USER_URL_PATH}/deleteUser`,
      data: JSON.stringify(user),
      headers: Constants.JSON_HEADERS
    });
  }

}

export default UserApi;
