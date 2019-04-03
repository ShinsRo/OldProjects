import React, { Component } from 'react';
import { Map, List } from 'immutable';

class ContentTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = Map({
            dirs: List(),
        });
    }

    render() {

        const {upmus, dirs} = this.props;
        const { onClickDir } = this.props;
        //const {selectedProject} = projectState.get('selectedProject') && projectState.get('selectedProject');
        const {selectedProject} = this.props;
        console.log(selectedProject);
        //this.state.set('dirs', projectState.get('dirs').get('selectedProject'));
        //const {dirs} = projectState.get('dirs').get('selectedProject');
        console.log(dirs);
        //console.log('upmus---', upmus);
        //console.log('dirs---', dirs);
 //       console.log('dirs---', projectState.get('dirs').dirs.get(`${projectState.project.projId}`));
        return (
            <div>
                <table className="table" id="upmuTable" cellSpacing="0">
                    <thead>
                        <tr>
                            <th>제목</th>
                            <th>내용</th>
                            <th>경로</th>
                            <th>작성일자</th>
                            <th>수정일자</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dirs && dirs.get(`${selectedProject}`).map((dir, idx) => {
                            return (
                                <tr value={1002} key={idx} onClick={onClickDir}>
                                    <td>{dir.dirName}</td>
                                    <td>d</td>
                                    <td>d</td>
                                    <td>f</td>
                                    <td>s</td>
                                </tr>
                            );
                        })}
                        </tbody>
                        <tbody>
                        {upmus && upmus.map((upmu, idx) => {
                            return (
                                <tr value={1002} key={idx} onClick={onClickDir}>
                                    <td>{upmu.name}</td>
                                    <td>{upmu.contents}</td>
                                    <td>{upmu.localPath}</td>
                                    <td>{upmu.newDate}</td>
                                    <td>{upmu.updateDate}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ContentTable;