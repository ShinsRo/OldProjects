import React, { Component } from 'react';

class Pfileform extends Component {

    componentWillMount(){
        if(this.props.status === 'update'){            
            this.setTitle(this.props.pfile.name)
            this.setContent(this.props.pfile.contents)
        } else {
            this.setTitle('')
            this.setContent('')
        }
    }

    componentWillReceiveProps(nextProps) {        
        if(nextProps.status === 'update'){            
            this.setTitle(this.props.pfile.name)
            this.setContent(this.props.pfile.contents)
        } else {
            this.setTitle('')
            this.setContent('')
        }
    }

    shouldComponentUpdate(nextProps, nextState){
        return true;
    }

    setTitle = (input) => {
        this.setState({ name: input })
    }

    setContent = (input) => {
        this.setState({ contents: input })
    }

    handleTitleChange = (e) => {
        this.setState({ name: e.target.value })
    }

    handleContentChange = (e) => {
        this.setState({ contents: e.target.value })
    }

    handleInsert = (e) => {
        e.preventDefault();
        const { selectedDirId, savePfile, saveItem, reloadPLog, setPfile, selectedProject } = this.props;

        const pfile = {
            name: this.state.name,
            contents: this.state.contents,
            pdirId: selectedDirId,
        };
        
        savePfile(pfile).then(() => {reloadPLog(selectedProject.pid);});
        setPfile(pfile);
        saveItem({ detailViewLevel: 'pfile' });
    }

    handleUpdate = (e) => {
        e.preventDefault();

        const { updatePfile, pfile,setPfile, saveItem, reloadPLog, selectedProject } = this.props;

        const newPfile = {
            ...pfile,
            name: this.state.name,
            contents: this.state.contents,
        }

        updatePfile(newPfile).then(() => {reloadPLog(selectedProject.pid);});
        setPfile(newPfile);
        saveItem({ detailViewLevel: 'pfile' });
    }

    handleCancle = (e) => {
        e.preventDefault();

        const { saveItem } = this.props;

        saveItem({ detailViewLevel: 'pfile' })
    }

    render() {

        let title;
        let submitBts;
        let cancleBts;
        let nameTextArea;
        let contentsTextArea;
        
        if (this.props.status === 'add') {
            title = "파일을 추가 합니다."

            submitBts =
                    <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleInsert}>
                        추가 하기
                    </button>

            nameTextArea =
                <textarea className="form-control" rows='1' style={{ resize: 'none' }} defaultValue='' value={this.state.name} onChange={this.handleTitleChange} />

            contentsTextArea =
                <textarea className="form-control" rows='11' style={{ resize: 'none' }} defaultValue='' value={this.state.contents} onChange={this.handleContentChange} />

        }

        else if (this.props.status === 'update') {
            title = "파일을 수정 합니다."

            submitBts =
                <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleUpdate}>
                    수정 하기
                </button>

            cancleBts =
                <button type="button" className="btn btn-danger bg-danger p-2" onClick={this.handleCancle}>
                    취소 하기
                </button>

            nameTextArea =
                <textarea className="form-control" rows='1' style={{ resize: 'none' }} defaultValue={this.state.name} onChange={this.handleTitleChange} />

            contentsTextArea =
                <textarea className="form-control" rows='11' style={{ resize: 'none' }} defaultValue={this.state.contents} onChange={this.handleContentChange} />
        }



        return (

            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="row text-lg font-weight-bold text-dark-1" style={{textAlign: "center" ,marginLeft:"10px"}}>
                        {title}
                        {/* <div className="col-2 text-lg font-weight-bold text-dark-1" style={{textAlign: "right"}}>
                            {title}
                        </div>                         */}
                    </div>                    

                </div>
                <div className="card-body">
                    <div className="row mb-2">
                        <div className="col-2 text-lg font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                            제목
                        </div>
                        <div className="col-10">
                            {nameTextArea}
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-2 text-lg font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                            내용
                        </div>

                        <div className="col-10">
                            {contentsTextArea}
                        </div>
                    </div>

                    <div className="row justify-content-center mt-3">
                        <div className="btn-group" role="group" aria-label="Basic example">
                            {submitBts}
                            {cancleBts}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Pfileform;