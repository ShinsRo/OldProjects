<template>
  <v-layout row wrap>
    해당 기능은 아직 이슈를 해결 중입니다.
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';

export default {
  props: ['resList', 'loading', 'executer', 'log'],
  data() {
    return {
      query: '',
      startYear: '',
      endYear: '',
      listSearch: '',
      resHeaders: [
        {
          text: '제목',
          align: 'left',
          sortable: true,
          value: 'Title',
          width: '20px',
        },
        { text: '저자 목록', align: 'left', value: 'Authors', width: '30px' },
        { text: '발행년월', align: 'left', value: 'Publication Date', width: '5px' },
        { text: '발행처', align: 'left', value: 'Source Title', width: '20px' },
        { text: '피인용', align: 'left', value: 'Total Citations', width: '5px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: 'DOI', align: 'left', value: 'DOI', width: '5px' },
      ],
    };
  },
  components: {
    PulseLoader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    stdin() {
      console.log('stdin func');
      const payload = {
        scope: this,
        inputs: ['singleCitationSearch', this.query, this.startYear, this.endYear],
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