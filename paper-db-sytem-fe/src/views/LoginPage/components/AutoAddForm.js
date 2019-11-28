import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { InputAdornment, Icon } from "@material-ui/core";
// @material-ui/icons
import People from "@material-ui/icons/People";
// core components
import CustomInput from "components/CustomInput/CustomInput.js";

import typoStyles from "assets/jss/material-kit-react/components/typographyStyle.js";
import styles from "assets/jss/material-kit-react/views/loginPage.js";

const useStyles = makeStyles(styles);
const useTypoStyles = makeStyles(typoStyles);

export default function AutoAddForm(props) {
    const classes = useStyles();
    const typoClasses = useTypoStyles();

    const [authors, setAuthors] = React.useState(['Baik SW', 'Baik, Sung Wook']);
    const [authorInput, setAuthorInput] = React.useState('');

    const addAuthor = author => {
        authors.append(author);
        setAuthors(authors);
    }

    const { 
        organization, setAuthorList
    } = props;

    const organizationHTML = (organization)? organization : `모든 연구기관`;
    const authorsHTML = (<>{authors.join('; ')}</>);

    return (
    // <form className={classes.form}>
    //     <h3 className={typoClasses.mutedText}>
    //     총 <span className={typoClasses.primaryText}>95</span>개의 논문이 검색되었습니다.<br/>
    //     결과를 내 논문 목록에 담습니다.
    //     </h3>
    // </form>
    <form className={classes.form}>
        <p  className={classes.divider}>논문 상에 게재하신 논문명을 추가하세요.</p>
        <h3 className={typoClasses.mutedText}>
            <span className={typoClasses.primaryText}>{organizationHTML}</span> 소속<br/>
            <span className={typoClasses.primaryText}>{authorsHTML}</span><br/>
            를 검색어로 논문 추가<br/>
        </h3>
        <CustomInput
        value={authorInput}
        onChange={e => {setAuthorInput(e.target.value)}}
        labelText="논문 상 영문명 추가"
        id="regAuthor"
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
    </form>
    );
}