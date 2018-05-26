import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router'

import axios from 'axios'
import Form from 'form-data'

import Constants from '../common/js/constants'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    appTitle: 'Midas Cafe',
    user: JSON.parse(sessionStorage.getItem('user')),
    error: null,
    loading: false,
    usingModal: false,
    userCart: []
  },
  mutations: {
    setUser (state, payload) {
      sessionStorage.setItem('user', JSON.stringify(payload))
      state.user = payload
    },
    setError (state, payload) {
      if (payload !== null && state.usingModal) alert(payload)
      state.error = payloa
    },
    setLoading (state, payload) {
      state.loading = payload
    },
    cartAppend (state, payload) {
      state.userCart.push(payload)
    }
  },
  getters: {
    getSessionAttribute (state) {
      return state.user
    },
    isAuthenticated (state) {
      return (state.user !== null && state.user !== undefined) ? state.user.auth : 9
    },
    getCart (state) {
      return state.userCart
    }
  },
  actions: {
    addToCart ({commit}, payload) {
      commit('cartAppend', payload)
    },
    commonGET ({commit}, payload) {
      commit('setLoading', true)
      console.log('common : ' + this.state.user.email)
      axios.get(payload.url, payload.param, {headers: {'auto': this.state.user.email}}).then((response) => {
        console.log(response.data)
        commit('setLoading', false)
        return response.data
      }).catch((error) => {
        console.log('store : error' + error.response.data)
        commit('setError', error.response.data.message)
        commit('setLoading', false)
        return error.response.data
      })
      commit('setLoading', false)
    },
    /* 이하 유저 */
    userSignUp ({commit}, payload) {
      commit('setLoading', true)
      let bodyFormData = new Form()

      bodyFormData.append('email', payload.email)
      bodyFormData.append('password', payload.password)
      bodyFormData.append('name', payload.name)
      bodyFormData.append('phoneNumber', payload.phoneNumber)
      axios({
        method: 'post',
        url: Constants.USER_API_URL_PATH + '/signup',
        data: bodyFormData,
        config: {headers: {'Content-Type': 'multipart/form-data'}}
      }).then((response) => {
        commit('setUser', response.data.data)
        commit('setLoading', false)
        commit('setError', null)
        router.push('/home')
        return 'success'
      }).catch((error) => {
        commit('setError', error.response.data.message)
        commit('setError', error.response.data.message)
        commit('setLoading', false)
        return 'fail'
      })
    },
    userSignIn ({commit}, payload) {
      let bodyFormData = new Form()

      bodyFormData.append('email', payload.email)
      bodyFormData.append('password', payload.password)
      axios({
        method: 'post',
        url: Constants.USER_API_URL_PATH + '/signin',
        data: bodyFormData,
        config: {headers: {'Content-Type': 'multipart/form-data'}}
      }).then((response) => {
        const user = response.data.data
        if (user === undefined) {
          throw new Error('존재하지 않는 이메일입니다.')
        }
        if (user.status === 'USER') user.auth = 0
        else if (user.status === 'SUB_ADMIN') user.auth = 1
        else if (user.status === 'ADMIN') user.auth = 2
        switch (user.auth) {
          case 0:
            router.push('/home')
            break
          case 1:
          case 2:
            router.push('/admin/home')
            break
          default : throw new Error('알 수 없는 사용자')
        }
        commit('setUser', user)
        commit('setLoading', false)
        commit('setError', null)
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
