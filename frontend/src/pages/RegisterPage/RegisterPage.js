import React from "react";
import {Map} from 'immutable';

var fullScreen = {
    height: '100%',
    width: '100%',
    position: 'absolute',
    top: 0, left: 0
};

const Register = () => {

        return (
            <div className="bg-gradient-darkblue" style={fullScreen}>
    
                <div className="container">
    
                    {/* <!-- Outer Row --> */}
                    <div className="row justify-content-center">
    
                    {/* <div className="col-xl-10 col-lg-12 col-md-9"> */}
                    <div className="col-xl-6">
    
                        <div className="card o-hidden border-0 shadow-lg my-5">
                        <div className="card-body p-0">
                            {/* <!-- Nested Row within Card Body --> */}
                            <div className="row">
                            {/* <div className="col-lg-6 d-none d-lg-block bg-login-image"></div> */}
                            <div className="col-xl-12">
                                <div className="p-5">
                                <hr />
                                <div className="text-center">
                                    <h1 className="h4 text-gray-700 mb-4">신규 사원 등록</h1>
                                </div>
                                <form className="User" action="" method="post">
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="username" placeholder="아이디" />
                                    </div>
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="password" placeholder="비밀번호" />
                                    </div>
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="eid" placeholder="사번" />
                                    </div>
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="name" placeholder="이름" />
                                    </div>
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="birth" placeholder="생일" />
                                    </div>
                                    <div className="form-group">
                                    <input value='' onChange type="text" className="form-control form-control-user" name="phoneNum" placeholder="핸드폰" />
                                    </div>
                                
                                    <div className="form-group">
                                    <div className="custom-control custom-checkbox small">
                                        <input type="checkbox" className="custom-control-input" id="customCheck" />
                                        <label className="custom-control-label" htmlFor="customCheck">Remember Me</label>
                                    </div>
                                    </div>
                                    <input type="button" onClick className="btn btn-darkblue btn-user btn-block" value="등록" />
                                    <hr />
                                </form>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
    
                    </div>
    
                    </div>
    
                </div>
    
                {/* <!-- Bootstrap core JavaScript--> */}
                <script src="vendor/jquery/jquery.min.js"></script>
                <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    
                {/* <!-- Core plugin JavaScript--> */}
                <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    
                {/* <!-- Custom scripts for all pages--> */}
                <script src="js/sb-admin-2.min.js"></script>
    
            </div>
        );    
}   
Register.defaultProps= {
    inputId:'',
    inputPass:'',
    user: Map({
        id:'0',
        pass:'1'
    }),
}

export default Register;