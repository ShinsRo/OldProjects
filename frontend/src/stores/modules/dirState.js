import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';

const initialState = Map({
    dir: 1001,
    dirTree: [],
})

const CHANGE_SELECTED_DIR = 'CHANGE_SELECTED_DIR';
const SET_DIR_TREE = 'SET_DIR_TREE';

export const changeSelectedDir = createAction(CHANGE_SELECTED_DIR);
export const setDirTree = createAction(SET_DIR_TREE);

export default handleActions({
    // 타이틀 입력
    [CHANGE_SELECTED_DIR]: (state, action) => {
        return state.set('dir', action.payload)
    },
    [SET_DIR_TREE]: (state, action) => {
        // const tempMap = {};
        // const dirTree = [];
        // console.log(">>>>", action.payload);


        // action.payload.forEach((dir, idx) => {
        //     tempMap[dir.dirId] = {
        //         id: dir.dirId,
        //         text: dir.dirName,
        //         children: [],
        //         parent: dir.parentDirId,
        //         isLeaf: false
        //     }
        // });

        // console.log(tempMap);

        // Object.keys(tempMap).forEach(key => {
        //     const parent = tempMap[key].parent;
        //     // tempMap[key].isLeaf = tempMap[key].children.length === 0;

        //     if (parent !== 'root') {
        //         tempMap[parent].children.push(tempMap[key]);
        //     } else {
        //         dirTree.push(tempMap[key]);
        //     }
        // });
        return state.set('dirTree', action.payload);
    },
}, initialState);