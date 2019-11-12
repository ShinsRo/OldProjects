import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

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
    loadMyPaperAction (context) {
      axios({
        method: 'POST',
        // url: 'http://www.siotman.com:19401/myPaper/listAll',
        // data: { username: context.state.memberInfo.username },
        url: 'http://www.siotman.com:19401/myPaper/listByPage', // 페이징 처리 URL
        data: {
          username: context.state.memberInfo.username,
          sortBy: 'paper.title',
          isAsc: true,
          firstRecord: 0,
          count: 50
        },
        headers: {
          'Authorization': context.state.memberInfo.encodingAuthorization,
          'Content-Type': 'application/json'
        }
      }).then(res => {
        context.commit('loadMyPaperMutation', res.data)
      }).catch(error => {
        console.log(error)
      })
    }
  }
})
