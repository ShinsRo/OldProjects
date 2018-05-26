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

//   static updateBoard(board){
//     return $.post({
//       url: `${MENU_API_URL_PATH}/updateBoard`,
//       data: JSON.stringify(board),
//       headers: Constants.JSON_HEADERS
//     });
//   }
//   static selectBoard(boardNo) {
//     return $.get({
//       url: `${MENU_API_URL_PATH}/selectBoard?boardNo=${boardNo}`,
//       timeOut: 1000
//     })
//   }
//
//   static selectAllBoard() {
//     return $.get({
//       url: `${MENU_API_URL_PATH}/selectAllBoard`,
//       timeOut: 1000
//     })
//   }
//
//   static deleteBoard(boardNo) {
//     return $.get({
//       url: `${MENU_API_URL_PATH}/deleteBoard?boardNo=${boardNo}`,
//       timeOut: 1000
//     })
//   }
 }

export default MenuApi;
