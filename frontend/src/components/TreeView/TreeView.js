import React from "react";

class TreeView extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            treeData: [{ title: "src/", children: [{ title: "index.js" }] }]
        };
    }
    render() {
        return (
            <div style={{ height: 500 }}>
            </div>
        );
    }
}

export default TreeView;