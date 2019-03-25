import React, { Component } from 'react';

class ContentTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const upmus = this.props.upmus;

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
                        {upmus.map((upmu, idx) => {
                            console.log('--------', upmu);
                            return (
                                <tr key={idx}>
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