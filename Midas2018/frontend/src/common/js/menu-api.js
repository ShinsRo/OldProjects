/* eslint-disable */
import Constants from './constants';

const MENU_API_URL_PATH = '/api/menu';

// Mapping Class with BoardController
class MenuApi {

  static insertMenu(menu) {
    return $.post({
      url: `${MENU_API_URL_PATH}/insertMenu`,
      data: JSON.stringify(menu),
      headers: Constants.JSON_HEADERS
    });
  }

  static updateMenu(menu) {
    return $.post({
      url: `${MENU_API_URL_PATH}/updateMenu`,
      data: JSON.stringify(menu),
      headers: Constants.JSON_HEADERS
    });
  }

  static deleteMenu(id) {
    return $.get({
      url: `${MENU_API_URL_PATH}/deleteMenu?id=${id}`,
    });
  }

  static selectMenu(id) {
    return $.get({
      url: `${MENU_API_URL_PATH}/selectMenu?id=${id}`,
    });
  }

  static selectAll() {
    return $.get({
      url: `${MENU_API_URL_PATH}/selectAll`,
    });
  }
}

export default MenuApi;
