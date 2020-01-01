import React from "react";
import ReactDOM from "react-dom";
import { createBrowserHistory } from "history";
import { Router, Route, Switch } from "react-router-dom";

import "assets/css/material-kit-react.css";

// pages for this product
import LoginPage from "views/LoginPage/LoginPage.js";
import MainPage from "views/MainPage/MainPage.js";

var hist = createBrowserHistory();

ReactDOM.render(
  <Router history={hist}>
    <Switch>
      <Route path="/main-page" component={MainPage} />
      <Route path="/login-page" component={LoginPage} />
      <Route path="/" component={LoginPage} />
    </Switch>
  </Router>,
  document.getElementById("root")
);
