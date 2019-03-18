import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import { Dashboard, Login } from ".";
import { DashboardTest } from ".";

class App extends Component {

  render() {
    return (
      <div>
        <Route exact path="/login" component={Login}></Route>
        <Route exact path="/dashboard" component={Dashboard}></Route>
        <Route exact path="/dashboardtest" component={DashboardTest}></Route>
      </div>
    );
  }
}

export default App;
