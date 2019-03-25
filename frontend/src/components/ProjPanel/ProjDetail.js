import React from 'react';


class ProjTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const projects = this.props.projects;
        const onProjClick = this.props.onProjClick;

        return (
            <div className="table-responsive">
                <table className="table" id="dataTable" cellSpacing="0">
                    <thead>
                        <tr>
                        <th>프로젝트 이름</th>
                        <th>담당자</th>
                        <th>상태</th>
                        <th>진행률</th>
                        </tr>
                    </thead>
                    <tbody>
                        {projects.map( (proj, idx) => {
                            return (
                                <tr key={idx}>
                                    <td>
                                        <a  href="/"
                                            className="text-gray-800" 
                                            onClick={(e) => onProjClick(e, proj, idx)}>{proj.projName}</a>
                                    </td>
                                    <td>{proj.userName}</td>
                                    <td>{proj.projStat}</td>
                                    <td>
                                    <div className="row no-gutters align-items-center">
                                        <div className="col progress progress-sm mr-1">
                                            <div className={`bg-darkblue`} role="progressbar" style={{width: `${proj.progress}%`}} aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <div className="col-auto">
                                            <div className="ml-1">{proj.progress}%</div>
                                        </div>
                                    </div>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            
        )
    }
}

export default ProjTable;