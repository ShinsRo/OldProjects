import React, { Component } from 'react';

class Pfileform extends Component {


    static defaultProps = {
        status: 'add',
        pfile: '',
        selectedDirId: '',
    }

    componentWillReceiveProps() {
        this.setState({
            pfile: this.props
        })
    }

    state = {
        name: '',
        contents: '',
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
        const { selectedDirId, savePfile, saveItem, setPfile } = this.props;

        const pfile = {
            name: this.state.name,
            contents: this.state.contents,
            pdirId: selectedDirId,
        };

        setPfile(pfile);
        
        savePfile(pfile);
        saveItem({ detailViewLevel: 'pfile' });
    }

    handleUpdate = (e) => {
        e.preventDefault();

        const { updatePfile, pfile, saveItem, setPfile } = this.props;

        const newPfile = {
            ...pfile,
            name: this.state.name,
            contents: this.state.contents,
        }

        setPfile(newPfile);
        console.log(newPfile);
        updatePfile(newPfile);
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


        if (this.props.status === 'add') {

            submitBts =
                <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleInsert}>
                    추가 하기
                        </button>

            nameTextArea =
                <textarea className="form-control" rows='1' style={{ resize: 'none' }} value={this.state.name} onChange={this.handleTitleChange} />

            contentsTextArea =
                <textarea className="form-control" rows='13' style={{ resize: 'none' }} value={this.state.contents} onChange={this.handleContentChange} />

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
                <textarea className="form-control" rows='1' style={{ resize: 'none' }} defaultValue={this.props.pfile.name} onChange={this.handleTitleChange} />

            contentsTextArea =
                <textarea className="form-control" rows='13' style={{ resize: 'none' }} defaultValue={this.props.pfile.contents} onChange={this.handleContentChange} />
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
