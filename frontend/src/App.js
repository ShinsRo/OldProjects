import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom';
import { Dashboard, Login } from "./pages";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: false
    }
  }

  render() {
    const { redirect } = this.state;

    if (redirect) {
      this.setState({ redirect: false });
      
      return (
        <div>
          <Redirect to="/login"/>;
          <Redirect to="/dashboard"/>;
        </div>
      )
    }

    return (
      <div>
        <Route exact path="/login" component={Login}></Route>
        <Route exact path="/dashboard" component={Dashboard}></Route>
      </div>
    );
  }
}

export default App;
