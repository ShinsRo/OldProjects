import axios from 'axios';

export function saveUpmu(upmu){
    console.log('-----------saveUpmu');
    console.log(upmu);
    return axios.post('http://localhost:8080/upmureport/upmu', upmu);    
}

export function getUpmu(dirId){
    console.log('-----------getUpmu');
    console.log(typeof dirId);
    return axios.get(`http://localhost:8080/upmureport/upmu/${dirId}`);    
}

export function updateUpmu(upmu){
    console.log('-----------updateUpmu');
    console.log(upmu);
    return axios.put('http://localhost:8080/upmureport/upmu', upmu);    
}

export function deleteUpmu(upmuId){
    console.log('-----------deleteUpmu');
    console.log(upmuId);
    return axios.delete(`http://localhost:8080/upmureport/upmu/${upmuId}`);
}