<template>
  <v-layout row wrap>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-btn slot="activator" dark>엑셀로 다운로드하기</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">엑셀로 저장하기</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md4>
                <!-- <v-text-field label="저장할 파일 이름" required></v-text-field> -->
                저장하시겠습니까?
              </v-flex>
            </v-layout>
          </v-container>
          <small>최근 저장 : {{ recentFilePath }}</small>
          <v-alert
          :value="excelErrMSG"
          type="error"
          >
            {{ excelErrMSG }}
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="yellow darken-1" flat @click="dialog = false">Close</v-btn>
          <v-btn color="yellow darken-1" flat @click="makeResToXLSX">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
  </v-layout>
</template>

<script>
import { Workbook } from 'exceljs';
import { homedir } from 'os';

export default {
  props: ['loading', 'errQuery', 'resList', 'executer', 'gubun'],
  data() {
    return {
      fileName: 'sejong_wos_output',
      number: 0,
      ext: 'xlsx',
      dialog: false,
      recentFilePath: '없음',
      excelErrShow: false,
      excelErrMSG: '',
      saveLoading: false,
    };
  },
  components: {
  },
  methods: {
    makeResToXLSX() {
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

      const sheet = workbook.addWorksheet('Sheet Test', {
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
      sheet.getColumn(4).outlineLevel = 1;

      const rows = this.resList.slice();
      for (let ii = 0; ii < rows.length; ii += 1) {
        rows[ii].selfOrOthers = `${rows[ii].citingArticles.selfCitation || 0}/`;
        rows[ii].selfOrOthers += `${rows[ii].citingArticles.othersCitation || 0}`;
        rows[ii].authorsJoined = rows[ii].authors.join(' ');
        rows[ii].publisherJoined = rows[ii].publisher.join(' ');
        rows[ii].capedGradesJoined = rows[ii].capedGrades.join(' ');
        if ('tc_data' in rows[ii]) {
          for (let jj = 1; jj <= 10; jj += 1) {
            rows[ii][`${(intNY - 10) + jj}`] = rows[ii].tc_data[(intNY - 10) + jj];
          }
        }
      }
      sheet.addRows(rows);
      workbook.xlsx.writeFile(targetPath).then(() => {
        this.recentFilePath = targetPath;
        this.saveLoading = false;
      }).catch((err) => {
        this.excelErrShow = true;
        this.excelErrMSG = err;
        this.saveLoading = false;
      });
    },
  },
};
</script>

<style scoped>
</style>