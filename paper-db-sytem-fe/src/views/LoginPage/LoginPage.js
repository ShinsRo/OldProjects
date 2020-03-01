import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
// core components
import Header from "components/Header/Header.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";

import { LoginFormCard, RegFormCard } from 'views/LoginPage/components'

import styles from "assets/jss/material-kit-react/views/loginPage.js";

import image from "assets/img/bg7.jpg";

const useStyles = makeStyles(styles);

export default function LoginPage(props) {
  if (window.sessionStorage.getItem('member')) window.location.href = '/main-page';

  const [contentNo, setContentNo] = React.useState(0);
  const contents = [
    <LoginFormCard  setContentNo={setContentNo}/>,
    <RegFormCard    setContentNo={setContentNo}/>,
  ];

  const classes = useStyles();
  const { ...rest } = props;

  return (
    <div>
      <Header
        absolute
        color="transparent"
        brand="세종대학교 WOS 논문 DB 조회"
        {...rest}
      />
      <div
        className={classes.pageHeader}
        style={{
          backgroundImage: "url(" + image + ")",
          backgroundSize: "cover",
          backgroundPosition: "top center"
        }}
      >
        <div className={classes.container}>
          <GridContainer justify="center">
            <GridItem xs={12} sm={8} md={4}>
              {contents[contentNo]}
            </GridItem>
          </GridContainer>
        </div>
      </div>
    </div>
  );
}
