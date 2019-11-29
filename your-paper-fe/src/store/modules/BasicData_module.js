
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
    console.log('member object set mutataion start')
    const token = sessionStorage.getItem('token')
    const Session = JSON.parse(sessionStorage.getItem('data'))
    state.apiObject = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:9401/')
    console.log('member object set mutataion end')
  },
  MEMBER_PAPER_MUTATION (state) {
    console.log('member paper mutation start')
    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(0, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords([3, 4, 6, 7, 9])
      console.log(state.memberPaper)
    }).catch(error => {
      console.log(error)
    })
    console.log('member paper mutation end')
  },
  SEARCH_TRIGGER_MUTATION (state) {
    console.log('search trigger mutation start')
    state.searchTrigger = true
    console.log('search trigger mutation end')
  },
  // -----------------------------------------
  MEMBER_INFO_SET_MUTATION (state) {
    const token = sessionStorage.getItem('token')
    const Session = JSON.parse(sessionStorage.getItem('data'))

    state.apiObject = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:9401/')
    this.dispatch('GET_MY_PAPER_ACTION', state.apiObject)
  },
  CLEAR_STORE_MUTATION (state) {
    state.memberPaper = {}
    state.apiObject = {}
  }
}

const actions = {
  MEMBER_OBJECT_SET_ACTION (context) {
    context.commit('MEMBER_OBJECT_SET_MUTATION')
  },
  MEMBER_PAPER_ACTION (context) {
    context.commit('MEMBER_PAPER_MUTATION')
  },
  SEARCH_TRIGGER_ACTION (context) {
    context.commit('SEARCH_TRIGGER_MUTATION')
  },
  // ----------------------------------------------
  MEMBER_INFO_SET_ACTION (context) {
    console.log('Member_Info_Set_Action Start')
    context.commit('MEMBER_INFO_SET_MUTATION')
    console.log('Member_Info_Set_Action End')
  },
  GET_MY_PAPER_ACTION (context, payload) {
    context.commit('GET_MY_PAPER_MUTATION', payload)
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
