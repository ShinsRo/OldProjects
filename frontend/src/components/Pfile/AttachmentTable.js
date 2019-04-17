import React, { Component } from 'react';
import { Map, List } from 'immutable';
import axios from 'axios';

class AttachmentTable extends Component {

    constructor(props) {
        super(props);
        this.state ={
          uploadAttachment:null
        }
        this.onFormSubmit = this.onFormSubmit.bind(this)
        this.onChange = this.onChange.bind(this)
        this.attachmentUpload = this.attachmentUpload.bind(this)
      }


    onFormSubmit(e){
        e.preventDefault() // Stop form submit
        this.attachmentUpload(this.state.file).then((response)=>{
          console.log(response.data);
        })
      }

    onChange(e) {
       this.setState({uploadAttachment:e.target.files[0]})
       console.log(e.target.files[0]);
    }

    attachmentUpload(file){
       const url = 'http://localhost:8080/upmureport/attachment';
       const formData = new FormData();
       formData.append('file',file);
       formData.append('json', this.props.selectedDirId);
       const config = {
           headers: {
               'content-type': 'multipart/form-data'
           }
       }
       return  axios.post(url, formData, config)
    }

    


    render() {
        
        return (
            <div>
                <h2>첨부파일</h2>
                <span className="filebox"> 
                        <label for="ex_file">파일 가져오기</label>
                        <input type="file" id="ex_file" onChange={this.onChange}/>
                        <button type="submit" onClick={this.onFormSubmit} >Upload</button>
                        {this.state.uploadAttachment && this.state.uploadAttachment.name}
                      </span>
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
                        
                    </tbody>
                </table>
            </div>
        )
    }
}

export default AttachmentTable;