import React from "react";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// core components
import Header from "components/Header/Header.js";
import HeaderLinks from "components/Header/HeaderLinks.js";

import Footer from "components/Footer/Footer.js";
import Parallax from "components/Parallax/Parallax.js";

import styles from "assets/jss/material-kit-react/views/profilePage.js";

import { WosSearch, RegisteredAuthorSearch, DbSearch } from "views/MainPage/components"

const useStyles = makeStyles(styles);

export default function MainPage(props) {
  const { ...rest } = props;

  const [contentType, setContentType] = React.useState("wos");

  const handleFuncClick = (e, func) => {
    setContentType(func);
  };

  return (
    <div>
      <Header
        color="transparent"
        brand="세종대학교 WOS 논문 DB 조회"
        rightLinks={<HeaderLinks handleFuncClick={handleFuncClick}/>}
        fixed
        changeColorOnScroll={{
          height: 200,
          color: "white"
        }}
        {...rest}
      />
      <Parallax small filter image={require("assets/img/profile-bg.jpg")}/>
      {(() => {
        if      (contentType === 'wos') return <WosSearch/>;
        else if (contentType === 'pdb') return <DbSearch/>;
        else if (contentType === 'ras') return <RegisteredAuthorSearch/>;

        return <>UNKOWN FUNCTION</>;
      })()}
      <Footer />
    </div>
  );
}
