import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import { Dashboard, Login } from ".";
import { DevTest } from ".";

class App extends Component {

  render() {
    return (
      <div>
        <Route exact path="/login" component={Login}></Route>
        <Route exact path="/dashboard" component={Dashboard}></Route>
        <Route exact path="/devtest" component={DevTest}></Route>
      </div>
    );
  }
}

export default App;
