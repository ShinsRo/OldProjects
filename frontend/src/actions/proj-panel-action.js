import * as projectApi from '../api/project-api';

export const REQUEST_PROJECTS = 'REQUEST_PROJECTS';

export function requestProjects() {
    return {
        type: REQUEST_PROJECTS
    };
}

export const RECEIVE_PROJECTS = 'RECEIVE_PROJECTS';

export function receiveProjects(projs) {
  
    return {
        type: RECEIVE_PROJECTS,
        projects: projs,
        receivedAt: Date.now()
    };
}

// 썽크 미들웨어
export function fetchProjects(reqUrl) {
  
    return function (dispatch) {
      
      dispatch(requestProjects());
  
      return projectApi.getAll()
        .then(response => {
          const projs = response.data;
          dispatch(receiveProjects(projs))
        }, reason => {
          console.info(`프로젝트 Fetch axios 통신 중 에러, 사유 >> ${reason}`);
          dispatch(receiveProjects({ data: [{projName:'통신에 장애가 있습니다. 프로젝트 목록을 불러오지 못했습니다.'}] }))
        });
    };
  }