import axios from 'axios';

export function saveAttachment(attachment){
    console.log(attachment);
    return axios.post('http://localhost:8080/upmureport/attachment', attachment);
}

export function getAttachment(dirId){
    return axios.get(`http://localhost:8080/upmureport/attachment/${dirId}`);
}