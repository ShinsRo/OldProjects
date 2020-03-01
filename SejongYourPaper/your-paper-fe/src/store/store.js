import Vue from 'vue'
import Vuex from 'vuex'

import BasicData from './modules/BasicData_module.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    BasicData
  }
})

export default store
