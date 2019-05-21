import React from 'react';

class DetailView extends React.Component {

    render() {
        const { memberInfo, lastCareer, closeModal, changeViewLevel } = this.props;
        return (
            <div className="card">
                <div className="card-header">
                <h4 className="text-dark-1 pt-1">개인정보</h4>
                </div>
                <div className="card-body text-dark-1">
                    <h5 className="text-dark-1">프로필</h5>
                    <hr/>
                    <div className="row">
                        <div className="col-4"><small>이름</small></div>
                        <div className="col-8 text-right">{memberInfo.name}</div>
                    </div>
                    <div className="row">
                        <div className="col-4"><small>생년월일</small></div>
                        <div className="col-8 text-right">{memberInfo.birth}</div>
                    </div>
        
                    <h5 className="text-dark-1 pt-4">회사 내 정보</h5>
                    <hr/>
                    <div className="row">
                        <div className="col-4"><small>부서</small></div>
                        <div className="col-8 text-right">{lastCareer.dept}</div>
                    </div>
                    <div className="row">
                        <div className="col-4"><small>직책</small></div>
                        <div className="col-8 text-right">{lastCareer.posi}</div>
                    </div>
                    <div className="row">
                        <div className="col-4"><small>사원번호</small></div>
                        <div className="col-8 text-right">{memberInfo.eid}</div>
                    </div>
                    <div className="row">
                        <div className="col-4"><small>입사일</small></div>
                        <div className="col-8 text-right">{memberInfo.joinDate}</div>
                    </div>
        
                    <h5 className="text-dark-1 pt-4">연락처 정보</h5>
                    <hr/>
                    <div className="row">
                        <div className="col-4"><small>이동전화번호</small></div>
                        <div className="col-8 text-right">{memberInfo.phoneNum}</div>
                    </div>
                    <hr/>
                    <div className="row">
                        <div className="col-12 text-right">
                        <button className="btn btn-secondary mr-2" onClick={() => closeModal('visible')}>닫기</button>      
                        <button className="btn btn-dark-2" onClick={changeViewLevel}>비밀번호 변경하기</button>      
                        </div>          
                    </div>
                </div>
            </div>
        );
    }
}
export default DetailView;