import React, { Component } from 'react';
import { Map, List } from 'immutable';
import UpmuContentModal from './UpdateContentModal';

class ContentTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            dirs: List(),
            updateModal: false,
            upmu: {},
        };
    }

    handleClickUpmu = (upmu) => {
        console.log(upmu);
        //this.props.handleClickUpmu()
        this.setState({updateModal: true})
        this.setState({ upmu }, () => {
          console.log(">>>>", this.state.upmu);
          console.log(">>>>", this.state.updateModal);
        });
    }

    // handleCloseModal = () => {
    //    this.setState({updateModal: false});
    //    console.log('close-----');
    // }

    handleSubmit = (upmu) => {
        this.props.handleUpdate(upmu);
//        this.setState({updateModal: false});
    }

    render() {
        const upmus = this.props.upmus;
        const {onClickDir, projectState, dirs, selectedProject, selectedDirId, onClickUpmu} = this.props;       
        // if (currentDir === ''){
        //     currentDir = "root" ;
        // }

        console.log('updateModal---', this.state.updateModal);
        const updateModal = this.state.updateModal 
            && <UpmuContentModal
                    upmu={this.state.upmu} 
                    onClose={this.handleCloseModal} 
                    handleTitleChange = {this.props.handleTitleChange}
                    handleContentChange = {this.props.handleContentChange}
                    handleSubmit = {this.handleSubmit}
                    //handleCloseUpdateModal = {this.props.handleCloseUpdateModal}
                    status="update"/>;

        console.log('selectedDirId---', selectedDirId);
        return (
            <div>
                <h2>{selectedDirId}</h2>
                <table className="table" id="upmuTable" cellSpacing="0">
                    <thead>
                        <tr>
                            <th>제목</th>
                            <th>유형</th>
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
                                    <td>폴더</td>
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
                                <tr value={selectedDirId} key={idx} onClick={() => this.handleClickUpmu(upmu)} data-toggle="modal" data-target="#UpmuUpdateModal">
                                    <td>{upmu.name}</td>
                                    <td>업무 일지</td>
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
                {updateModal}
            </div>
        )
    }
}

export default ContentTable;