class MemberApi {
    constructor(SERVER_URL) {
        this.SERVER_URL = SERVER_URL;
    }

    // 중복확인
    availableCheck(username) {
        const data = { 'username': username };
        return axios.post(`${this.SERVER_URL}/auth/availableCheck`, data).then(response => {
            return !!response.data;
        });
    }

    // 회원가입
    register(username, password, name, authorNameList, organizationList) {
        event.preventDefault();
        
        const data = {
            'username': username,
            'password': password,
            'memberInfoDto': {
                'name': name,
                'authorNameList': authorNameList,
                'organizationList': organizationList, // 필드가 하나일 경우 배열로 만들어서 전달해야함
            }
        }

        return axios.post(`${this.SERVER_URL}/auth/register`, data).then(response => {
            return response.data;
        });
    }

    // 로그인
    memberLogin(username, password) {
        const data = {
            'username': username,
            'password': password
        }
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${btoa(`${username}:${password}`)}`
        }
        
        return axios.post(`${this.SERVER_URL}/auth/login`, data, { headers: headers }).then(response => {
            return response.data;
        });
    }
}