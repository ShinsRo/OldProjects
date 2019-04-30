import axios from 'axios';
import { BASE_URL } from '../supports/API_CONSTANT';

export function savePfile(pfile){
    console.log(pfile);
    return axios.post(`${BASE_URL}/pfile`, pfile);    
}

export function getPfile(dirId){
    console.log(typeof dirId);
    return axios.get(`${BASE_URL}/pfile/${dirId}`);    
}

export function updatePfile(pfile){
    console.log(pfile);
    return axios.put(`${BASE_URL}/pfile`, pfile);    
}

export function deletePfile(pfileId){
    console.log(pfileId);
    return axios.delete(`${BASE_URL}/pfile/${pfileId}`);
}

export function getAttachment(pdirId){
    console.log(pdirId);
    return axios.get(`${BASE_URL}/attachment/${pdirId}`);
}

