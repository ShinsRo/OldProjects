import axios from 'axios'

/*const ADD_PIN_LECTURE = 'ADD_PIN_LECTURE';*/

const GET_MY_PAPER = 'GET_MY_PAPER';
const REQUEST_MY_PAPER = 'REQUEST_MY_PAPER';

const state ={
  memberInfo: {
    username: '',
    encodingAuthorization: '',
    memberInfoDto: {}
  }
};

const mutations = {
  memberInfoMutation (state, payload) {
    state.memberInfo.username = payload.username;
    state.memberInfo.memberInfoDto = payload.memberInfoDto
  },
  memberInfoResetMutation (state) {
    state.memberInfo.username = '';
    state.memberInfo.encodingAuthorization = '';
    state.memberInfo.memberInfoDto = {}
  },
  encodingMutation (state, payload) {
    state.memberInfo.encodingAuthorization = payload
  },
  /*ADD_PIN_LECTURE(state, subject){
    let result = state.pin_lectures.findIndex(lecture => lecture.code === subject.code);
    if(result === -1){
      result = state.option_lectures.findIndex(lecture => lecture.code === subject.code);

      if(result !== -1){
        alert('해당 고정강의는 이미 선택강의로 추가되었습니다.');
        subject.out = {
          status : "failed"
        };
        return;
      }

      let overlap = false;

      for (let i=0; i<state.pin_lectures.length; i++){

        for (let j=0; j<state.pin_lectures[i].timetable.length; j++){

          let pin_lecture_time = state.pin_lectures[i].timetable[j];

          const pin_start = pin_lecture_time.start.replace(/:/gi, '');
          const pin_end = pin_lecture_time.end.replace(/:/gi, '');

          for(let k=0; k<subject.timetable.length; k++){
            let subject_time = subject.timetable[k];

            if (pin_lecture_time.day !== subject_time.day) continue;

            const subject_start = subject_time.start.replace(/:/gi, '');
            const subject_end = subject_time.end.replace(/:/gi, '');

            if ((pin_start < subject_end && pin_end > subject_start) || (pin_end > subject_start && pin_end < subject_end)){
              overlap = true;
              break;
            }
          }
          if (overlap === true) break;
        }
        if (overlap === true) break;
      }

      if (overlap !== true){
        state.pin_lectures.push(subject);
        subject.out = {
          status : "succeed"
        }
      }
      else {
        alert('해당 시간표에 이미 고정강의가 추가되었습니다.');
        subject.out = {
          status : "failed"
        }
      }
    }
    else{
      alert('해당 학수번호는 이미 고정강의에 추가되었습니다.');
      subject.out = {
        status : "failed"
      }
    }
  },*/
  REQUEST_MY_PAPER(state, encoding_data){

  },
};

const actions ={
  loginAction (context, payload) {
    context.commit('memberInfoMutation', payload)
  },
  encodingAction (context, payload) {
    context.commit('encodingMutation', payload)
  },
  logoutAction (context) {
    context.commit('memberInfoResetMutation')
  },
  getMyPaperAction (context) {
    axios({
      method: 'POST',
      url: 'http://www.siotman.com:19401/myPaper/listAll',
      data: { username: context.state.memberInfo.username },
      headers: {
        'Authorization': context.state.memberInfo.encodingAuthorization,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      context.state.memberPaper = res.data
    }).catch(error => {
      console.log(error)
    })
  },
  REQUEST_MY_PAPER({commit}, encoding_data){
    commit(REQUEST_MY_PAPER, encoding_data)
  }
};

const getters = {
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
  }
};

export default{
  state,
  mutations,
  actions,
  getters,
}
