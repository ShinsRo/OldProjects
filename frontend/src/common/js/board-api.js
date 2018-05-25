/* eslint-disable */
import Constants from './constants';

const BOARD_API_URL_PATH = '/api/board';

// Mapping Class with BoardController
class BoardApi {

  static insertBoard(board) {
    return $.post({
      url: `${BOARD_API_URL_PATH}/insertBoard`,
      data: JSON.stringify(board),
      headers: Constants.JSON_HEADERS
    });
  }

  static updateBoard(board){
    return $.post({
      url: `${BOARD_API_URL_PATH}/updateBoard`,
      data: JSON.stringify(board),
      headers: Constants.JSON_HEADERS
    });
  }
  static selectBoard(boardNo) {
    return $.get({
      url: `${BOARD_API_URL_PATH}/selectBoard?boardNo=${boardNo}`,
      timeOut: 1000
    })
  }

  static selectAllBoard() {
    return $.get({
      url: `${BOARD_API_URL_PATH}/selectAllBoard`,
      timeOut: 1000
    })
  }

  static deleteBoard(boardNo) {
    return $.get({
      url: `${BOARD_API_URL_PATH}/deleteBoard?boardNo=${boardNo}`,
      timeOut: 1000
    })
  }
}

export default BoardApi;
