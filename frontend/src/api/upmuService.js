import axios from 'axios';

export function saveUpmuAPI(upmu){
    console.log('-----------');
    console.log(upmu);
    return axios.post('http://localhost:8080/upmureport/upmu', upmu);    
}

export function getUpmu(dirId){
    //console.log('-----------');
    console.log(typeof dirId);
    return axios.get(`http://localhost:8080/upmureport/upmu/${dirId}`);    
}
