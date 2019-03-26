import React from 'react';


class ProjTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const projects = this.props.projects;
        const onProjClick = this.props.onProjClick;
        const progressColor = (projProgress) => {
            const toInt = Number(projProgress);

            if (toInt === 100) {
                return 'bg-darkblue';
            } else if (toInt >= 70){
                return 'bg-success';
            } else if (toInt >= 40){
                return 'bg-info';
            } else if (toInt >= 20){
                return 'bg-warning';
            } else {
                return 'bg-danger';
            }
        }

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
                        {projects && projects.map( (proj, idx) => {
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
                                            <div className={`${progressColor(proj.projProgress)}`} role="progressbar" style={{width: `${proj.projProgress}%`}} aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <div className="col-auto">
                                            <div className="ml-1">{proj.projProgress}%</div>
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