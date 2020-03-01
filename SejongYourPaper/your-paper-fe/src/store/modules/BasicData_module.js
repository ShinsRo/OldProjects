
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'
import { WokSearchClient } from '../../../public/apis/api/wos-api'

const state = {
  memberPaper: {},
  searchPaperOnWOS: {},
  apiObject: {},
  WOSObject: {},
  searchPaperPage: 0,
  memberPaperPage: null,

  // refactoring
  componentFlag: 1, // component 전환 flag ( 1: search my paper / 2: paper statics / 3: paper edit )
  searchFlag: 0,
  optionByComponent : [[],                              // 0: empty
    [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18],      // 1: option for search my paper
    [3, 4, 5, 6, 7, 8, 9, 13, 10, 15, 17, 18, 1, 2, 0], // 2: option for paper statics
    [1, 3, 4, 6, 7, 9]],                                // 3: option for paper edit
  endPage: -1,
  citeCounter: 0,
  grade: [{'SCI': 1}, {'SCIE': 1}, {'SPCIS': 1}, {'NONE': 1}],
  authorType: [],
  sciGrade: 0,
  scieGrade: 0,
  scpisGrade: 0,
  noneGrade: 10,
  reprintPaper: 0,
  generalPaper: 0,
  refPaper: 0,
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

  // refactoring
  PAGE_FLAG_GETTER (state){
    return state.componentFlag
  },
  SEARCH_FLAG_GETTER (state){
    return state.searchFlag
  },
  MEMBER_PAPER_GETTER (state) {
    return state.memberPaper
  },
  GRADE_GETTER (state) {
    return state.grade
  },
  SCI_GRADE_GETTER (state) {
    return state.sciGrade
  },
  SCIE_GRADE_GETTER (state) {
    return state.scieGrade
  },
  SCPIS_GRADE_GETTER (state) {
    return state.scpisGrade
  },
  NONE_GRADE_GETTER (state) {
    return state.noneGrade
  },
  AUTHOR_TYPE_GETTER (state) {
    return state.authorType
  },
  REPRINT_COUNT_GETTER (state) {
    return state.reprintPaper
  },
  GENERAL_COUNT_GETTER (state) {
    return state.generalPaper
  },
  REF_COUNT_GETTER (state) {
    return state.refPaper
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
      console.log(payload)
    }).catch(error => {
      console.log(error)
    })
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
  },
  // refactoring
  SET_PAGE_MUTATION (state, page){
    state.componentFlag = page
  },
  SET_SEARCH_FLAG_MUTATION (state){
    state.searchFlag = 0
  },
  NEW_MEMBER_PAPER_MUTATION (state, {count, orderBy, criteria} ){
    state.apiObject.listByPage(1, count, orderBy, true, criteria).then(res => {
      state.memberPaper = state.apiObject.getRecords(state.optionByComponent[state.componentFlag])
    }).catch(error => {
      console.log(error)
    })
  }, // 넘겨받은 조건으로 첫 페이지 불러오기
  MEMBER_PAPER_PAGING_MUTATION (state, page) {
    state.apiObject.retrive(page).then(res => {
      state.memberPaper = state.apiObject.getRecords(state.optionByComponent[state.componentFlag])
      return 1
    }).catch(error => {
      console.log(error)
    })
  },
  SET_END_PAGE_MUTATION (state, {count, criteria}) {

    state.apiObject.listByPage(1, count, FIELD.TITLE, true, criteria).then(res => {
      console.log('new')
      state.endPage = state.apiObject.getPageState().endPage + 1
    }).catch(error => {
      console.log(error)
    })
  },
  SEARCH_MY_PAPER_MUTATION (state, criteria){
    state.searchFlag = 1
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, criteria).then(res => {
      state.memberPaper = state.apiObject.getRecords(state.optionByComponent[state.componentFlag])
    }).catch(error => {
      console.log(error)
    })
  },
  COMPUTE_MY_PAPER_INFO_MUTATION (state, {count, criteria}){
    let page = 1
    let grade = [
      ['SCI', 0],
      ['SCIE', 0],
      ['SCPIS', 0],
      ['NONE', 0]
    ]
    let authorType = [
      ['REPRINT', 0],
      ['GENERAL', 0],
      ['REFFERING', 0]
    ]

    while(state.endPage > page){
      state.apiObject.listByPage(page, count, FIELD.TITLE, true, criteria).then(res => {

        state.loadPaper = state.apiObject.getRecords(state.optionByComponent[state.componentFlag])
        for(let i = 0; i< state.loadPaper.length ;i++){
          switch (state.loadPaper[i][3]){
            case 'REPRINT':
              authorType[0][1] += 1
              state.reprintPaper += 1
              break
            case 'GENERAL':
              authorType[1][1] += 1
              state.generalPaper += 1
              break
            case 'REFFERING':
              authorType[2][1] += 1
              state.refPaper += 1
              break
          }

          if(state.loadPaper[i][3] !== 'REFFERING'){
            switch (state.loadPaper[i][9]) {
              case 'SCI':
                grade[0][1] += 1
                state.sciGrade += 1
                break
              case 'SCIE':
                grade[1][1] += 1
                state.scieGrade += 1
                break
              case 'SCPIS':
                grade[2][1] += 1
                state.scpisGrade += 1
                break
              default:
                grade[3][1] += 1
                state.noneGrade += 1
                break
            }
          }
        }
      }).catch(error => {
        console.log(error)
      })
      state.grade= grade

      state.authorType = authorType
      console.log(grade, state.authorType)
      page += 1
    }
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

  // refactoring
  SET_PAGE_ACTION (context, page) {
    context.commit('SET_PAGE_MUTATION', page)
  },
  SET_SEARCH_FLAG_ACTION(context) {
    context.commit('SET_SEARCH_FLAG_MUTATION')
  },
  NEW_MEMBER_PAPER_ACTION (context, {count, orderBy, criteria}){
    context.commit('NEW_MEMBER_PAPER_MUTATION', {count, orderBy, criteria})
  },
  MEMBER_PAPER_PAGING_ACTION (context, page ) {
    context.commit('MEMBER_PAPER_PAGING_MUTATION', page)
  }, // 내 논문 불러오기
  SET_END_PAGE_ACTION (context, {count, criteria}){
    context.commit('SET_END_PAGE_MUTATION', {count, criteria})
  },
  SEARCH_MY_PAPER_ACTION (context, criteria){
    context.commit('SEARCH_MY_PAPER_MUTATION', criteria)
  },
  COMPUTE_MY_PAPER_INFO_ACTION (context, {count, criteria}){
    context.commit('COMPUTE_MY_PAPER_INFO_MUTATION', {count, criteria})
  }
}
export default {
  state,
  mutations,
  actions,
  getters
}
