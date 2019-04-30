import axios from 'axios';
import { BASE_URL } from '../supports/API_CONSTANT';


export function saveAttachment(attachment, config){
    console.log(attachment);
    return axios.post(`${BASE_URL}/attachment`, attachment, config);
}

export function getAttachment(dirId){
    return axios.get(`${BASE_URL}/attachment/${dirId}`);
}

export function downAttachment(attachmentId){
    return axios.get(`${BASE_URL}/attachment/download/${attachmentId}`);
}

export function deleteAttachment(attachmentId){
    return axios.delete(`${BASE_URL}/attachment/${attachmentId}`);
}