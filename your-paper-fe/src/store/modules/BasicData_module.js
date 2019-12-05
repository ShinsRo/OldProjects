
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'
// import Axios from 'axios'
// const LOGIN_ACTION = 'LOGIN_ACTION'
// const ENCODING_ACTION = 'ENCODING_ACTION'
// const LOGOUT_ACTION = 'LOGOUT_ACTION'
// const LOAD_MY_PAPER_MUTATION = 'LOAD_MY_PAPER_MUTATION'

const state = {
  memberPaper: {},
  searchPaperOnWOS: {},
  apiObject: {},
  myPaperList: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18]
}

const getters = {
  MEMBER_OBJECT_GETTER (state) {
    return state.apiObject
  },
  MEMBER_PAPER_GETTER (state) {
    /*
    let paperData = []
    let data = {}
    for(let i = 0; i<state.memberPaper.length; i++){
      data = []
      for(let j = 0; j< state.myPaperList.length;j++){
        data.push(state.memberPaper[i][j])
      }
      paperData.push(data)
    }*/

    return state.memberPaper
  },
  SEARCH_ON_WOS_GETTER (state) {
    return state.searchPaperOnWOS
  }
}

const mutations = {
  MEMBER_OBJECT_SET_MUTATION (state) {
    const token = sessionStorage.getItem('token')
    const Session = JSON.parse(sessionStorage.getItem('data'))
    state.apiObject = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:9401/')
  },

  MEMBER_PAPER_MUTATION (state, payload) {
    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords(payload)
      console.log('store')
      return 1;
    }).catch(error => {
      console.log(error)
    })
  },
  MEMBER_PAPER_PAGING_MUTATION (state, page, payload) {
    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(page, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords(payload)
      console.log('store')
      return 1;
    }).catch(error => {
      console.log(error)
    })
  },

  SEARCH_ON_WOS_MUTATION (state, payload) {
    state.searchPaperOnWOS = payload
  }
}

const actions = {
  MEMBER_OBJECT_SET_ACTION (context) {
    context.commit('MEMBER_OBJECT_SET_MUTATION')
  },
  MEMBER_PAPER_ACTION (context, payload) {
    context.commit('MEMBER_PAPER_MUTATION', payload)
  }, // 내 논문 불러오기
  MEMBER_PAPER_PAGING_ACTION (context,  payload, page) {
    context.commit('MEMBER_PAPER_PAGING_MUTATION', page, payload)
  }, // 내 논문 불러오기
  SEARCH_ON_WOS_ACTION (context, payload) {
    context.commit('SEARCH_ON_WOS_MUTATION', payload)
  }
}
export default {
  state,
  mutations,
  actions,
  getters
}
