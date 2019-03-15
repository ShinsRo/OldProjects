import React, { Component } from 'react';
import { Route } from 'react-router-dom';

import { Header } from './components/Header'
import { Sidebar } from './components/Sidebar'
import { Dashboard, Login } from "./pages";

class App extends Component {
  render() {
    return (
      <div>
        <Route exact path="/Login" component={Login}></Route>
        <Route exact path="/Dashboard" component={Dashboard}></Route>
      </div>
    );
  }
}

export default App;
