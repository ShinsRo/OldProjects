<template>
  <v-layout row wrap>
    <v-flex xs12 class='mb-3'>
      <h1 class="headline">엑셀 취합 하기</h1>
      <v-divider></v-divider>
    </v-flex>
    <!-- 결과표 -->
    <v-flex xs12>
      <v-card>
        <v-card-title>
          취합하실 항목
          <v-spacer></v-spacer>
          <v-text-field
            v-model="listSearch"
            append-icon="search"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="resHeaders"
          :items="inteList"
          :search="listSearch"
          :pagination.sync="pagination"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr>
              <td >
                <v-icon @click="deleteRow(props.item)">delete</v-icon>
              </td>
              <!-- <td >{{ props.item[0] }}</td> -->
              <td >{{ props.item[1] }}</td>
              <td >{{ props.item[2] }}</td>
              <td >{{ props.item[3] }}</td>
              <td >{{ props.item[4] }}</td>
              <td >{{ props.item[5] }}</td>
              <td >{{ props.item[6] }}</td>
              <td >{{ props.item[7] }}</td>
              <td >{{ props.item[8] }}</td>
              <td >{{ props.item[9] }}</td>
              <!-- <td >{{ props.item[10] }}</td> -->
              <td >{{ props.item[11] }}</td>
              <td >{{ props.item[12] }}</td>
              <td v-html="`${props.item[13]}/${props.item[14]}<br>${props.item[15]}`"></td>
              <td >{{ props.item[16] }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            Your search for "{{ listSearch }}" found no results.
          </v-alert>
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 결과표 -->
    <v-flex xs12>
      <v-card>
        <v-toolbar color="" dark>
          <v-toolbar-title>선택된 파일
          </v-toolbar-title>
          <v-flex class="text-xs-center">이전 저장 경로 : {{ recentFilePath }}</v-flex>
          <v-spacer></v-spacer>
          <v-btn @click="download" v-if="inteList.length">취합한 파일 다운로드 하기</v-btn>
          <multi-file-select v-model="file"></multi-file-select>
          <v-btn @click="integrateAll">취합하기</v-btn>

        </v-toolbar>
        <v-list>
          <v-list-tile
            v-for="item in file"
            :key="item.name"
          >
            <v-list-tile-content>
              <v-list-tile-title v-text="item.path"></v-list-tile-title>
            </v-list-tile-content>

          </v-list-tile>
        </v-list>
      </v-card>
    </v-flex>
    <!-- 로딩 -->
    <v-dialog
      v-model="saveLoading"
      hide-overlay
      persistent
      width="300"
    >
      <v-card
        color="primary"
        dark
      >
        <v-card-text>
          저장 중입니다.
          <v-progress-linear
            indeterminate
            color="white"
            class="mb-0"
          ></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="alert.show" persistent max-width="290">
      <v-card>
        <v-card-title class="headline">알림</v-card-title>
        <v-card-text>{{ alert.msg }}</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" flat @click="alert.show = false">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import { Workbook } from 'exceljs';
import { homedir } from 'os';
import MultiFileSelect from './excelIntegrationView/MultiFileSelect.vue';

export default {
  // props: ['resHeaders'],
  data() {
    return {
      gubun: 0,
      file: null,
      inteList: [],
      count: 0,
      fileName: 'sejong_wos_output',
      number: 0,
      ext: 'xlsx',
      dialog: false,
      recentFilePath: '없음',
      excelErrShow: false,
      excelErrMSG: '',
      saveLoading: false,
      listSearch: '',
      alert: {
        show: false,
        msg: '',
      },
      pagination: {
        sortBy: 'index',
        descending: true,
      },
      resHeaders: [
        { text: '행삭제', align: 'left', value: '__icon', width: '5px', sortable: false },
        // { text: '번호', align: 'left', value: 'index', width: '5px' },
        { text: '제목', align: 'left', value: 'title', width: '20px' },
        { text: '교신저자', align: 'left', value: 'reprint', width: '30px' },
        { text: '저자 목록', align: 'left', value: 'authors', width: '30px' },
        { text: '발행분류', align: 'left', value: 'docType', width: '10px' },
        { text: '발행년월', align: 'left', value: 'publishedMonth', width: '5px' },
        { text: '등급', align: 'left', value: 'capedGrades', width: '20px' },
        { text: '백분율', align: 'left', value: 'goodRank', width: '5px' },
        { text: 'IF', align: 'left', value: 'prevYearIF', width: '5px' },
        { text: '피인용', align: 'left', value: 'timesCited', width: '5px' },
        { text: '저널명', align: 'left', value: 'journal_name', width: '20px' },
        { text: '발행처', align: 'left', value: 'publisher', width: '20px' },
        { text: 'ISSN', align: 'left', value: 'issn', width: '5px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: '언어', align: 'left', value: 'language', width: '10px' },
      ],
    };
  },
  components: {
    MultiFileSelect,
  },
  methods: {
    showAlert(msg) {
      this.alert.show = true;
      this.alert.msg = msg;
    },
    download() {
      this.saveLoading = true;
      this.excelErrShow = false;
      const fs = require('fs');
      let targetPath = `${homedir()}\\Downloads\\${this.fileName}.${this.ext}`;
      while (fs.existsSync(targetPath)) {
        targetPath = `${homedir()}\\Downloads\\${this.fileName}${this.number}.${this.ext}`;
        this.number += 1;
      }
      const workbook = new Workbook();
      workbook.creator = 'sejong-wos';
      workbook.created = new Date();
      // console.log(workbook);
      workbook.views = [
        {
          x: 0,
          y: 0,
          width: 10000,
          height: 20000,
          firstSheet: 0,
          activeTab: 1,
          visibility: 'visible',
        },
      ];

      const sheet = workbook.addWorksheet('result', {
        properties: {
          tabColor: { argb: 'FFC0000' },
          outlineLevelCol: 1,
        },
        pageSetup: {
          fitToPage: true,
          fitToHeight: 5,
          fitToWidth: 7,
        },
      });

      sheet.pageSetup.margins = {
        left: 0.7,
        right: 0.7,
        top: 0.75,
        bottom: 0.75,
        header: 0.3,
        footer: 0.3,
      };
      const intNY = parseInt((new Date()).getFullYear(), 10);
      sheet.columns = [
        { header: '제목', key: 'title', width: 20 },
        { header: '교신저자', key: 'reprint', width: 10 },
        { header: '저자 목록', key: 'authorsJoined', width: 20 },
        { header: '발행분류', key: 'docType', width: 10 },
        { header: '발행년월', key: 'publishedMonth', width: 12 },
        { header: '등급', key: 'capedGradesJoined', width: 10 },
        { header: '백분율', key: 'goodRank', width: 7 },
        { header: 'IF', key: 'prevYearIF', width: 7 },
        { header: '피인용', key: 'timesCited', width: 7 },
        { header: '자인용/타인용', key: 'selfOrOthers', width: 15 },
        { header: '저널명', key: 'journal_name', width: 20 },
        { header: '발행처', key: 'publisherJoined', width: 20 },
        { header: 'ISSN', key: 'issn', width: 10 },
        { header: '권', key: 'volume', width: 5 },
        { header: '호', key: 'issue', width: 5 },
        { header: '페이지', key: 'pages', width: 10 },
        { header: '언어', key: 'language', width: 8 },

        { header: `${intNY - 9}`, key: `${intNY - 9}`, width: 5 },
        { header: `${intNY - 8}`, key: `${intNY - 8}`, width: 5 },
        { header: `${intNY - 7}`, key: `${intNY - 7}`, width: 5 },
        { header: `${intNY - 6}`, key: `${intNY - 6}`, width: 5 },
        { header: `${intNY - 5}`, key: `${intNY - 5}`, width: 5 },
        { header: `${intNY - 4}`, key: `${intNY - 4}`, width: 5 },
        { header: `${intNY - 3}`, key: `${intNY - 3}`, width: 5 },
        { header: `${intNY - 2}`, key: `${intNY - 2}`, width: 5 },
        { header: `${intNY - 1}`, key: `${intNY - 1}`, width: 5 },
        { header: `${intNY - 0}`, key: `${intNY - 0}`, width: 5 },
      ];
      const targetList = this.inteList.slice(0);
      targetList.forEach((row) => {
        row.shift();
      });
      sheet.addRows(targetList);
      workbook.xlsx.writeFile(targetPath).then(() => {
        this.recentFilePath = targetPath;
        this.saveLoading = false;
      }).catch((err) => {
        this.excelErrShow = true;
        this.excelErrMSG = err;
        this.saveLoading = false;
      });
    },
    deleteRow(item) {
      if (confirm('정말 행을 삭제합니까?')) {
        this.inteList.splice(this.inteList.indexOf(item), 1);
      }
    },
    integrateAll() {
      this.inteList = [];
      if (this.file === null) {
        this.showAlert('파일을 선택하지 않았습니다.');
        return;
      }
      const promises = [];
      for (let ii = 0; ii < this.file.length; ii += 1) {
        const tempWb = new Workbook();
        promises.push(tempWb.xlsx.readFile(this.file[ii].path));
      }
      Promise.all(promises).then((values) => {
        values.forEach((wb) => {
          if (wb) {
            const x = wb.getWorksheet().getSheetValues();
            x.forEach((row) => {
              if (row && row[1] !== '제목' && row[2] !== '교신저자') {
                row[0] = this.count;
                this.count += 1;
                this.inteList.push(row);
              }
            });
          }
        });
      });
    },
  },
};
</script>

<style scoped>
</style>