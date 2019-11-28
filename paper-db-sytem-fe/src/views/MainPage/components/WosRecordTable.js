import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import GridContainer from "components/Grid/GridContainer";
import GridItem from "components/Grid/GridItem";

import Paginations from "components/Pagination/Pagination.js";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";

const useStyles = makeStyles(theme => ({
    root: {
      width: '100%',
      marginBottom: theme.spacing(2),
    },
    paper: {
      marginTop: theme.spacing(3),
      width: '100%',
      overflowX: 'auto',
      marginBottom: theme.spacing(2),
    },
    table: {
      minWidth: 650,
    },
    cell: {
      maxWidth: 200,
      overflowX: 'scroll',
      whiteSpace: 'nowrap',
      direction: 'rtl',
    }
}));

const pagesTransform = (now, end) => {
    const formedPages = [];

    let amount = 5;
    let start = Math.floor(+(now - 1) / amount) * amount + 1;

    if (start !== 1) formedPages.push({ text: 'PREV' });
    for (let i = start; i <= end; i++) {
        if (start + amount === i) { formedPages.push({ text: 'NEXT' }); break; }

        if (i === now) formedPages.push({ active: true, text: i });
        else formedPages.push({ text: i });
    }
    return formedPages;
}

export default function WosRecordTable(props) {
    const classes = useStyles();
    const {records, headers, pageState, onPageClick, setPageSize} = props;

    if (!records || !records.length) {
        return <div><br/><h1>NO RECORDS AVAILABLE</h1></div>
    }
    
    const pagesElements = pagesTransform(pageState.currentPage, pageState.endPage);
    return (
        <div className={classes.root} style={{ paddingBottom: '100px' }}>
            <GridContainer>
                <GridItem xs={12} sm={12} md={8} style={{ textAlign: 'left', paddingTop: '24px' }}>
                    <br/><h1>RECORDS FOUND : {pageState.recordsFound}</h1>
                </GridItem>
                <GridItem xs={12} sm={12} md={4} style={{ textAlign: 'right', paddingTop: '24px' }}>
                    <CustomDropdown
                        noLiPadding
                        buttonText={<h3>{pageState.pageSize}</h3>}
                        buttonProps={{
                            color: "transparent"
                        }}
                        dropdownList={[
                            <h3 onClick={(e) => {setPageSize(10)}}>10</h3>,
                            {divider: true},
                            <h3 onClick={(e) => {setPageSize(25)}}>25</h3>,
                            {divider: true},
                            <h3 onClick={(e) => {setPageSize(50)}}>50</h3>
                        ]}
                        />
                </GridItem>
            </GridContainer>
            <Paper className={classes.paper}>
            <Table className={classes.table} size="small" aria-label="a dense table">
                <TableHead>
                <TableRow>
                    <TableCell className={classes.cell} component="th">NO</TableCell>
                    {headers.map((col, idx) => 
                        <TableCell className={classes.cell} component="th" key={`col-${idx}`}align="right">{col}</TableCell>)}
                </TableRow>
                </TableHead>
                <TableBody>
                {records.map((record, idx) => (
                    <TableRow key={`rcd-${idx}`}>
                    <TableCell key={`rcd-no${idx}`} className={classes.cell} component="th" scope="row">
                        {pageState.firstRecord + idx}
                    </TableCell>
                    {record.map((value, jdx) => {
                        if (jdx === 1) return <TableCell className={classes.cell} key={`val-${jdx}`} component="td" align="right"><a href={value}>이동</a></TableCell>;

                        return <TableCell className={classes.cell} key={`val-${jdx}`} component="td" align="right">{value}</TableCell>;
                    })}
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            <GridContainer>
                <GridItem xs={12} sm={12} md={12} style={{ textAlign: 'center', paddingTop: '24px' }}>
                    <Paginations justify="center"
                        onClick={onPageClick}
                        pages={pagesElements}
                    />
                </GridItem>
            </GridContainer>
            </Paper>
        </div>
    );
}