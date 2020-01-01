/*eslint-disable*/
import React from "react";
// react components for routing our app without refresh
import { Link } from "react-router-dom";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";

// @material-ui/icons
import { Apps, ExitToApp } from "@material-ui/icons";

// core components
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";
import Button from "components/CustomButtons/Button.js";

import styles from "assets/jss/material-kit-react/components/headerLinksStyle.js";

const useStyles = makeStyles(styles);

export default function HeaderLinks(props) {
  const classes = useStyles();

  const logout = (e) => {
    window.sessionStorage.removeItem('member');
    window.location.href = '/';
  };

  const { handleFuncClick } = props;

  return (
    <List className={classes.list}>
      <ListItem className={classes.listItem}>
        <CustomDropdown
          noLiPadding
          buttonText="FUNCTIONS"
          buttonProps={{
            className: classes.navLink,
            color: "transparent"
          }}
          buttonIcon={Apps}
          dropdownList={[
            <a
              onClick={e => {handleFuncClick(e, 'wos')}}
              target="_blank"
              className={classes.dropdownLink}
            >
              Web of Science DB에서 검색하기
            </a>,
            <a
              onClick={e => {handleFuncClick(e, 'pdb')}}
              target="_blank"
              className={classes.dropdownLink}
            >
              담아둔 논문에서 검색하기
            </a>,
            <a
              onClick={e => {handleFuncClick(e, 'ras')}}
              target="_blank"
              className={classes.dropdownLink}
            >
              등록된 저자의 논문보기
            </a>
          ]}
        />
      </ListItem>
      <ListItem className={classes.listItem}>
        <Button
          onClick = {logout}
          color="transparent"
          target="_blank"
          className={classes.navLink}
        >
          <ExitToApp className={classes.icons} /> 로그아웃
        </Button>
      </ListItem>
      
    </List>
  );
}
