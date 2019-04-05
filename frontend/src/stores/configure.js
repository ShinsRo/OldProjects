import { createStore ,applyMiddleware, compose} from 'redux';
import modules from './modules';

import { createLogger } from 'redux-logger';
import ReduxThunk from 'redux-thunk';

/* 로그 미들웨어를 생성 할 때 설정을 커스터마이징 할 수 있습니다.
   https://github.com/evgenyrodionov/redux-logger#options
*/
const configure = () => {
    const logger = createLogger(); 
    const store = createStore(modules, applyMiddleware(logger,ReduxThunk));
    return store;
}
export default configure;