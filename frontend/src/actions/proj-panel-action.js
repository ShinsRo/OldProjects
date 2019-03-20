import * as projectApi from '../api/project-api';

export const REQUEST_PROJECTS = 'REQUEST_PROJECTS';

export function requestProjects(reqUrl) {
    return {
        type: REQUEST_PROJECTS,
        reqUrl
    };
}

export const RECEIVE_PROJECTS = 'REQUEST_PROJECTS';

export function receiveProjects(reqUrl, json) {
    return {
        type: RECEIVE_PROJECTS,
        projects: json.data,
        receivedAt: Date.now()
    };
}

// 썽크 미들웨어
export function fetchProjects(reqUrl) {
  
    return function (dispatch) {
  
      dispatch(requestProjects(reqUrl));
  
      return projectApi.getAll()
        .then(response => response.json())
        .then(json =>
  
          dispatch(requestProjects(reqUrl, json))
        );
    };
  }