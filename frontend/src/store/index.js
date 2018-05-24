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
    user: JSON.parse(sessionStorage.getItem('user')),
    error: null,
    loading: false
  },
  mutations: {
    setUser (state, payload) {
      sessionStorage.setItem('user', JSON.stringify(payload))
      state.user = payload
    },
    setError (state, payload) {
      state.error = payload
    },
    setLoading (state, payload) {
      state.loading = payload
    }
  },
  getters: {
    getSessionAttribute (state) {
      return state.user
    },
    isAuthenticated (state) {
      return state.user !== null && state.user !== undefined
    }
  },
  actions: {
    userSignUp ({commit}, payload) {
      commit('setLoading', true)
      let bodyFormData = new Form()

      bodyFormData.append('email', payload.email)
      bodyFormData.append('password', payload.password)
      bodyFormData.append('name', payload.name)
      axios({
        method: 'post',
        url: Constants.USER_API_URL_PATH + '/userRegister',
        data: bodyFormData,
        config: {headers: {'Content-Type': 'multipart/form-data'}}
      }).then((response) => {
        commit('setUser', response.data.data)
        commit('setLoading', false)
        commit('setError', null)
        router.push('/home')
      }).catch((error) => {
        commit('setError', error.response.data.message)
        commit('setLoading', false)
      })
    },
    userSignIn ({commit}, payload) {
      let bodyFormData = new Form()

      bodyFormData.append('email', payload.email)
      bodyFormData.append('password', payload.password)
      axios({
        method: 'post',
        url: Constants.USER_API_URL_PATH + '/userSignin',
        data: bodyFormData,
        config: {headers: {'Content-Type': 'multipart/form-data'}}
      }).then((response) => {
        if (response.data.data.user === undefined) {
          throw new Error('존재하지 않는 이메일입니다.')
        } else if (!response.data.data.isValid) {
          throw new Error('비밀번호가 일치하지 않습니다.')
        }
        commit('setUser', response.data.data.user)
        commit('setLoading', false)
        commit('setError', null)
        router.push('/home')
      }).catch((error) => {
        commit('setError', error.message)
        commit('setLoading', false)
      })
    },
    userSignOut ({commit}) {
      commit('setUser', null)
      sessionStorage.setItem('user', null)
      router.push('/')
    }
  }
})
