import axios from 'axios'

/*const ADD_PIN_LECTURE = 'ADD_PIN_LECTURE';*/

const GET_MY_PAPER = 'GET_MY_PAPER';
const REQUEST_MY_PAPER = 'REQUEST_MY_PAPER';

const state ={
  myPaper: {},
};

const mutations = {
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
  REQUEST_MY_PAPER(state){

    axios.post('http://www.siotman.com:19401/myPaper/listByPage', {
        username: 'admin',
        sortBy: 'paper.title',
        isAsc: true,
        page: 0,
        count: 10,},
      {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4=',
          'Content-Type': 'application/json'
        } })
      .then(response => {

        state.myPaper = response.data;
        console.log(state.myPaper);
      })
      .catch(error =>{
        console.log(error);
      })

  },
};

const actions ={
  REQUEST_MY_PAPER({commit}, encoding_data){
    commit(REQUEST_MY_PAPER, encoding_data)

  }
};

const getters = {
  GET_MY_PAPER: state => {
    return state.myPaper;
  },
};

export default{
  state,
  mutations,
  actions,
  getters
}
