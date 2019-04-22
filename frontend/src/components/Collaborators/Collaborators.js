import React, { Component } from 'react';
import stores from '../../stores';

class Collaborators extends Component {
    constructor(props) {
        super(props);
        this.state = {
            collaborators: [],
            PROLES_DEF: ['관리자', '책임자', '구성원', '게스트'],
            privileges: {
                관리자: '읽기/쓰기 권한',
                책임자: '읽기/쓰기 권한',
                구성원: '읽기 권한',
                게스트: '읽기 권한',
            },

            userInfo: '',
            collaboratorsMap: {},
            memberQuery: '',
            suggests: [],
        };

        this.list = this.list.bind(this);
        this.onMemberSearch = this.onMemberSearch.bind(this);
        this.onAutocompleteClick = this.onAutocompleteClick.bind(this);
    }

    onMemberSearch(e) {
        const { projectState } = stores.getState();
        const memberAutocompletor = projectState.get('memberAutocompletor');
        const keyword = e.target.value;
        this.setState( { 
            suggests: memberAutocompletor.autocompleted(keyword, 5),
            memberQuery: keyword,
        } );
        
    }

    onAutocompleteClick(e, mDto) {
        const { collaborators, collaboratorsMap } = this.state;
        if (collaboratorsMap[mDto.mid]) {
            alert("이미 포함한 공동 작업자입니다.");
            return;
        }
        collaboratorsMap[mDto.mid] = { mid: mDto.mid, name: mDto.name, prole: '구성원' };
        collaborators.push(collaboratorsMap[mDto.mid]);
        this.setState({ collaborators });
    }

    deleteCollaborator(e, collab) {
        const { collaborators, collaboratorsMap } = this.state;
        
    }

    list(collaborators) {
        return collaborators.map((collab, idx) => {
            return (
                <span key={idx} className="member mr-2 align-center">
                    <span className="dropdown">
                    {collab.name} <span data-toggle="dropdown" className="badge badge-primary bg-darkblue dropdown-toggle">{collab.prole}</span>
                    <div className="dropdown-menu">
                        {this.state.PROLES_DEF.map(prole => {
                            return (
                            <div className="dropdown-item" key={prole} >
                                <span className="badge badge-primary">{prole}</span> {this.state.privileges[prole]}
                            </div>    
                            );
                        })}
                        <div className="dropdown-item" onClick={(e) => this.deleteCollaborator(e, collab)}>
                            삭제
                        </div>
                    </div>
                </span>
                    <input name="mids" type="hidden" value={collab.mid}/>
                    <input name="proles" type="hidden" value={collab.prole}/>
                </span>
            );
        });
    }

    render() {
        const { collaborators } = this.state;
        let autocompletedDropDown;

        if (! this.state.memberQuery) {
            autocompletedDropDown = (
                <div className="col-12">
                    <div className="dropdown-item sm-text">공공 프로젝트로 등록</div>
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
                            >{data.msg}</div>
                        </div>
                    ); 
                });
        }

        if (!collaborators) return <></>;
        return (<>
            {this.list(collaborators)}
            <span className="dropdown">
                <span data-toggle="dropdown" className="fas fa-plus-circle"></span>
                <div className="dropdown-menu">
                    <div className="row mb-1">
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
        </>);
    }
}

export default Collaborators;