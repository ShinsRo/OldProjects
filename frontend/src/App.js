import React, { Component } from 'react';
import logo from './logo.svg';
import { Header } from './components/Header'
import { Sidebar } from './components/Sidebar'
class App extends Component {
  render() {
    return (
      <div id="wrapper">
        <Sidebar/>
        {/* Content Wrapper */}
        <div id="content-wrapper" class="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <Header/>
          </div>

        </div>
      </div>
    );
  }
}

export default App;
