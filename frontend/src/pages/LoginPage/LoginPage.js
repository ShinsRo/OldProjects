import React, { Component } from 'react';
import LoginContainer from './containers/LoginContainer';

// const styleCenter = {
//     position: 'absolute',
//     top: '50%',
//     left: '50%',
//     marginRight: '-50%',
//     transform: 'translate(-50%, -50%)' 
// };
// const mainTitleStyle = {  marginLeft: '40%', transform: 'translate(0%, -50%)' }
// const logoStyle = { transform: 'translate(60%, 100%)', width: 100, height: 47, position: "absolute" };
const loginFormCardStyle = { marginLeft: '30%', transform: 'translate(120%, -10%)', width: '426px'};
const fullHeightStyle = { height: '92.2vh', width: '100%', padding: '0px', overflow: 'hidden' };

class LoginPage extends Component {

    render() {

        return (<div style={{ overflow: 'hidden' }}>
            <div className="container-fluid d-flex align-items-center flex-wrap" style={ fullHeightStyle }>
                <div className="row flex-grow-1">
                    <div className="col-12 p-0">
                        <>
                            <img
                                src={process.env.PUBLIC_URL + '/resources/img/undraw_setup_analytics_8qkl.svg'} 
                                alt="main_ill"
                                style={{ position: 'absolute', transform: 'translate(20%, -30%)', width: '1000px' }}
                            />
                            <div className="card shadow-lg p-5" style={loginFormCardStyle}>
                                <div className="mb-3">
                                    <h2 className="font-weight-bold" style={ { color: 'black' } }>UPMUREPORT</h2>
                                    <h2 className="font-weight-bold text-bright-1">WEB</h2>
                                </div>
                                <div className="card-body p-0">
                                        <LoginContainer history={this.props.history}/>
                                </div>
                            </div>
                        </>
                    </div>
                </div>
            </div>
            <footer className="sticky-footer bg-dark-2">
                <div className="container my-auto">
                <div className="copyright text-center my-auto">
                    <span>2019 UPMUREPORT | 김승신 | 김윤상 | 마규석 |</span>
                </div>
                </div>
            </footer>
        </div>);
    }
}

export default LoginPage;