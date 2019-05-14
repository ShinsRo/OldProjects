/** 
 * 팀원 컴포넌트 정의
 * 
 * 팀원(Collaborators) 컴포넌트는 해당 프로젝트가 어떠한 멤버로 구성되었는가를 보여주는 컴포넌트이다.
 * 해당 컴포넌트는 각 멤버의 권한을 이해할 수 있고, 컴포넌트 상에서 수정하거나 추가/삭제할 수 있다.
 * 이 컴포넌트는 프로젝트 추가/수정 컴포넌트 등에서 사용할 수 있다.
 * 
 * 2019.05.13
 * @file 이 파일은 Collaborators 컴포넌트를 정의한다.
 * @author 김승신
 */
import React, { Component } from 'react';
import stores from '../../../../stores';

class Collaborators extends Component {
    /**
     * props :
     *  - collaborators : 프로젝트가 지닌 원본 팀원 배열
     *  - memberInfo    : 로그인한 사원의 정보
     *  - type          : 해당 컴포넌트가 어떤 타입에서 사용되는 지 결정
     *      - NEW_PROJECT
     *      - CURRECT_PROJECT
     */
    constructor(props) {
        super(props);

        let {collaborators, memberInfo} = this.props;
        const collaboratorsMap = {};            //  빠른 팀원 정보 참조를 위한 맵, { [mid]: mDto, ... }
        
        collaborators = collaborators || [];    // collaborators가 Array임을 보장
        let adminCnt = 0;                       // 프로젝트 내 팀원 중 관리자가 한 명 이상임을 보장하기 위한 카운팅

        /* 팀원 맵 초기화 */
        collaborators.forEach((collab, idx) => {
            collaboratorsMap[collab.mid] = collab;
            collab.prole === '관리자' && ++adminCnt;

            // 로그인한 유저를 가장 첫번째로 보여주기 위한 Swap
            if (`${collab.mid}` === `${memberInfo.mid}`) {
                collaborators[idx] = [collaborators[0], collaborators[0] = collaborators[idx]][0];
            }
        });
        /* 팀원 맵 초기화 끝 */

        /* Default State 초기화 */
        this.state = {
            collaborators: collaborators,                           // 프로젝트가 지닌 원본 팀원 배열
            adminCnt,                                               // 팀원 내 관리자 수
            PROLES_DEF: ['관리자', '책임자', '구성원', '게스트'],     // 권한 이름 정의   
            PRIVILEGES_DEF: ['읽기', '쓰기', '팀원추가'],            // 권한별 제한명 정의
            privileges: {                                           // 권한 이름에 따른 권한별 제한명 정의
                게스트: ['읽기'],
                구성원: ['읽기', '쓰기'],
                책임자: ['읽기', '쓰기', '팀원추가'],
                관리자: ['읽기', '쓰기', '팀원추가'],
            },
            type: this.props.type,                                  // 컴포넌트 사용 타입
            nowPid: this.props.type === 'CURRECT_PROJECT' && this.props.project.pid,    // 현재 프로젝트 아이디
            collaboratorsMap,                                       // 팀원 정보 맵
            deletedCollaborators: [],                               // 삭제할 팀원정보 배열 버퍼
            memberQuery: '',                                        // 자동완성 검색 키워드
            suggests: [],                                           // 자동완성 추천 목록
            isCorrected: false,                                     // 이전 정보 대비 변동 사항이 있는가에 대한 플래그
        };

        this.list = this.list.bind(this);
        this.onMemberSearch = this.onMemberSearch.bind(this);
        this.onAutocompleteClick = this.onAutocompleteClick.bind(this);
    }

    /**
     * Collbaorators 컴포넌트 DidUpdate 라이프 사이클
     * 
     * 다른 collaborators로 props를 넘겨받았을 때 해당 컴포넌트를 다시 초기화한다.
     * 
     * @param {*} prevProps 
     * @param {*} prevState 
     */
    componentDidUpdate(prevProps, prevState) {
        const prevCollabs = prevProps.collaborators;    // Update 전 this.collaborators
        const thisCollabs = this.props.collaborators;   // Update 후 this.collaborators
        
        let isSame = false;                             // 전후 collaborators가 같은 지 판단하는 플래그
        
        /* 전후 Collaborators 비교 */
        if (prevCollabs === null || thisCollabs === null) {
            console.warn('Collaborators의 props, collabroators 는 필수 인자입니다.');
        } else {
            // 직접 비교하여 collaborators가 같은 지 판단한다.
            isSame = prevCollabs.length === thisCollabs.length;
            
            for (let idx = 0; idx < prevCollabs.length; idx++) {
                if (!isSame) { break; } 
                else {
                    isSame =    `${thisCollabs[idx].mid}` === `${prevCollabs[idx].mid}`
                                && `${thisCollabs[idx].prole}` === `${prevCollabs[idx].prole}`;
                }
            }
        }
        /* 전후 Collaborators 비교 끝 */
        
        /* 전후 Collaborators가 다를 때 state 초기화 */
        if (!isSame) {
            const collaborators = [];
            const memberInfo = this.props.memberInfo;
            const deletedCollaborators = [];
            const collaboratorsMap = {};
            let adminCnt = 0;

            // constructor의 작업 내용과 동일
            this.props.collaborators.forEach((collab, idx) => {
                collaboratorsMap[collab.mid] = collab;
                collab.prole === '관리자' && ++adminCnt;
                collaborators.push({...collab});
                if (`${collab.mid}` === `${memberInfo.mid}`) {
                    collaborators[idx] = [collaborators[0], collaborators[0] = collaborators[idx]][0];
                }
            });
            
            this.setState({collaborators, collaboratorsMap, deletedCollaborators, adminCnt});
        }
        /* 전후 Collaborators가 다를 때 state 초기화 끝 */
    }

    /**
     * 검색 키워드에 따라 자동완성 결과를 State에 저장한다.
     * 
     * @param {Event} e 이벤트 객체
     */
    onMemberSearch(e) {
        const { projectState } = stores.getState();                             // 프로젝트 Store state
        const memberAutocompletor = projectState.get('memberAutocompletor');    // 자동완성 객체
        const keyword = e.target.value;                                         // 검색 키워드

        this.setState( { 
            suggests: memberAutocompletor.autocompleted(keyword, 5),            // 검색 결과
            memberQuery: keyword,                                               // 검색에 사용한 키워드
        } );
    }

    /**
     * 트랜젝션을 위해 Collaborators가 변경되면, 
     * 프로젝트의 isOrigin 값을 false로 변경하여 서버와 동기화되지 않았음을 표현한다.
     * 
     */
    setProjectNotOrigin() {
        const { projectState } = stores.getState();                 // 프로젝트 Store state
        const dirContainer = projectState.get("dirContainer");      // KssTree 객체 (디렉토리 트리 제어 객체)
        const selectedDirId = projectState.get("selectedDirId");    // 현재 선택된 디렉토리 아이디
        const selectedDir = dirContainer.treeMap[selectedDirId];    // 현재 선택된 디렉토리 객체
        const project = dirContainer.projectMap[selectedDir.pid];   // 현재 선택된 프로젝트 객체

        /* 현재 프로젝트가 원본일 때 원본을 백업 */
        if (project.isOrigin) {
            dirContainer.tempProjectData = {...project};
            dirContainer.tempProjectData.collaborators = [...project.collaborators];
            dirContainer.tempProjectData.deletedCollaborators = [];
            project.isOrigin = false;
        }
        /* 현재 프로젝트가 원본일 때 원본을 백업 끝 */

        // 상위에서 리로딩(Triggering this compoenent's update cycle.)
        this.props.reload();
    }

    /**
     * 자동완성한 결과를 클릭했을 때 발생하는 이벤트를 핸들링한다.
     * 
     * 클라이언트 상 프로젝트 데이터를 변경하고, 서버와 동기화된 데이터가 아님을 표현한다.
     * 여기서 변경은 팀원이 추가되는 것을 의미한다.
     * 
     * @param {*} e 
     * @param {Object} mDto 클릭한 사원 데이터
     */
    onAutocompleteClick(e, mDto) {
        const { collaborators, collaboratorsMap, deletedCollaborators } = this.state;

        // mDto가 이미 팀원으로 포함한 사원일 경우
        if (collaboratorsMap.hasOwnProperty(`${mDto.mid}`)) {
            alert("이미 포함한 공동 작업자입니다.");
            return;
        }
        
        /* 포함할 mDto가 이전에 삭제한 이력이 있는 경우 deletedCollaborators에서 제외 */
        for (let idx = 0; idx < deletedCollaborators.length; idx++) {
            if (`${deletedCollaborators[idx].mid}` === `${mDto.mid}`) {
                
                deletedCollaborators.splice(idx, 1);
                this.setState({ deletedCollaborators, });
                break;
            }
        }
        /* 포함할 mDto가 이전에 삭제한 이력이 있는 경우 deletedCollaborators에서 제외 끝 */

        // 컴포넌트 타입이 CURRECT_PROJECT 일 때 해당 collaborators 데이터가 원본이 아님을 저장
        if (this.props.type === 'CURRECT_PROJECT') this.setProjectNotOrigin();
        
        /* State 업데이트 */
        collaboratorsMap[mDto.mid] = { mid: mDto.mid, name: mDto.name, prole: '구성원' };
        collaborators.push(collaboratorsMap[mDto.mid]);
        this.setState({ collaborators });
        /* State 업데이트 끝 */
    }

    /**
     * 한 공동 작업자에 대한 권한을 변경한다.
     * 
     * @param {*} collab Target 공동 작업자 사원 정보 객체
     * @param {*} prole 변경할 권한 이름
     */
    changeProle(collab, prole) {

        /* 프로젝트 내 관리자가 한 명 이상일 것을 보장하기 위한 작업 */
        if (collab.prole === this.state.PROLES_DEF[0] && this.state.adminCnt - 1 === 0)  {
            alert("관리자는 한 명 이상 존재해야합니다.");
            return;
        }
        if (prole === this.state.PROLES_DEF[0]) {
            this.setState( { adminCnt: this.state.adminCnt + 1 } );
        }
        collab.prole = prole;
        /* 프로젝트 내 관리자가 한 명 이상일 것을 보장하기 위한 작업 끝 */
        
        // 컴포넌트 타입이 CURRECT_PROJECT 일 때 해당 collaborators 데이터가 원본이 아님을 저장
        if (this.props.type === 'CURRECT_PROJECT') this.setProjectNotOrigin();

        // 상위에서 리로딩(Triggering this compoenent's update cycle.)
        this.props.reload();
    }

    /**
     * 공동 작업자(팀원) 삭제 이벤트를 핸들링한다.
     * 
     * 서버로 전달되기 전 삭제 대상을 deleteCollaborators state로 관리한다.
     * 따라서 삭제 이벤트를 핸들링할 때 이 state를 변경한다.
     * 
     * @param {Event} e 클릭 이벤트 객체
     * @param {*} collab 제거할 Target 공동작업자 정보 객체
     */
    deleteCollaborator(e, collab) {

        /* 프로젝트 내 관리자 한 명 이상일 것을 보장하기 위한 작업 */
        if (collab.prole === this.state.PROLES_DEF[0] && this.state.adminCnt - 1 === 0)  {
            alert("관리자는 한 명 이상 존재해야합니다.");
            return;
        }
        if (collab.length === 1) {
            alert("참여자는 한 명 이상 존재해아합니다.");
            return;
        }
        if (collab.prole === this.state.PROLES_DEF[0]) {
            this.setState( { adminCnt: this.state.adminCnt - 1 } );
        }
        /* 프로젝트 내 관리자가 한 명 이상일 것을 보장하기 위한 작업 끝 */

        const { collaborators, collaboratorsMap, deletedCollaborators } = this.state;
        
        /* 타깃 공동작업자를 deleteCollaborators state에 추가하는 작업 */
        deletedCollaborators.push({...collaboratorsMap[collab.mid]});
        delete collaboratorsMap[collab.mid];        // 팀원 맵에서 타깃을 제거
        let idx = 0;
        for(; idx < collaborators.length; idx++) {  // 팀원 배열에서 타깃을 제거
            if (`${collaborators[idx].mid}` === `${collab.mid}`) { 
                collaborators.splice(idx, 1);
                break; 
            }
        }
        if (this.props.type === 'CURRECT_PROJECT') this.setProjectNotOrigin();
        /* 타깃 공동작업자를 deleteCollaborators state에 추가하는 작업 끝 */

        this.setState({ deletedCollaborators });
        this.props.reload();
    }

    /**
     * 권한에 따라 권한 변경 드롭다운을 렌더링한다.
     * 
     * @param {Object} collab 타깃 공동작업자 
     */
    privilegeDropdownRenderer(collab) {
        const { project } = this.props
        const privileges = this.state.privileges;       // 권한별 제한명

        /* 드롭다운 렌더링 */
        // 팀원추가 권한이 없는 경우 작업자 이름과 권한명으로 렌더한다.
        if (project && !privileges[project.prole].includes('팀원추가')) {
            return (
                <span className="">
                    <span className="text-black">{collab.name}</span>
                    <span className="badge btn-dark-1 bg-darkblue ml-1">{collab.prole}</span>
                </span>
            
            );
        }
        // 팀원추가 권한이 있는 경우 권한을 변경할 수 있도록 드롭다운을 렌더한다.
        return (
            <span className="dropdown">
                    <span className="text-black">{collab.name}</span>
                    <span data-toggle="dropdown" className="badge btn-dark-1 bg-darkblue ml-1 dropdown-toggle">{collab.prole}</span>
                    <div className="dropdown-menu">
                        {this.state.PROLES_DEF.map(prole => {
                            return (
                                <div
                                    onClick={(e) => { this.changeProle(collab, prole) }}
                                    className="dropdown-item" key={prole} >
                                    <span className="badge btn-dark-2">{prole}</span> {this.state.privileges[prole].join('/')}
                                </div>    
                            );
                        })}
                <div className="dropdown-item" onClick={(e) => this.deleteCollaborator(e, collab)}>
                    삭제
                </div>
            </div>
                        
            </span>
        );
        /* 드롭다운 렌더링 끝 */
    }

    /**
     * 프로젝트가 포함한 공동작업자들을 렌더링한다.
     * 
     * 클라이언트에 접속한 유저의 권한은 공동 작업자 데이터와 별개로 폼 파라미터에 추가함을 유의한다.
     * 
     * @param {Array} collaborators 공동 작업자 배열
     */
    list(collaborators) {
        const { memberInfo } = this.props;

        /* 공동작업자 렌더링 */
        return collaborators.map((collab, idx) => {
            return (
                <span key={idx} className="member mr-2 align-center">
                    {this.privilegeDropdownRenderer(collab)}
                    
                    <input name="mids" type="hidden" value={collab.mid}/>
                    <input name="proles" type="hidden" value={collab.prole}/>

                    { `${collab.mid}` === `${memberInfo.mid}` && <input name="prole" type="hidden" value={collab.prole}/> }
                </span>
            );
        });
        /* 공동작업자 렌더링 끝 */
    }

    /* 렌더 함수 */
    render() {
        const { collaborators, deletedCollaborators } = this.state;
        let autocompletedDropDown;

        if (!this.state.memberQuery) {
            autocompletedDropDown = (
                <div className="col-12">
                    <div className="dropdown-item sm-text" onClick={(e) => { alert("구현 중"); }}>공공 프로젝트로 등록</div>
                </div>
            );
        } else {
            autocompletedDropDown
                = this.state.suggests.map((data, idx) => { 
                    return (
                        <div className="col-12" key={idx}>
                            <div 
                                className="dropdown-item sm-text"
                                onClick={(e) => {this.onAutocompleteClick(e, data.mDto)}}
                            >{data.tag}</div>
                        </div>
                    ); 
                });
        }

        if (!collaborators) return <></>;
        return (<>
            {this.list(collaborators)}
            <span className="dropdown">
                <span data-toggle="dropdown" className="fas fa-plus-circle text-dark-2" style={{cursor:'pointer'}}></span>
                <div className="dropdown-menu">
                    <div className="row mb-1 p-1">
                        <div className="col">
                            <input type="text" className="form-control sm-text" placeholder="멤버 검색..." onChange={(e) => {this.onMemberSearch(e)}}/>
                        </div>
                    </div>
                    <div className="dropdown-divider"></div>
                    <div className="row">
                        {autocompletedDropDown}
                    </div>
                </div>
            </span>
            <input type="hidden" name="deletedCollaborators" value={JSON.stringify(deletedCollaborators || [])} readOnly/>
            <input type="hidden" name="collaborators" value={JSON.stringify(collaborators || [])} readOnly/>
        </>);
    }
}

export default Collaborators;