
import { YOUR_PAPER_SERVER_URL } from '_constants';
import { PaperRecordContainer, FIELD, CRITERIA } from "api/paper-api.js";

import WosRecordTable from "./WosRecordTable.js";

import React from "react";

// nodejs library that concatenates classes
import classNames from "classnames";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { InputAdornment, CircularProgress } from "@material-ui/core";

// @material-ui/icons
import { Search } from "@material-ui/icons";

import CustomInput from "components/CustomInput/CustomInput.js";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";

import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";

import GridContainer from "components/Grid/GridContainer";
import GridItem from "components/Grid/GridItem";

import styles from "assets/jss/material-kit-react/views/profilePage.js";

const useStyles = makeStyles(styles);


export default function DbSearch(props) {
    const classes = useStyles();
    if (!window.sessionStorage.getItem('member')) window.location.href = '/';
    const member = JSON.parse(window.sessionStorage.getItem('member'));
    const [paperContainer, ] = React.useState(new PaperRecordContainer(member.username, member.token, YOUR_PAPER_SERVER_URL));

    const [userQuery, setUserQuery] = React.useState('');
    const [category, setCategory] = React.useState('TITLE');
    const [pageState, setPageState] = React.useState(paperContainer.getPageState());

    const [records, setRecords] = React.useState([]);
    const [headers, setHeaders] = React.useState([]);
    const [loading, setLoading] = React.useState(false);
    const Loading = loading? <CircularProgress/>: '';

    // search(userQuery, begin, end, symbolicTimeSpan, noLAMR)
    const fieldChange = (e, field) => {
        if          (field === 'query') {
            setUserQuery(e.target.value);
        } else if   (field === 'category') {
            setCategory(e);
        }  else {
        }
    };

    const filter    = [3, 4, 1, 2, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21];
    const process = (res) => {
        setRecords(paperContainer.getRecords(filter));
        setHeaders(paperContainer.getHeaders(filter));
        setPageState(paperContainer.getPageState());
        console.log('pageSate', paperContainer.getPageState());
        
    }
    const onPageClick = (e) => {
        setLoading(true);
        let amount = 5;
        let start = Math.floor(+(paperContainer.getPageState().currentPage - 1) / amount) * amount + 1;
        const page = 
            (e.target.innerText === 'PREV' && start - 1)        ||
            (e.target.innerText === 'NEXT' && start + amount)   || 
            Number(e.target.innerText);
        // if (pageState.currentPage === page) return;
        paperContainer.retrive(page).then(res => {
            process(res);
            setLoading(false);
        }).catch(err => {
            console.error(err);
            setLoading(false);
        });
    };
    const setPageSize = (size) => {
        paperContainer.setPageSize(size);
        onPageClick({ target: { innerText: `${1}` } });
    }

    const onSearch = () => {
        setLoading(true);
        const criteria = [{ 'field': FIELD[category], 'operation': CRITERIA.LIKE, 'value': userQuery }];
        paperContainer.listByPage(1, 25, FIELD.TIMES_CITED, false, criteria)
            .then(res => {
                console.log(res);
                process(res);
                setLoading(false);
            }).catch(err => {
                console.error(err);
                setLoading(false);
            });
    };

    return (
        <div className={classNames(classes.main, classes.mainRaised)}>
            <div>
                <div className={classes.container}>
                <h2 style={{
                        position: 'absolute',
                        top: '0', left: '50%',
                        width: '90%',
                        textAlign: 'center',
                        color: 'white',
                        transform: 'translate(-50%, -220px)'}}>담아둔 논문에서 검색하기</h2>
                    <Card style={{
                        position: 'absolute',
                        top: '0', left: '50%',
                        width: '90%',
                        transform: 'translate(-50%, -150px)'}}>
                        <CardBody>
                        <h4 className={classes.cardTitle}>담아둔 논문에서 검색하기</h4>
                            
                            <GridContainer justify="center">
                            <GridItem xs={12} sm={12} md={2}>
                            </GridItem>
                            <GridItem xs={12} sm={12} md={6}>
                                <div style={{ textAlign: 'center' }}>{Loading}</div>
                            </GridItem>
                            <GridItem xs={12} sm={12} md={12}>
                                <CustomInput
                                value={userQuery}
                                onChange={(e) => {fieldChange(e, 'query')}}
                                labelText="검색어"
                                id="wosQuery"
                                formControlProps={{
                                    fullWidth: true
                                }}
                                inputProps={{
                                    endAdornment: (<InputAdornment position="end" onClick={(e) => {onSearch()}}><Search style={{ cursor: 'pointer' }}/></InputAdornment>),
                                    startAdornment: (<InputAdornment position="start">
                                        <CustomDropdown
                                            noLiPadding
                                            buttonText={category}
                                            buttonProps={{
                                                color: "transparent"
                                            }}
                                            dropdownList={[
                                                <div onClick={(e) => {fieldChange('TITLE', 'category')}}>TITLE</div>,
                                                {divider: true},
                                                <div onClick={(e) => {fieldChange('DOI', 'category')}}>DOI</div>,
                                                {divider: true},
                                                <div onClick={(e) => {fieldChange('AUTHORS', 'category')}}>AUTHORS</div>,
                                                {divider: true},
                                                <div onClick={(e) => {fieldChange('YEAR', 'category')}}>YEAR</div>
                                            ]}
                                            />
                                    </InputAdornment>)
                                }}
                                />
                            </GridItem>
                            </GridContainer>
                            {/* <Button color="primary">Do something</Button> */}
                        </CardBody>
                    </Card>
                    
                </div>
            </div>
            <div>
                <div className={classes.container} style={{ minHeight: '80vh'}}>
                    <div style={{ height: '100px' }}></div>
                    <div>
                    <WosRecordTable 
                        headers={headers} 
                        records={records} 
                        pageState={pageState}
                        onPageClick={onPageClick}
                        setPageSize={setPageSize}
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}