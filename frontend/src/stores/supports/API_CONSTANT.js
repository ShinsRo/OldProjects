const BASE_URL = 'http://localhost:8080/upmureport';
const PROJECT_URL = `${BASE_URL}/api/project`;
const PDIR_URL = `${BASE_URL}/api/pdir`;
const MEMBER_URL = `${BASE_URL}/api/users`;

export const URL = {
    DEFAULT: '/',

    MEMBER: {
        LIST_ALL: `${MEMBER_URL}/listAll`,
    },

    PROJECT: {
        LIST: `${PROJECT_URL}/list`,
        REGISTER: `${PROJECT_URL}/register`,
        CORRECT: `${PROJECT_URL}/correct`,
        DISABLE: `${PROJECT_URL}/disable`,
    },

    PDIR: {
        LIST: `${PDIR_URL}/list`,
        REGISTER: `${PDIR_URL}/register`,
        CORRECT: `${PDIR_URL}/correct`,
        DISABLE: `${PDIR_URL}/disable`,
    }
};

export const HEADERS = {}