import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import axios from 'axios';
import KssTree from '../supports/kss-tree';
import KssAutocompletor from '../supports/kss-autocompletor';
import KssPushMsgReceiver from '../supports/kss-push-msg-receiver';
import { URL } from '../supports/API_CONSTANT';
//Actions
export const REQUEST = 'project/REQUEST';
export const RECEIVE = 'project/RECEIVE';   // { endPoint, items }
export const PUSHERR = 'project/PUSHERR';
export const CONNECT = 'project/CONNECT';

export const request = createAction(REQUEST);
export const receive = createAction(RECEIVE);
export const pusherr = createAction(PUSHERR);
export const connect = createAction(CONNECT);

//init state
const initialState = Map({
    isFetching: false,
    projects: List([]),
    lastUpdated: '',
    errObj: Map({
        msg: '',
        err: '',
        type: '',
        isHandled: true
    }),
    selectedProject: '',
    selectedDirId: '',
    pushMsgReceiver: '',
    dirContainer: new KssTree(),
});

//액션 핸들링
export default handleActions({
    [REQUEST]: (state, action) => { 

        let projectState = state;
        projectState = projectState.set('isFetching', true);

        return projectState;
    },

    [RECEIVE]: (state, action) => {
        const items = action.payload;

        let projectState = state;
        
        for (const key in items) {
            projectState = projectState.set(key, items[key]);
            if (key === 'projects') {
                const dirContainer = projectState.get('dirContainer');
                dirContainer.setProjects(items[key]);
                dirContainer.init();
            }
            if (key === 'members') {
                const memberAutocompletor = new KssAutocompletor("memberAutocompletor", "member", items[key]);
                projectState = projectState.set('memberAutocompletor', memberAutocompletor);
            }
        }

        projectState = projectState.set('receivedAt', Date.now());
        projectState = projectState.set('isFetching', false);

        return projectState;
    },
    [PUSHERR] : (state, action) => {
        const err = action.payload;

        let projectState = state;
        projectState = projectState.set('errObj', Map({
            msg: '프로젝트 관련 정보를 받는 중 통신 오류가 발생했습니다.',
            err: 'conn',
            type: err,
            isHandled: false
        }));

        return projectState;
    },
    [CONNECT] : (state, action) => {
        let projectState = state;
        const pushMsgReceiver = new KssPushMsgReceiver(action.payload.mid);
        
        pushMsgReceiver.connect(state.get('dirContainer'));

        projectState = projectState.set('pushMsgReceiver', pushMsgReceiver);

        return projectState;
    }
}, initialState);

// 단순 접근 api
export function saveItem(items) {
    return (dispatch) => {
        dispatch(receive(items))
    }
}

// 비동기 api
export function list(mid, name) {
        return (dispatch) => {
            dispatch(request());
            dispatch(connect({ mid, }));
            return axios.get(`${URL.PROJECT.LIST}?mid=${mid}`)
                .then   (res => { 
                    dispatch(receive({ projects: res.data, })); 
                })
                .catch  (err => { dispatch(pusherr(err)); });
        }
};
export function setMemberAutocompletor() {
    return (dispatch) => {
        dispatch(request());
        return axios.get(`${URL.MEMBER.LIST_ALL}`)
            .then   (res => { dispatch(receive({ members: res.data, })); })
            .catch  (err => { dispatch(pusherr(err)) });
    }
};

export function register(pDto) { return axios.post(`${URL.PROJECT.REGISTER}`, pDto); };
export function correct(pDto) { return axios.put(`${URL.PROJECT.CORRECT}`, pDto) };
export function disable(pDto) { return axios.put(`${URL.PROJECT.DISABLE}`, pDto) };

export const pdir_api = {
    register: dDto => {
        return axios.post(`${URL.PDIR.REGISTER}`, dDto);
    },
    correct: (dDto, gubun) => {
        return axios.put(`${URL.PDIR.CORRECT}?gubun=${gubun}`, dDto);
    },
    disable: dDto => axios.put(`${URL.PDIR.DISABLE}`, dDto),
};