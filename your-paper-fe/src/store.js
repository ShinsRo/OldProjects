import Vue from 'vue'
import Vuex from 'vuex'
import { SORT_MP_ENUM, PaperRecordContainer } from '../public/apis/api/paper-api.js'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    memberInfo: {
      username: '',
      encodingAuthorization: '',
      memberInfoDto: {}
    },
    memberPaper: {}
  },
  getters: {
    memberInfoGetter (state) {
      return state.memberInfo
    }, // memberInfo 정보를 가져오는 getter
    memberInfoUserNameGetter (state) {
      return state.memberInfo.username
    }, // memberInfo내 username을 가져오는 getter
    memberInfoDtoGetter (state) {
      return state.memberInfo.memberInfoDto
    }, // memberInfo내 memberInfoDto를 가져오는 getter
    memberInfoEncodingGetter (state) {
      return state.memberInfo.encodingAuthorization
    },
    memberPaperGetter (state) {
      return state.memberPaper
    }
  },
  mutations: {
    memberInfoMutation (state, payload) {
      state.memberInfo.username = payload.username
      state.memberInfo.memberInfoDto = payload.memberInfoDto
    },
    memberInfoResetMutation (state) {
      state.memberInfo.username = ''
      state.memberInfo.encodingAuthorization = ''
      state.memberInfo.memberInfoDto = {}
    },
    encodingMutation (state, payload) {
      state.memberInfo.encodingAuthorization = payload
    },
    loadMyPaperMutation (state, payload) {
      state.memberPaper = payload
    }
  },
  actions: {
    loginAction (context, payload) {
      context.commit('memberInfoMutation', payload)
    },
    encodingAction (context, payload) {
      context.commit('encodingMutation', payload)
    },
    logoutAction (context) {
      context.commit('memberInfoResetMutation')
    },
    loadMyPaperAction (context, page) {
      const token = sessionStorage.getItem('token')
      const Session = JSON.parse(sessionStorage.getItem('data'))

      const paperContainer = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:19401/')
      paperContainer.listByPage(page, 10, SORT_MP_ENUM.TITLE, true).then(res => {
        context.commit('loadMyPaperMutation', paperContainer)
      }).catch(error => {
        console.log(error)
      })
    }
  }
})
