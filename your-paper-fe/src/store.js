import Vue from 'vue'
import Vuex from 'vuex'
import getter from './store/getters'
import mutation from './store/mutations'
import action from './store/actions'

Vue.use(Vuex)

const state = {
  username: '',
  userAuthName: {},
  organization: '',
  isAuth: false
}

export default new Vuex.Store({
  state,
  getter,
  mutation,
  action
})
