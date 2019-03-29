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
        const upmus = this.props.upmus;
        const {onClickDir, projectState, dirs, selectedProject, selectedDirId, onClickUpmu} = this.props;       
        // if (currentDir === ''){
        //     currentDir = "root" ;
        // }

        console.log('selectedDirId---', selectedDirId);
        return (
            <div>
                <h2>{selectedDirId}</h2>
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
                            console.log('--------', dir);
                            if (dir.parentDirId === selectedDirId){
                            return (
                                <tr value={dir.dirId} key={idx} onClick={() => onClickDir(`${dir.dirId}`)}>
                                    <td>{dir.dirName}</td>
                                    <td>{dir.dirId}</td>
                                    <td>d</td>
                                    <td>f</td>
                                    <td>s</td>
                                </tr>
                            );
                            }
                        })}
                        </tbody>

                    <tbody>
                        {upmus.map((upmu, idx) => {
                            console.log('--------', upmu);
                            //if(upmu.dirId === selectedDirId){
                            return (
                                <tr value={selectedDirId} key={idx} onClick={() => onClickUpmu(upmu)}>
                                    <td>{upmu.name}</td>
                                    <td>{upmu.contents}</td>
                                    <td>{upmu.localPath}</td>
                                    <td>{upmu.newDate}</td>
                                    <td>{upmu.updateDate}</td>
                                </tr>
                            );
                           // }
                        })}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ContentTable;