import Vue from 'vue'
import Vuex from 'vuex'
import getters from './store/getters'
import mutations from './store/mutations'
import actions from './store/actions'

Vue.use(Vuex)

const state = {
  userId: '',
  username: '',
  authorName: [],
  token: false
}

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
})
