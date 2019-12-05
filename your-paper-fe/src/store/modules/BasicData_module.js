
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'

const state = {
  memberPaper: {},
  searchPaperOnWOS: {},
  apiObject: {},
  endPage: -1,
}

const getters = {
  MEMBER_OBJECT_GETTER (state) {
    return state.apiObject
  },
  MEMBER_PAPER_GETTER (state) {
    return state.memberPaper
  },
  SEARCH_ON_WOS_GETTER (state) {
    return state.searchPaperOnWOS
  },
  END_PAGE_GETTER (state){
    return state.endPage
  },
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
      return 1;
    }).catch(error => {
      console.log(error)
    })
  },
  SET_END_PAGE_MUTATION (state, value) {
    const criteria = { field: FIELD.AUTHOR_TYPE, operation: CRITERIA.LIKE, value: value}
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.endPage = state.apiObject.getPageState().endPage + 1
    }).catch(error => {
      console.log(error)
    })
  },
  ALL_PAPER_MUTATION (state, {payload, value}) {
    let page = 1
    state.memberPaper = []
    const criteria = { field: FIELD.AUTHOR_TYPE, operation: CRITERIA.LIKE, value: value }
    while(state.endPage > page){
      state.apiObject.listByPage(page, 10, FIELD.TITLE, true, [criteria]).then(res => {
        state.memberPaper.push(state.apiObject.getRecords(payload))
      }).catch(error => {
        console.log(error)
      })
      page += 1
    }
  },
  SEARCH_ON_WOS_MUTATION (state, payload) {
    state.searchPaperOnWOS = payload
  },
  PAGING_MUTATION (state, payload) {
    state.memberPaper = payload
  },
  SEARCH_MY_PAPER_MUTATION (state, {payload, criteria}){
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, criteria).then(res => {
      state.memberPaper =state.apiObject.getRecords(payload)
      console.log('searched')
    }).catch(error => {
      console.log(error)
    })
  },
  CLEAR_STORE_MUTATION (state) {
    state.memberPaper = {}
    state.searchPaperOnWOS = {}
    state.apiObject = {}
  }
}

const actions = {
  MEMBER_OBJECT_SET_ACTION (context) {
    context.commit('MEMBER_OBJECT_SET_MUTATION')
  },
  MEMBER_PAPER_ACTION (context, payload) {
    context.commit('MEMBER_PAPER_MUTATION', payload)
  }, // 내 논문 불러오기
  ALL_PAPER_ACTION (context,  {payload, value}) {
    context.commit('ALL_PAPER_MUTATION', {payload, value})
  }, // 내 논문 불러오기
  SET_END_PAGE_ACTION (context, value){
    context.commit('SET_END_PAGE_MUTATION', value)
  },
  SEARCH_ON_WOS_ACTION (context, payload) {
    context.commit('SEARCH_ON_WOS_MUTATION', payload)
  },
  PAGING_ACTION (context, payload) {
    context.commit('PAGING_MUTATION', payload)
  },
  SEARCH_MY_PAPER_ACTION (context, {payload, criteria}){
    context.commit('SEARCH_MY_PAPER_MUTATION', {payload, criteria})
  },
  CLEAR_STORE_ACTION (context) {
    context.commit('CLEAR_STORE_MUTATION')
  }
}
export default {
  state,
  mutations,
  actions,
  getters
}
