import React, { Component } from 'react';
import stores from '../../../../stores';

class Collaborators extends Component {
    constructor(props) {
        super(props);

        let {collaborators, memberInfo} = this.props;
        collaborators = collaborators || [];
        const collaboratorsMap = {};
        let adminCnt = 0;

        collaborators.forEach((collab, idx) => {
            collaboratorsMap[collab.mid] = collab;
            collab.prole === '관리자' && ++adminCnt;
            if (`${collab.mid}` === `${memberInfo.mid}`) {
                collaborators[idx] = [collaborators[0], collaborators[0] = collaborators[idx]][0];
            }
        });

        this.state = {
            collaborators: collaborators || [],
            adminCnt,
            PROLES_DEF: ['관리자', '책임자', '구성원', '게스트'],
            PRIVILEGES_DEF: ['읽기', '쓰기', '팀원추가'],
            privileges: {
                게스트: ['읽기'],
                구성원: ['읽기', '쓰기'],
                책임자: ['읽기', '쓰기', '팀원추가'],
                관리자: ['읽기', '쓰기', '팀원추가'],
            },
            type: this.props.type,
            nowPid: this.props.type === 'CURRECT_PROJECT' && this.props.project.pid,
            collaboratorsMap,
            deletedCollaborators: [],
            memberQuery: '',
            suggests: [],
            isCorrected: false,
        };

        this.list = this.list.bind(this);
        this.onMemberSearch = this.onMemberSearch.bind(this);
        this.onAutocompleteClick = this.onAutocompleteClick.bind(this);
    }

    componentDidUpdate(prevProps, prevState) {
        const prevCollabs = prevProps.collaborators;
        const thisCollabs = this.props.collaborators;
        
        let isSame = false;
        if (prevCollabs === null || thisCollabs === null) {
            console.warn('Collaborators의 props, collabroators 는 필수 인자입니다.');
        } else {
            isSame = prevCollabs.length === thisCollabs.length;
            
            for (let idx = 0; idx < prevCollabs.length; idx++) {
                if (!isSame) { break; } 
                else {
                    isSame =    `${thisCollabs[idx].mid}` === `${prevCollabs[idx].mid}`
                                && `${thisCollabs[idx].prole}` === `${prevCollabs[idx].prole}`;
                }
            }
        }
        
        if (!isSame) {
            const collaborators = [];
            const memberInfo = this.props.memberInfo;
            const deletedCollaborators = [];
            const collaboratorsMap = {};
            let adminCnt = 0;

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

    setProjectNotOrigin() {
        const { projectState } = stores.getState();
        const dirContainer = projectState.get("dirContainer");
        const selectedDirId = projectState.get("selectedDirId");
        const selectedDir = dirContainer.treeMap[selectedDirId];
        const project = dirContainer.projectMap[selectedDir.pid];

        if (project.isOrigin) {
            dirContainer.tempProjectData = {...project};
            project.isOrigin = false;
        }

        this.setState({isCorrected: true});
    }

    onAutocompleteClick(e, mDto) {
        const { collaborators, collaboratorsMap, deletedCollaborators } = this.state;
        if (collaboratorsMap[mDto.mid]) {
            alert("이미 포함한 공동 작업자입니다.");
            return;
        }
        
        for (let idx = 0; idx < deletedCollaborators.length; idx++) {
            if (`${deletedCollaborators[idx].mid}` === `${mDto.mid}`) {
                
                deletedCollaborators.splice(idx, 1);
                this.setState({ deletedCollaborators, });
                break;
            }
        }
        if (this.props.type === 'NEW_PROJECT') this.setProjectNotOrigin();
        
        collaboratorsMap[mDto.mid] = { mid: mDto.mid, name: mDto.name, prole: '구성원' };
        collaborators.push(collaboratorsMap[mDto.mid]);
        this.setState({ collaborators });
    }

    changeProle(collab, prole) {
        if (collab.prole === this.state.PROLES_DEF[0] && this.state.adminCnt - 1 === 0)  {
            alert("관리자는 한 명 이상 존재해야합니다.");
            return;
        }
        if (prole === this.state.PROLES_DEF[0]) {
            this.setState( { adminCnt: this.state.adminCnt + 1 } );
        }
        collab.prole = prole;
        
        if (this.props.type === 'NEW_PROJECT') this.setProjectNotOrigin();
        this.props.reload();
    }

    deleteCollaborator(e, collab) {
        if (collab.prole === this.state.PROLES_DEF[0] && this.state.adminCnt - 1 === 0)  {
            alert("관리자는 한 명 이상 존재해야합니다.");
            return;
        }
        if (collab.length === 1) {
            alert("참여자는 한 명 이상 존재햐아합니다.");
            return;
        }
        if (collab.prole === this.state.PROLES_DEF[0]) {
            this.setState( { adminCnt: this.state.adminCnt - 1 } );
        }

        const { collaborators, collaboratorsMap, deletedCollaborators } = this.state;
        
        deletedCollaborators.push({...collaboratorsMap[collab.mid]});
        delete collaboratorsMap[collab.mid];
        let idx = 0;
        for(; idx < collaborators.length; idx++) { 
            if (`${collaborators[idx].mid}` === `${collab.mid}`) { 
                collaborators.splice(idx, 1);
                break; 
            }
        }
        if (this.props.type === 'NEW_PROJECT') this.setProjectNotOrigin();

        this.setState({ deletedCollaborators });
        this.props.reload();
    }

    privilegeDropdownRenderer(collab) {
        const { project } = this.props
        const privileges = this.state.privileges;

        if (project && !privileges[project.prole].includes('팀원추가')) {
            return (
                <span className="">
                    <span className="text-black">{collab.name}</span>
                    <span className="badge btn-dark-1 bg-darkblue ml-1">{collab.prole}</span>
                </span>
            
            );
        }
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
    }

    list(collaborators) {
        const { memberInfo } = this.props;

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
    }

    render() {
        const { collaborators, deletedCollaborators } = this.state;
        let autocompletedDropDown;

        if (! this.state.memberQuery) {
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