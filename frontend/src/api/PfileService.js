import axios from 'axios';
import { BASE_URL } from '../supports/API_CONSTANT';

export function savePfile(pfile){
    console.log(pfile);
    return axios.post(`${BASE_URL}/pfile`, pfile);    
}

export function getPfile(pdirId){
    console.log(typeof pdirId);
    return axios.get(`${BASE_URL}/pfile/${pdirId}`);    
}

export function updatePfile(pfile){
    console.log(pfile);
    return axios.put(`${BASE_URL}/pfile`, pfile);    
}

export function deletePfile(pfileId){
    console.log(pfileId);
    return axios.delete(`${BASE_URL}/pfile/${pfileId}`);
}

export function movePfile(pfileId, pdirId) {
    return axios.put(`${BASE_URL}/pfile/move/${pfileId}/${pdirId}`);    
}

export function copyPfile(pfileId, pdirId) {
    return axios.put(`${BASE_URL}/pfile/copy/${pfileId}/${pdirId}`);    
}