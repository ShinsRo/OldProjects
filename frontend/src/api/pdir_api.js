
import { URL } from '../stores/modules/API_CONSTANT';
import { request, receive, pusherr, updateBreadcrumb } from "../stores/modules/projectState";
import axios from 'axios';

// api
export const project_api = {
    list(mid) {
        return (dispatch) => {
            dispatch(request());
            return axios.get(`${URL.PROJECT.LIST}?mid=${mid}`)
                .then   (res => { dispatch(receive(res.data)); dispatch(updateBreadcrumb( [mid, "내 프로젝트"] )); })
                .catch  (err => { dispatch(pusherr(err)) });
        }
    },
    register(pDto) {
        return (dispatch) => {
            dispatch(request());
            return axios.post(`${URL.PROJECT.REGISTER}`, pDto)
                .then   (res => { dispatch(receive(res.data)); })
                .catch  (err => { dispatch(pusherr(err)) });
        };
    },
    correct(pDto) { axios.put(`${URL.PROJECT.CORRECT}`, pDto) },
    disable(pDto) { axios.patch(`${URL.PROJECT.DISABLE}`, pDto) },
};

export const pdir_api = {
    list: mid => axios.get(`${URL.PROJECT.LIST}?pid=${mid}`),
    register: pDto => axios.post(`${URL.PROJECT.REGISTER}`, pDto),
    correct: pDto => axios.put(`${URL.PROJECT.CORRECT}`, pDto),
    disable: pDto => axios.patch(`${URL.PROJECT.DISABLE}`, pDto),
};