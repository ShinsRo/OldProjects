
import { SORT_MP_ENUM, PaperRecordContainer } from '../../../public/apis/api/paper-api.js'

const LOGIN_ACTION = 'LOGIN_ACTION';
const ENCODING_ACTION = 'ENCODING_ACTION';
const LOGOUT_ACTION = 'LOGOUT_ACTION';
const LOAD_MY_PAPER_MUTATION = 'LOAD_MY_PAPER_MUTATION';


const state = {
  memberInfo: {
    username: '',
    encodingAuthorization: '',
    memberInfoDto: {}
  },
  memberPaper: {}
};

const getters = {
  MEMBER_INFO_GETTER (state) {
    return state.memberInfo
  }, // memberInfo 정보를 가져오는 getter
  /*
      GET_RESULT: (state) => {
        return state.result_data;
      }
      memberInfoUserNameGetter (state) {
        return state.memberInfo.username
      }, // memberInfo내 username을 가져오는 getter*/
  MEMBER_INFO_USER_NAME_GETTER (state) {
    return state.memberInfo.username
  },// memberInfo내 username을 가져오는 getter
  MEMBER_INFO_DTO_GETTER (state) {
    return state.memberInfo.memberInfoDto
  }, // memberInfo내 memberInfoDto를 가져오는 getter
  MEMBER_INFO_ENCODING_GETTER (state) {
    return state.memberInfo.encodingAuthorization
  },
  MEMBER_PAPER_GETTER (state) {
    return state.memberPaper
  },
};

const mutations = {
  LOGIN_ACTION (state, payload) {
    state.memberInfo.username = payload.username
    state.memberInfo.memberInfoDto = payload.memberInfoDto
  },
  memberInfoResetMutation (state) {
    state.memberInfo.username = ''
    state.memberInfo.encodingAuthorization = ''
    state.memberInfo.memberInfoDto = {}
  },
  ENCODING_ACTION (state, payload) {
    state.memberInfo.encodingAuthorization = payload
  },
  LOAD_MY_PAPER_MUTATION (state, payload) {
    state.memberPaper = payload
  }
};

const actions =  {
  LOGIN_ACTION (context, payload) {
    context.commit(LOGIN_ACTION, payload)
  },
  ENCODING_ACTION (context, payload) {
    context.commit(ENCODING_ACTION, payload)
  },
  LOGOUT_ACTION (context) {
    context.commit(LOGOUT_ACTION)
  },
  LOAD_MY_PAPER_ACTION (context, page) {
    const token = sessionStorage.getItem('token')
    const Session = JSON.parse(sessionStorage.getItem('data'))

    const paperContainer = new PaperRecordContainer(Session.username, token, 'http://www.siotman.com:19401/')
    paperContainer.listByPage(page, 10, SORT_MP_ENUM.TITLE, true).then(res => {
      context.commit(LOAD_MY_PAPER_MUTATION, paperContainer)
    }).catch(error => {
      console.log('hi',error)
    })
  }
};

export default{
  state,
  mutations,
  actions,
  getters,
}

/*state: {
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

  GET_RESULT: (state) => {
    return state.result_data;
  }
  memberInfoUserNameGetter (state) {
    return state.memberInfo.username
  }, // memberInfo내 username을 가져오는 getter
  MEMBER_INFO_USER_NAME_GETTER (state) {
    return state.memberInfo.username
  },// memberInfo내 username을 가져오는 getter
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
}*/
