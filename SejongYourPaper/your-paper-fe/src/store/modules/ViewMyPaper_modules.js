
import { FIELD, PaperRecordContainer, CRITERIA } from '../../../public/apis/api/paper-api.js'

const state = {
  memberPaper: {},
  apiObject: {}
}

const getters = {
  MY_PAPER_GETTER (state) {
    return state.memberPaper
  },
}

const mutations = {

  MY_OBJECT_SET_MUTATION (state) {
    console.log('set action start')
    const token = sessionStorage.getItem('token')
    const Session = JSON.parse(sessionStorage.getItem('data'))
    state.apiObject = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:9401/')
    console.log('set action end')
  },

  MY_PAPER_LOAD_MUTATION (state, payload) {
    console.log('load action start')

    const criteria = { field: FIELD.TITLE, operation: CRITERIA.LIKE, value: ' ' }
    state.apiObject.listByPage(1, 10, FIELD.TITLE, true, [criteria]).then(res => {
      state.memberPaper = state.apiObject.getRecords(payload)
      console.log('My paper load mutation', state.memberPaper)
    }).catch(error => {
      console.log(error)
    })
    console.log('load action end')
  },
}

const actions = {
  MY_OBJECT_SET_ACTION (context) {
    context.commit('MY_OBJECT_SET_MUTATION')
  },
  MY_PAPER_LOAD_ACTION (context, payload) {
    context.commit('MY_PAPER_LOAD_MUTATION', payload)
  }, // 내 논문 불러오기
}
export default {
  state,
  mutations,
  actions,
  getters
}
