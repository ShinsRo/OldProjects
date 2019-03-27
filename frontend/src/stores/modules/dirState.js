import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';

const initialState = Map({
    dir: 1001,
})

const CHANGE_SELECTED_DIR = 'CHANGE_SELECTED_DIR';

export const changeSelectedDir = createAction(CHANGE_SELECTED_DIR);

export default handleActions({
    // 타이틀 입력
    [CHANGE_SELECTED_DIR]: (state, action) => {
        return state.set('dir', action.payload)
    },
}, initialState);