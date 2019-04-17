import axios from 'axios';

export function savePfile(pfile){
    console.log(pfile);
    return axios.post('http://localhost:8080/upmureport/pfile', pfile);    
}

export function getPfile(dirId){
    console.log(typeof dirId);
    return axios.get(`http://localhost:8080/upmureport/pfile/${dirId}`);    
}

export function updatePfile(pfile){
    console.log(pfile);
    return axios.put('http://localhost:8080/upmureport/pfile', pfile);    
}

export function deletePfile(pfileId){
    console.log(pfileId);
    return axios.delete(`http://localhost:8080/upmureport/pfile/${pfileId}`);
}

export function getAttachment(pdirId){
    console.log(pdirId);
    return axios.get(`http://localhost:8080/upmureport/attachment/${pdirId}`);
}