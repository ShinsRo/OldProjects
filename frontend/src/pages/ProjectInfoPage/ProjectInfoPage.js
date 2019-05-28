import React, { Component } from "react";
import HeaderContainer from './containers/HeaderContainer';

class ProjectInfoPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
    }

    
  }

  render() {
    let content = (<>EMPTY</>);

    content = (
    <div className="row"  >
        <div className="col-6">
          <div className="card shadow mb-4" style={{ height: '850px' }}>
            1
          </div>
        </div>
        <div className="col-6">
          <div className="row" style={{ height: '480px' }}>
            <div className="card shadow" style={{ width: '100%' }}>
                2
            </div>
          </div>
          <div className="row" style={{ height: '20px' }}></div>
          <div className="row" style={{ height: '350px' }}>
            <div className="card shadow" style={{ width: '100%' }}>
                3
            </div>
          </div>
        </div>
      </div>);
    
    return (
      <div id="wrapper">
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            {/* Page Content  */}
            <HeaderContainer history={this.props.history} />
            <div className="container-fluid pb-0" style={{ height: '90%', maxHeight: '880px', overflow: 'hidden' }}>
                {content}
            </div>
          </div>
        </div>
      </div>
    );
  }
};
export default ProjectInfoPage;