import React, { Component } from 'react';


class App extends Component {

  render() {
    return (
      <div>
        앱 메인 수정 필수<br/>
        **배포 시 이하 내용 삭제 필수** <br/>
        localhost:3000/login -> 로그인 페이지 <br/>
        localhost:3000/dashboard -> 대쉬 보드 페이지 <br/>
        localhost:3000/devtest -> 컴포넌트 테스트 페이지 (.gitignore 에 등록했으므로 푸쉬해도 저장 안되니 컴포넌트 테스트 용으로만 사용할 것.) <br/>
        <a href="http://133.9.100.223/internship/upmureport/tree/jsp_to_react/frontend">이 내용 필수 참조!!</a>
      </div>
    );
  }
}

export default App;
