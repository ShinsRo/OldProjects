import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { InputAdornment, Icon } from "@material-ui/core";
// @material-ui/icons
import People from "@material-ui/icons/People";
// core components
import CustomInput from "components/CustomInput/CustomInput.js";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";

import styles from "assets/jss/material-kit-react/views/loginPage.js";

const useStyles = makeStyles(styles);

export default function RegForm(props) {
    const classes = useStyles();

    const { 
        username, setUsername,
        password, setPassword,
        name, setName,
        organization, setOrganization
    } = props;
    return (
        <form className={classes.form}>
        <p className={classes.divider}>필수 정보를 기입해주세요.</p>

        <CustomInput
        value={username}
        onChange={e => {setUsername(e.target.value)}}
        labelText="Username"
        id="regUsername"
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
        id="legPassword"
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
        <CustomInput
        value={name}
        onChange={e => {setName(e.target.value)}}
        labelText="Name"
        id="regName"
        formControlProps={{
            fullWidth: true
        }}
        inputProps={{
            type: "text",
            endAdornment: (<InputAdornment position="end">
                                <CustomDropdown
                                    noLiPadding
                                    buttonText={organization || 'Other'}
                                    buttonProps={{
                                        color: "transparent",
                                    }}
                                    dropdownList={[
                                        <div onClick={(e) => {setOrganization('Sejong Univ')}}>Sejong Univ</div>,
                                        {divider: true},
                                        <div onClick={(e) => {setOrganization(null)}}>Other</div>,
                                    ]}
                                    />
                            </InputAdornment>)
        }}
        />
    </form>
    );
}