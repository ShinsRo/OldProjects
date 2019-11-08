import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    memberInfoDto: {}
  },
  getters: {
  },
  mutations: {
    setUserInfo (state, payload) {
      state.memberInfoDto = payload.memberInfoDto
    }
  },
  actions: {
    loginAction (context, payload) {
      context.commit('setUserInfo', payload)
    }
  }
})
