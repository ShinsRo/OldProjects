import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router'

import axios from 'axios'
import Form from 'form-data'

import Constants from '../common/js/constants'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    appTitle: 'Midas App',
    user: null,
    error: null,
    loading: false
  },
  mutations: {
    setUser (state, payload) {
      state.user = payload
    },
    setError (state, payload) {
      state.error = payload
    },
    setLoading (state, payload) {
      state.loading = payload
    }
  },
  actions: {
    userSignUp ({commit}, payload) {
      commit('setLoading', true)
      let bodyFormData = new Form()

      bodyFormData.append('email', payload.email)
      bodyFormData.append('password', payload.password)
      bodyFormData.append('name', payload.name)
      console.log(bodyFormData)
      axios({
        method: 'post',
        url: Constants.MEMBER_API_URL_PATH + '/userRegister',
        data: bodyFormData,
        config: {headers: {'Content-Type': 'multipart/form-data'}}
      }).then((response) => {
        commit('setUser', response.email)
        commit('setLoading', false)
        router.push('/home')
      }).catch((error) => {
        commit('setError', error.message)
        commit('setLoading', false)
      })
    }
  }
})
