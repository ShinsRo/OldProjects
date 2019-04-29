import axios from 'axios';

export function saveAttachment(attachment, config){
    console.log(attachment);
    return axios.post('http://localhost:8080/attachment', attachment, config);
}

export function getAttachment(dirId){
    return axios.get(`http://localhost:8080/attachment/${dirId}`);
}

export function downAttachment(attachmentId){
    return axios.get(`http://localhost:8080/attachment/download/${attachmentId}`);
}

export function deleteAttachment(attachmentId){
    return axios.delete(`http://localhost:8080/attachment/${attachmentId}`);
}