import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router'

import axios from 'axios'
import Form from 'form-data'

Vue.use(Vuex)

/* back-end devServer URL */
const BackEndServerURL = 'http://localhost:8888'

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
      console.log(bodyFormData)
      axios({
        method: 'post',
        url: BackEndServerURL + '/userRegister.do',
        data: bodyFormData,
        /* 사진 등 파일 전송용 헤더 */
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
