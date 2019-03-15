import axios from 'axios';

export function getAll() {
    return axios.get('http://localhost:8080/upmureport/api/projects/getAll');
}