import React, { Component } from 'react';

class Collaborators extends Component {
    constructor(props) {
        super(props);
        this.state = {
            collaborators: [],
            PROLES_DEF: ['관리자', '책임자', '구성원', '게스트'],
            userInfo: '',
        };

        this.list = this.list.bind(this);
    }

    list(collaborators) {

        return collaborators.map((collab, idx) => {
            return (
                <span key={idx} className="member mr-2 align-center">
                    <span className="dropdown">
                    {collab.name} <span data-toggle="dropdown" className="badge badge-primary bg-darkblue dropdown-toggle">{collab.prole}</span>
                    <div className="dropdown-menu">
                    <div className="dropdown-item">
                            {this.state.PROLES_DEF.map(prole => {
                                return (<span key={prole} className="badge badge-primary">{prole}</span>);
                            })}
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
        const { collaborators } = this.props;
        
        if (!collaborators) return <></>;
        return (<>
            {this.list(collaborators)}
            <span className="dropdown">
                <span data-toggle="dropdown" className="fas fa-plus-circle"></span>
                <div className="dropdown-menu">
                        <div><input type="text" placeholder="멤버 검색..."/></div>
                        <div className="dropdown-divider"></div>
                        <div className="">모든 멤버 포함</div>
                </div>
            </span>
        </>);
    }
}

export default Collaborators;