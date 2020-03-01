import { YOUR_PAPER_SERVER_URL } from '_constants';
import MemberApi from "api/member-api.js";

import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
// core components
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import RegForm from './RegForm';
import AutoAddForm from './AutoAddForm';

import styles from "assets/jss/material-kit-react/views/loginPage.js";

const useStyles = makeStyles(styles);

export default function RegFormCard(props) {
    const classes = useStyles();

    const [memberApi, ] = React.useState(new MemberApi(YOUR_PAPER_SERVER_URL));

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [name, setName] = React.useState("");
    const [organization, setOrganization] = React.useState("Sejong Univ");

    const [authorNameList, setAuthorNameList] = React.useState("");

    const [formNo, setFormNo] = React.useState(0);
    const forms = [
        <RegForm
            username={username}
            setUsername={setUsername}
            password={password}
            setPassword={setPassword}
            name={name}
            setName={setName}
            organization={organization}
            setOrganization={setOrganization}
        />,
        <AutoAddForm
            organization={organization}
            setAuthorNameList={setAuthorNameList}
        />
    ];

    const [cardAnimaton, ] = React.useState("");

    const onRegClick = () => {
        const organizationList = [organization];
        memberApi.register(
            username, password,
            name, authorNameList, organizationList
        ).then(res => {

        }).catch(err => {

        });
    };

    return (
        <Card className={classes[cardAnimaton]}>
            <CardHeader color="primary" className={classes.cardHeader}>
            <h4>REGISTER</h4>
            </CardHeader>
            <CardBody>
                {forms[formNo]}
            </CardBody>
            <CardFooter className={classes.cardFooter}>
            <Button onClick={() => props.setContentNo(0)} simple color="danger" size="lg">
                첫화면으로
            </Button>
            <Button onClick={() => setFormNo(formNo + 1)} simple color="primary" size="lg">
                계속하기
            </Button>
            </CardFooter>
        </Card>
    );
}