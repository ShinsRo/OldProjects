import {
    REQUEST_PROJECTS, RECEIVE_PROJECTS
} from '../actions/proj-panel-action';

export default function getAll(state = {
    isFetching: false,
    projects: []
}, action) {
    switch (action.type) {
        case REQUEST_PROJECTS:
            return { ...state, isFetching: true, projects: [] };
        case RECEIVE_PROJECTS:
            return { ...state, isFetching: false, projects: action.projects, lastUpdated: action.receivedAt };
        default:
            return state;
    }
}