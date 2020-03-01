import React from "react";

const NotFound = () => {
    return (
      <div id="wrapper">
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content" style={{ backgroundColor: 'white' }}>
            {/* Page Content  */}
            <div className="container-fluid">
                <div className="text-center">
                    <div className="error mx-auto" data-text="404">404</div>
                    <p className="lead text-gray-800 mb-5">페이지를 찾을 수 없습니다.</p>
                </div>
            </div>
          </div>

        </div>
      </div>
    );
};
export default NotFound;