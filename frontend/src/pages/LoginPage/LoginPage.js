import React, { Component } from 'react';
import LoginContainer from './containers/LoginContainer';

const styleCenter = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)' 
};
const loginFormCardStyle = { marginLeft: '6%', transform: 'translate(0%, -20%)', width: '426px'};
const mainTitleStyle = {  marginLeft: '40%', transform: 'translate(0%, -50%)' }
const fullHeightStyle = { height: '100vh', padding: '0px', overflow: 'hidden' };
const logoStyle = { transform: 'translate(60%, 100%)', width: 100, height: 47, position: "absolute" };

class LoginPage extends Component {

    render() {

        return (<>
            <img
                src={process.env.PUBLIC_URL + '/resources/img/nastech.png'} 
                alt="main_logo" 
                style={logoStyle}/>
            <div className="container-fluid d-flex align-items-center flex-wrap" style={ fullHeightStyle }>
            
                <div className="row flex-grow-1">
                    <div className="col-6 p-0">
                        <h1 className="display-3 font-weight-bold text-nowrap" style={{...mainTitleStyle, color: 'black' }}>
                            WEB
                        </h1>
                        <h1 className="display-3 font-weight-bold text-nowrap" style={{...mainTitleStyle, color: 'white' }}>
                            <span className="bg-darkblue pt-4 pb-4 pl-5 pr-5 rounded">UPMUREPORT</span>
                        </h1>
                        <h1 className="display-3 font-weight-bold text-nowrap" style={{...mainTitleStyle, color: 'white' }}>
                            PROJECT-MANAGER
                        </h1>
                    </div>
                    <div className="col-6 p-0">
                        <div className="card shadow-lg p-5" style={loginFormCardStyle}>
                            <div className="card-body p-0">
                                    <LoginContainer history={this.props.history}/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>);
    }
}

export default LoginPage;