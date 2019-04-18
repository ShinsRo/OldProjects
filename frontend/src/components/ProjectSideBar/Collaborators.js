import React, { Component } from 'react';

class Collaborators extends Component {
    constructor(props) {
        super(props);
        this.state = {
            collaborators: [{name: '임시 멤버', mid: '11111'}],
            proles: ['책임자'],
            PROLES: ['관리자', '책임자', '구성원', '게스트'],
        };

        this.list = this.list.bind(this);
    }

    list() {
        const { collaborators, proles } = this.state;

        return collaborators.map((collab, idx) => {
            return (
                <span className="member mr-2">
                    <span className="dropdown">
                    {collab.name} <span data-toggle="dropdown" className="badge badge-primary dropdown-toggle">관리자</span>
                    <div class="dropdown-menu">
                        {this.state.PROLES.map(prole => {
                            return (<span className="badge badge-primary">{prole}</span>);
                        })}
                    </div>
                </span>
                    <input name="collaborators" type="hidden" value={collab.mid}/>
                    <input name="proles" type="hidden" value={proles[idx]}/>
                </span>
            );
        });
    }

    render() {
        const { userInfo } = this.props;

        return (<>
            <span className="member mr-2">
                <span className="dropdown">
                    {userInfo.name} <span data-toggle="dropdown" className="badge badge-primary dropdown-toggle">관리자</span>
                    <div class="dropdown-menu">
                        {this.state.PROLES.map(prole => {
                            return (<span className="badge badge-primary">{prole}</span>);
                        })}
                    </div>
                </span>
                <input name="collaborators" type="hidden" value={userInfo.mid}/>
                <input name="proles" type="hidden" value="관리자"/>
            </span>
            {this.list()}
            <span class="dropdown">
                <span data-toggle="dropdown" className="fas fa-plus-circle"></span>
                <div class="dropdown-menu">
                        <div><input type="text" placeholder="멤버 검색..."/></div>
                        <div class="dropdown-divider"></div>
                        <div className="">모든 멤버 포함</div>
                </div>
            </span>
        </>);
    }
}

export default Collaborators;