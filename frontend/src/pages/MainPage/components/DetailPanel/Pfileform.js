import React, { Component } from 'react';

class Pfileform extends Component {


    static defaultProps = {
        status: 'add',
        pfile: '',
        selectedDirId: '',
    }

    componentDidMount() {
        this.setState({
            name: this.props.pfile.name,
            contents: this.props.pfile.contents,
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
        console.log(pfile);
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
        const { pfile } = this.props;
        let submitBts;
        let cancleBts;
        if (this.props.status === 'add') {
            submitBts =
                <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleInsert}>
                    추가 하기
                        </button>

        }
        else if (this.props.status === 'update') {
            submitBts =
                <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleUpdate}>
                    수정 하기
                        </button>

        }


        if (this.props.status === 'update') {
            cancleBts =
                <button type="button" className="btn btn-danger bg-danger p-2" onClick={this.handleCancle}>
                    취소 하기
                </button>

        }


        return (
            
                <div className="card shadow mb-4">
                    <div className="card-body">
                        <div className="m-0 font-weight-bold text-darkblue">
                            <h2>제목</h2>
                            <hr />
                            <textarea className="form-control" defaultValue={pfile.name} onChange={this.handleTitleChange} />
                        </div>
                    </div>
                    <div className="card-body">
                        <div className="m-0 font-weight-bold text-darkblue">
                            <h2>내용</h2>
                            <hr />
                            <textarea className="form-control" defaultValue={pfile.contents} onChange={this.handleContentChange} />
                        </div>
                    </div>

                    <div className="card-body">
                        <div className="row justify-content-end">
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