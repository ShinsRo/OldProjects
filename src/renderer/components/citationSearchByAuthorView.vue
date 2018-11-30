<template>
  <v-layout row wrap>
    아직 구현 중입니다.
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';
import FileSelect from './citationSearchMultiView/FileSelect.vue';
import ExcelDownloader from './ExcelDownloader/ExcelDownloader';

export default {
  props: ['mErrQuery', 'mResList', 'loading', 'executer', 'log'],
  data() {
    return {
      errObj: {
        show: false,
        code: 200,
        msg: '입력 값이 잘못되었습니다',
      },
      startYear: '2010',
      endYear: '2018',
      gubun: '논문제목',
      gubuns: ['논문제목', 'DOI'],
      gubunsId: {
        논문제목: 'TI',
        저자명: 'AU',
        연구기관: 'AD',
        DOI: 'DO',
      },
      file: null,
      inputFilePath: 'C:\\input.csv',
      listSearch: '',
      pagination: {
        sortBy: 'index',
        descending: true,
      },
      resHeaders: [
        { text: '행삭제', align: 'left', value: '__icon', width: '5px', sortable: false },
        { text: '번호', align: 'left', value: 'index', width: '5px' },
        { text: '제목', align: 'left', value: 'title', width: '20px' },
        { text: '교신저자', align: 'left', value: 'reprint', width: '30px' },
        { text: '저자 목록', align: 'left', value: 'authors', width: '30px' },
        { text: '발행분류', align: 'left', value: 'docType', width: '10px' },
        { text: '발행년월', align: 'left', value: 'publishedMonth', width: '5px' },
        { text: '등급', align: 'left', value: 'capedGrades', width: '20px' },
        { text: '백분율', align: 'left', value: 'goodRank', width: '5px' },
        { text: 'IF', align: 'left', value: 'prevYearIF', width: '5px' },
        { text: '피인용', align: 'left', value: 'timesCited', width: '5px' },
        { text: '발행처', align: 'left', value: 'publisher', width: '20px' },
        { text: 'ISSN', align: 'left', value: 'issn', width: '5px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: '언어', align: 'left', value: 'language', width: '10px' },
      ],
      errQueryHeaders: [
        { text: '검색어', align: 'left', value: 'query', width: '20px' },
        { text: '메세지', align: 'left', value: 'msg', width: '20px' },
      ],
    };
  },
  components: {
    FileSelect,
    PulseLoader,
    ExcelDownloader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    deleteRow(index) {
      if (confirm('정말 행을 삭제합니까?')) {
        this.mResList.splice(index, 1);
      }
    },
    stdin() {
      // 인풋값 체크
      const errObj = this.errObj;
      try {
        this.startYear.trim();
        const intSY = parseInt(this.startYear, 10);
        const intEY = parseInt(this.endYear, 10);
        const intNY = parseInt((new Date()).getFullYear(), 10);
        if (!this.startYear || this.startYear.length !== 4) {
          errObj.code = 100;
          errObj.msg = '시작년도를 확인해주세요.';
        } else if (!this.endYear || this.endYear.length !== 4) {
          errObj.code = 101;
          errObj.msg = '끝 년도를 확인해주세요.';
        } else if (
          !(intSY >= 1900 && intSY <= intNY) ||
          !(intEY >= 1900 && intEY <= intNY)
        ) {
          errObj.code = 102;
          errObj.msg = '년도는 1900년에서 금년 + 1 사이여야 합니다.';
        } else if (intSY > intEY) {
          errObj.code = 103;
          errObj.msg = '시작년도가 끝 년도보다 최근일 수 없습니다.';
        } else {
          this.inputFilePath = this.file.path;
          errObj.code = false;
        }
        if (errObj.code) { throw errObj; }
      } catch (e) {
        errObj.show = true;
        return;
      }
      errObj.show = false;
      const payload = {
        scope: this,
        inputs: ['multiCitationSearch', this.startYear, this.endYear, this.gubunsId[this.gubun], this.inputFilePath],
      };
      this.$emit('stdin', payload);
    },
  },
};

</script>

<style scoped>
  img {
    margin-left: auto;
    margin-right: auto;
    display: block;
  }
</style>