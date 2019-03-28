import React from 'react';
import ProjAddModal from './ProjAddModal';
import ProjUpdateModal from './ProjUpdateModal';
import * as projectActions from '../../stores/modules/projectState';
import store from '../../stores';

class ProjTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = { 
        };
    }

    deleteItemClick(projId) {
        if (!window.confirm('정말로 삭제 할까요?')) return;

        const URL = projectActions.BASE_URL + projectActions.END_POINT.PROJ_DISABLE;
        const { userState } = store.getState();
        const data = {
            userId: userState.userInfo.userId,
            projId,
        }
        fetch(URL, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'accept': 'application/json'
            }
        });
        window.location.href="/";
    }
    
    editItemClick(proj) {
        this.setState({ selectedProj: proj });
    }

    render() {
        console.log("Rendering: ProjTable");
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
                                    <span className="action-btn trash" onClick={(e) => this.deleteItemClick(proj.projId)}><i className="fas fa-trash-alt"></i></span>
                                    <span className="action-btn edit" data-toggle="modal" data-target="#ProjUpdateModal" onClick={(e) => this.editItemClick(proj)}><i className="fas fa-edit"></i></span>
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
                <div className="row" style={{ margin: '5px', cursor: 'pointer' }}>
                    <div className="btn-cirecle btn-sm bg-darkblue text-white" data-toggle="modal" data-target="#projAddModal"><i className="fas fa-plus"></i> 프로젝트 추가하기</div>
                </div>
                <ProjAddModal/>
                <ProjUpdateModal project={Object.assign({}, this.state.selectedProj)}/>
            </div>
            
        )
    }
}

export default ProjTable;