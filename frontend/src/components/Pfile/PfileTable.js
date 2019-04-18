import React, { Component } from 'react';
import { Map, List } from 'immutable';
import UpdatePfileModal from './UpdatePfileModal';
import AttachmentItem from './AttachmentItem' ;

class PfileTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            dirs: List(),
            updateModal: false,
            pfile: {},
        };
    }

    handleClickPfile = (pfile) => {
        console.log(pfile);
        this.setState({updateModal: true})
        this.setState({ pfile }, () => {
          console.log(">>>>", this.state.pfile);
          console.log(">>>>", this.state.updateModal);
        });
    }

    render() {
        const pfiles = this.props.pfiles;
        const {onClickDir, projectState, dirs, selectedProject, selectedDirId, onClickPfile} = this.props;       
        

        console.log('updateModal---', this.state.updateModal);
        const updateModal = this.state.updateModal 
            && <UpdatePfileModal
                    pfile={this.state.pfile}
                    onClose={this.handleCloseModal} 
                    handleTitleChange = {this.props.handleTitleChange}
                    handleContentChange = {this.props.handleContentChange}
                    handleSubmit = {this.props.handleUpdate}
                    status="update"/>;

        console.log('selectedDirId---', selectedDirId);
        return (
            <div>
                <h2>{selectedDirId}</h2>
                <table className="table" id="PfileTable" cellSpacing="0">
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
                        <AttachmentItem > </AttachmentItem>
        
                        {pfiles && pfiles.map((pfile, idx) => {
                            return (
                                <tr value={selectedDirId} key={idx} >
                                    <td>{pfile.name}</td>
                                    <td>업무 일지</td>
                                    <td>{pfile.contents}</td>
                                    <td>{pfile.localPath}</td>
                                    <td>{pfile.newDate}</td>
                                    <td>{pfile.updateDate}</td>
                                    <td>
                                        <button onClick={() => this.handleClickPfile(pfile) } data-toggle="modal" data-target="#UpdatePfileModal" class="btn btn-info btn-circle btn-sm">
                                            <i class="fas fa-info-circle"></i>
                                        </button>
                                    </td>
                                    <td>
                                        <button onClick={() => this.props.handleDeletepfile(pfile.pfileId)} class="btn btn-danger btn-circle btn-sm">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                {updateModal}
            </div>
        )
    }
}

export default PfileTable;