
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'
import { WokSearchClient } from '../../../public/apis/api/wos-api'

const state = {
  memberPaper: {},
  searchPaperOnWOS: {},
  apiObject: {},
  WOSObject: {},
  myPaperList: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18],
  searchPaperPage: 0,
  memberPaperPage: null,
  endPage: -1,
  citeCounter: 0
}

const getters = {
  MEMBER_OBJECT_GETTER (state) {
    return state.apiObject
  },
  WOS_OBJECT_GETTER (state) {
    return state.WOSObject
  },
  SEARCH_PAGE_GETTER (state) {
    return state.searchPaperPage
  },
  MEMBER_PAPER_GETTER (state) {
    return state.memberPaper
  },
  MEMBER_PAGING_COUNT_GETTER (state) {
    return state.memberPaperPage
  },
  SEARCH_ON_WOS_GETTER (state) {
    return state.searchPaperOnWOS
  },
  CITE_COUNTER_GETTER (state) {
    return state.citeCounter
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
  WOS_OBJECT_SET_MUTATION (state) {
    const SERVER_URL = 'http://www.siotman.com:9400/'
    state.WOSObject = new WokSearchClient(SERVER_URL)
  },
  MEMBER_PAPER_MUTATION (state, payload) {
    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords(payload)
      return 1
    }).catch(error => {
      console.log(error)
    })
  },
  MEMBER_PAPER_PAGING_MUTATION (state, page, payload) {
    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(page, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords(payload)
      console.log('store')
      return 1
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
  SEARCH_PAGING_MUTATION (state, payload) {
    state.searchPaperPage = payload
  },
  MEMBER_PAGING_MUTATION (state, payload) {
    state.memberPaper = payload
  },
  MEMBER_PAGING_COUNT_MUTATION (state) {
    state.memberPaperPage = state.apiObject.getPageState().endPage
  },
  CITE_COUNT_MUTATION (state, payload) {
    state.citeCounter = payload
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
  }, // api 객체 생성 action
  WOS_OBJECT_SET_ACTION (context) {
    context.commit('WOS_OBJECT_SET_MUTATION')
  }, // WOS api 객체 생성 action
  MEMBER_PAPER_ACTION (context, payload) {
    context.commit('MEMBER_PAPER_MUTATION', payload)
  },
  MEMBER_PAPER_PAGING_ACTION (context, payload, page) {
    context.commit('MEMBER_PAPER_PAGING_MUTATION', page, payload)
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
  SEARCH_PAGING_ACTION (context, payload) {
    context.commit('SEARCH_PAGING_MUTATION', payload)
  },
  MEMBER_PAGING_ACTION (context, payload) {
    context.commit('MEMBER_PAGING_MUTATION', payload)
  },
  MEMBER_PAGING_COUNT_ACTION (context) {
    context.commit('MEMBER_PAGING_COUNT_MUTATION')
  },
  CITE_COUNT_ACTION (context, payload) {
    context.commit('CITE_COUNT_MUTATION', payload)
  },
  CLEAR_STORE_ACTION (context) {
    context.commit('CLEAR_STORE_MUTATION')
  }, // 로그아웃시 스토어 클리어 action
}
export default {
  state,
  mutations,
  actions,
  getters
}
