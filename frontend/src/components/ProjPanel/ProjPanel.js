import React from 'react';
import ProjTable from './ProjTable';


const VIEW_LEVEL = {
    NONE: 0,
    PROJECTS: 1,
    PROJ_DETAIL: 2
};

class ProjPanel extends React.Component {
    static defaultProps = {
        targetUser: { userName: "이름없음" },
    }
    constructor(props) {
        super(props);
        this.state = {
            breadcrumb: [props.targetUser.userName, "Projects"],
            projects: [],
            viewLevel: VIEW_LEVEL.PROJECTS,
        };
        this.onBreadcrumbClick = this.onBreadcrumbClick.bind(this);
        this.onProjClick = this.onProjClick.bind(this);
    }

    onBreadcrumbClick(e, el, idx) {
        e.preventDefault(); // react 상 onclick="return false"와 같음.

        const prevBreadcrumb = this.state.breadcrumb;
        this.setState({ breadcrumb: prevBreadcrumb.slice(0, idx + 1), viewLevel: VIEW_LEVEL.PROJECTS });
        return;
    }

    onProjClick(e, proj) {
        e.preventDefault();
        
        const prevBreadcrumb = this.state.breadcrumb;
        this.setState( { breadcrumb: Array.prototype.concat(prevBreadcrumb, [proj.projName]), viewLevel: VIEW_LEVEL.PROJ_DETAIL } )
    }

    render() {
        const breadcrumb = this.state.breadcrumb;
        const projects = this.props.projects;
        let panelBody = {};
            switch (this.state.viewLevel) {
                case VIEW_LEVEL.PROJECTS:
                    panelBody = (<ProjTable projects={projects} onProjClick={this.onProjClick}/>);
                    break;
                case VIEW_LEVEL.PROJ_DETAIL:
                    panelBody = (<div>프로젝트 디테일</div>);
                    break;
                default:
                    panelBody = (<div>알 수 없는 뷰 레벨입니다.</div>);
            }
        

        return (
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <nav aria-label="breadcrumb">
                    </nav>
                    <div className="m-0 font-weight-bold text-darkblue">
                        <ol className="breadcrumb">
                        {breadcrumb.map( (el, idx) => {
                            if (idx === 0) {
                                return (<li className="breadcrumb-item text-gray-700" key={idx}>{el}</li>);
                            } else if (idx < breadcrumb.length - 1) {
                                return (
                                    <li className="breadcrumb-item active" key={idx}>
                                        <a  href="/"
                                            className="text-blue-700" 
                                            onClick={(e) => this.onBreadcrumbClick(e, el, idx)}>{el}</a>
                                    </li>
                                );
                            } else { return (<li className="breadcrumb-item" key={idx}>{el}</li>); }
                        })}
                        </ol>
                    </div>
                </div>
                <div className="card-body">
                        {panelBody}
                </div>
            </div>
        );
    }
}

export default ProjPanel;