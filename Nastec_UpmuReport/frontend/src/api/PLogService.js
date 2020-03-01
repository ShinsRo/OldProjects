import axios from 'axios';
import { BASE_URL } from '../supports/API_CONSTANT';

export function getPLog(pid){
    return axios.get(`${BASE_URL}/plog/${pid}`);  
}