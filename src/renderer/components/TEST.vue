<template>
  <v-layout row wrap>
    TEST
    <v-btn
    @click="killTest"
    >
      test
    </v-btn>
        <v-btn
    @click="makeResToXLSX('test1111')"
    >
      makeResToELSX
    </v-btn>
  </v-layout>
</template>

<script>
import { Workbook } from 'exceljs';

export default {
  props: ['loading', 'errQuery', 'resList', 'executer'],
  data() {
    return {
      gubun: 0,
    };
  },
  components: {
  },
  methods: {
    makeResToXLSX(test) {
      const workbook = new Workbook();
      workbook.creator = 'sejong-wos';
      workbook.created = new Date();
      console.log(workbook);
      alert(test);
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
      console.log(sheet);

      sheet.pageSetup.margins = {
        left: 0.7,
        right: 0.7,
        top: 0.75,
        bottom: 0.75,
        header: 0.3,
        footer: 0.3,
      };
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
      ];
      sheet.getColumn(4).outlineLevel = 1;

      const rows = this.resList.slice();
      console.log(rows);
      for (let ii = 0; ii < rows.length; ii += 1) {
        rows[ii].selfOrOthers = `${rows[ii].citingArticles.selfCitation}/${rows[ii].citingArticles.othersCitation}`;
        rows[ii].authorsJoined = rows[ii].authors.join(' ');
        rows[ii].publisherJoined = rows[ii].publisher.join(' ');
        rows[ii].capedGradesJoined = rows[ii].capedGrades.join(' ');
      }
      sheet.addRows(rows);
      workbook.xlsx.writeFile('C:/Users/F/Desktop/sejong-wos_Setup1.03/test.xlsx').then(() => {
        alert('test');
      }).catch((err) => {
        alert(err);
      });
    },
    killTest() {
      if (this.executer !== '') {
        console.log(`killTest${this.executer}`);
        console.log(this.executer);
        this.executer.stdin.pause();
        console.log(this.executer.kill());
        // this.executer.kill();
      }
    },
  },
};
</script>

<style scoped>
</style>