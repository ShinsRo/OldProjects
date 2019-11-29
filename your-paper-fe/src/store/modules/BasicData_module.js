
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'
// const LOGIN_ACTION = 'LOGIN_ACTION'
// const ENCODING_ACTION = 'ENCODING_ACTION'
// const LOGOUT_ACTION = 'LOGOUT_ACTION'
// const LOAD_MY_PAPER_MUTATION = 'LOAD_MY_PAPER_MUTATION'

const state = {
  memberPaper: {},
  apiObject: {},
  searchTrigger: false
}

const getters = {
  MEMBER_OBJECT_GETTER (state) {
    return state.apiObject
  },
  MEMBER_PAPER_GETTER (state) {
    return state.memberPaper
  },
  SEARCH_TRIGGER_GETTER (state) {
    return state.searchTrigger
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
      // state.memberPaper = state.apiObject.getRecords([3, 4, 6, 7, 9])
      state.memberPaper = state.apiObject.getRecords(payload)
    }).catch(error => {
      console.log(error)
    })
  },
  SEARCH_MY_PAPER_MUTATION (state, payload) {

  },
  SEARCH_TRIGGER_MUTATION (state) {
    state.searchTrigger = true
  }
}

const actions = {
  MEMBER_OBJECT_SET_ACTION (context) {
    context.commit('MEMBER_OBJECT_SET_MUTATION')
  },
  MEMBER_PAPER_ACTION (context, payload) {
    context.commit('MEMBER_PAPER_MUTATION', payload)
  }, // 내 논문 불러오기
  SEARCH_MY_PAPER_ACTION (context, payload) {
    context.commit('SEARCH_MY_PAPER_MUTATION', payload)
  }, // 내 논문 검색하기
  SEARCH_TRIGGER_ACTION (context) {
    context.commit('SEARCH_TRIGGER_MUTATION')
  }
}
export default {
  state,
  mutations,
  actions,
  getters
}
