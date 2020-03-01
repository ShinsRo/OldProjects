import { YOUR_PAPER_SERVER_URL } from '_constants';
import MemberApi from "api/member-api.js";

import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { InputAdornment, Icon } from "@material-ui/core";
// @material-ui/icons
import People from "@material-ui/icons/People";
// core components
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import CustomInput from "components/CustomInput/CustomInput.js";

import styles from "assets/jss/material-kit-react/views/loginPage.js";

const useStyles = makeStyles(styles);

export default function LoginForm(props) {
    const classes = useStyles();

    const [memberApi, ] = React.useState(new MemberApi(YOUR_PAPER_SERVER_URL));
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    const [cardAnimaton, setCardAnimation] = React.useState("cardHidden");

    setTimeout(function() {
        setCardAnimation("");
    }, 700);

    const loginClick = e => {
        memberApi.memberLogin(username, password).then(res => {
        res['token'] = memberApi.token;

        const memberInfoString = JSON.stringify(res);
        window.sessionStorage.setItem("member", memberInfoString);
        window.location.href = '/landing-page';
        }).catch(err => {
        console.error(err);
        alert("아이디가 존재하지 않거나, 비밀번호가 일치하지 않습니다.");
        });
    };

    return (
        <Card className={classes[cardAnimaton]}>
            <CardHeader color="primary" className={classes.cardHeader}>
            <h4>LOGIN</h4>
            </CardHeader>
            <CardBody>
            <form className={classes.form}>
                <p className={classes.divider}>로그인이 필요합니다.</p>

                <CustomInput
                value={username}
                onChange={e => {setUsername(e.target.value)}}
                labelText="Username"
                id="loginUsername"
                formControlProps={{
                    fullWidth: true
                }}
                inputProps={{
                    type: "text",
                    endAdornment: (
                    <InputAdornment position="end">
                        <People className={classes.inputIconsColor} />
                    </InputAdornment>
                    )
                }}
                />
                <CustomInput
                value={password}
                onChange={e => {setPassword(e.target.value)}}
                labelText="Password"
                id="loginPassword"
                formControlProps={{
                    fullWidth: true
                }}
                inputProps={{
                    type: "password",
                    endAdornment: (
                    <InputAdornment position="end">
                        <Icon className={classes.inputIconsColor}>
                        lock_outline
                        </Icon>
                    </InputAdornment>
                    ),
                    autoComplete: "off"
                }}
                />
            </form>
            </CardBody>
            <CardFooter className={classes.cardFooter}>
            <Button onClick={loginClick} simple color="primary" size="lg">
                로그인
            </Button>
            <Button onClick={() => props.setContentNo(1)} simple color="danger" size="lg">
                가입하기
            </Button>
            </CardFooter>
        </Card>
    );
}