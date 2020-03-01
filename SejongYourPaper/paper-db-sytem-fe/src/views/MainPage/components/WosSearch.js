
import { JAX2REST_SERVER_URL } from '_constants';
import { WokSearchClient } from "api/wos-api.js";

import WosRecordTable from "./WosRecordTable.js";

import React from "react";
import moment from "moment";

import Datetime from "react-datetime";

// nodejs library that concatenates classes
import classNames from "classnames";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { InputAdornment, CircularProgress } from "@material-ui/core";

// @material-ui/icons
import { Search, Work } from "@material-ui/icons";

import CustomInput from "components/CustomInput/CustomInput.js";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";

import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";

import GridContainer from "components/Grid/GridContainer";
import GridItem from "components/Grid/GridItem";

import styles from "assets/jss/material-kit-react/views/profilePage.js";

const useStyles = makeStyles(styles);


export default function WosSearch(props) {
    const classes = useStyles();

    const [searchClient, ] = React.useState(new WokSearchClient(JAX2REST_SERVER_URL));

    const nowDate = new Date();
    const aWeekAgo = new Date();
    aWeekAgo.setDate(nowDate.getDate() - 7);
    const [begin, setBegin] = React.useState(aWeekAgo);
    const [end, setEnd] = React.useState(nowDate);
    const [organization, setOrganizaiton] = React.useState('');
    const [userQuery, setUserQuery] = React.useState('');
    const [category, setCategory] = React.useState('TITLE');
    const categoryValue = {
        'TITLE'     : 'TI',
        'AUTHOR'    : 'AU',
        'DOI'       : 'DO',
        'NONE'      : 'TS',
    }

    const [records, setRecords] = React.useState([]);
    const [headers, setHeaders] = React.useState([]);
    const [pageState, setPageState] = React.useState(searchClient.getPageState());

    const [loading, setLoading] = React.useState(false);
    const Loading = loading? <CircularProgress/>: '';

    // search(userQuery, begin, end, symbolicTimeSpan, noLAMR)
    const fieldChange = (e, field) => {
        if          (field === 'query') {
            setUserQuery(e.target.value);
        } else if   (field === 'begin') {
            if (e.toDate() > end) return alert('시작 날짜가 끝 날짜보다 크면 안됩니다.');
            setBegin(e.toDate());
        } else if   (field === 'end') {
            if (e.toDate() < begin) return alert('끝 날짜가 시작 날짜보다 작으면 안됩니다.');
            setEnd(e.toDate());
        } else if   (field === 'organization') {
            setOrganizaiton(e.target.value);
        } else if   (field === 'category') {
            setCategory(e);
        }  else {
        }
    };

    const filter    = [3, 4, 7, 8, 9, 11, 12, 13, 19, 21];
    const process = (res) => {
        setRecords(searchClient.getRecords(filter));
        setHeaders(searchClient.getHeaders(filter));
        setPageState(searchClient.getPageState());
    }
    const onPageClick = (e) => {
        setLoading(true);
        let amount = 5;
        let start = Math.floor(+(searchClient.getPageState().currentPage - 1) / amount) * amount + 1;
        const page = 
            (e.target.innerText === 'PREV' && start - 1)        ||
            (e.target.innerText === 'NEXT' && start + amount)   || 
            Number(e.target.innerText);
        // if (pageState.currentPage === page) return;
        searchClient.retrive(page).then(res => {
            process(res);
            setLoading(false);
        }).catch(err => {
            console.error(err);
            setLoading(false);
        });
    };
    const setPageSize = (size) => {
        searchClient.setPageSize(size);
        onPageClick({ target: { innerText: `${1}` } });
    }

    const onSearch = () => {
        setLoading(true);
        const orgnList = [];
        if (organization) orgnList.push(organization);
        const query = searchClient.buildUserQuery(categoryValue[category], userQuery, orgnList);
        const beginString = moment(begin).format('YYYY-MM-DD');
        const endString = moment(end).format('YYYY-MM-DD');
        searchClient.search(query, beginString, endString, null, false)
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
                        transform: 'translate(-50%, -220px)'}}>Web of Science DB에서 검색하기</h2>
                    <Card style={{
                        position: 'absolute',
                        top: '0', left: '50%',
                        width: '90%',
                        transform: 'translate(-50%, -150px)'}}>
                        <CardBody>
                        <h4 className={classes.cardTitle}>Web of Science DB에서 검색하기</h4>
                            
                            <GridContainer justify="center">
                            <GridItem xs={12} sm={12} md={2} style={{marginTop: '25px'}}>
                                <Datetime
                                    onChange={(e) => {fieldChange(e, 'begin')}}
                                    value={begin}
                                    id="wosQueryStDate"
                                    timeFormat={false}
                                    dateFormat="YYYY-MM-DD"
                                    inputProps={{ placeholder: "검색 시작 기간", style: {paddingTop: '0px'} }}
                                />
                            </GridItem>
                            <GridItem xs={12} sm={12} md={2} style={{marginTop: '25px'}}>
                                <Datetime
                                    onChange={(e) => {fieldChange(e, 'end')}}
                                    value={end}
                                    id="wosQueryEdDate"
                                    timeFormat={false}
                                    dateFormat="YYYY-MM-DD"
                                    inputProps={{ placeholder: "검색 끝 기간", style: {paddingTop: '0px'} }}
                                />
                            </GridItem>
                            <GridItem xs={12} sm={12} md={2}>
                                <CustomInput
                                    value={organization}
                                    onChange={(e) => {fieldChange(e, 'organization')}}
                                    labelText="소속 기관"
                                    id="wosOrgn"
                                    formControlProps={{
                                        fullWidth: true
                                    }}
                                    inputProps={{
                                        endAdornment: (<InputAdornment position="end"><Work/></InputAdornment>),
                                    }}
                                />
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
                                                <div onClick={(e) => {fieldChange('AUTHOR', 'category')}}>AUTHOR</div>,
                                                {divider: true},
                                                <div onClick={(e) => {fieldChange('DOI', 'category')}}>DOI</div>,
                                                {divider: true},
                                                <div onClick={(e) => {fieldChange('NONE', 'category')}}>NONE</div>
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