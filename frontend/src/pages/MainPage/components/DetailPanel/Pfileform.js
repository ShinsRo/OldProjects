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
        //const {PfileActions} = this.props;
        //PfileActions.changeTitleInput(e.target.value);
        this.setState({ name: input })
    }

    setContent = (input) => {
        // const {PfileActions} = this.props;
        // PfileActions.changeContentInput(e.target.value);
        this.setState({ contents: input })
    }

    handleTitleChange = (e) => {
        //const {PfileActions} = this.props;
        //PfileActions.changeTitleInput(e.target.value);
        this.setState({ name: e.target.value })
    }

    handleContentChange = (e) => {
        // const {PfileActions} = this.props;
        // PfileActions.changeContentInput(e.target.value);
        this.setState({ contents: e.target.value })
    }

    handleInsert = (e) => {
        e.preventDefault();

        // const {pfileState, projectState} = store.getState();
        const { selectedDirId, savePfile, saveItem, reloadPLog,setPfile, selectedProject } = this.props;

        const pfile = {
            name: this.state.name,
            contents: this.state.contents,
            pdirId: selectedDirId,
        };
        console.log("selectedProject ----------", selectedProject)
        reloadPLog(selectedProject)
        savePfile(pfile);
        setPfile(pfile);
        saveItem({ detailViewLevel: 'pfile' });
    }

    handleUpdate = (e) => {
        e.preventDefault();

        const { updatePfile, pfile,setPfile, saveItem } = this.props;



        const newPfile = {
            ...pfile,
            name: this.state.name,
            contents: this.state.contents,
        }

        updatePfile(newPfile);
        setPfile(newPfile);
        saveItem({ detailViewLevel: 'pfile' });
    }

    handleCancle = (e) => {
        e.preventDefault();

        const { saveItem } = this.props;

        saveItem({ detailViewLevel: 'pfile' })
    }

    render() {

        let submitBts;
        let cancleBts;
        let nameTextArea;
        let contentsTextArea;

        console.log('pfile form rendering --------------')
        
        
        if (this.props.status === 'add') {

            submitBts =
                    <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleInsert}>
                        추가 하기
                    </button>

            nameTextArea =
                <textarea className="form-control" rows='1' style={{ resize: 'none' }} defaultValue='' value={this.state.name} onChange={this.handleTitleChange} />

            contentsTextArea =
                <textarea className="form-control" rows='12' style={{ resize: 'none' }} defaultValue='' value={this.state.contents} onChange={this.handleContentChange} />

        }

        else if (this.props.status === 'update') {

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
                <textarea className="form-control" rows='12' style={{ resize: 'none' }} defaultValue={this.state.contents} onChange={this.handleContentChange} />
        }



        return (

            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="row">
                        <div className="col-2 text-lg font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                            제목
                        </div>
                        <div className="col-10">
                            {nameTextArea}
                        </div>
                    </div>

                </div>
                <div className="card-body">
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
