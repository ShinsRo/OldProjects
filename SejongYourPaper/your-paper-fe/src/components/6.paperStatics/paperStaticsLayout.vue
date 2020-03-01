<template>
<div>
  <allStatics id="allStatics"></allStatics>
  <allPaperList id="allPaperList" :loading="loading" :paperData="paperData"></allPaperList>
  <div id="buttonWrap">
    <div class="button" > < </div>
    <div v-for="i in endPage-1" class="page" v-on:click="page = i">{{i}}</div>
    <div class="button"> > </div>
  </div>

</div>
</template>

<script>
import allPaperList from './allPaperList/allPaperListComponent.vue'
import allStatics from './allStatics/allStaticsComponent.vue'

export default {
  name: 'paperStaticsLayout.vue',
  components: {
    'allPaperList': allPaperList,
    'allStatics': allStatics
  },
  data(){
    return{
      page: 0,
      pageFlag: 1,
      reprint: 'REPRINT',
      endPage: 1,
      loading: 0,
      allPaperData: [],
      paperData: [],
      count: 10,
      criteria: [
        { field: this.$FIELD.TITLE, operation: this.$CRITERIA.LIKE, value: ' '},
        { field: this.$FIELD.AUTHOR_TYPE, operation: this.$CRITERIA.LIKE, value: 'REPRINT'},
        { field: this.$FIELD.AUTHOR_TYPE, operation: this.$CRITERIA.LIKE, value: 'GENERAL'},
      ],
      citedStack: 0,
      orderBy: this.$FIELD.YEAR,
      isComputed: 0
    }
  },
  mounted(){
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('SET_SEARCH_FLAG_ACTION')
    this.$store.dispatch('SET_END_PAGE_ACTION', {count: this.count, criteria: [this.criteria[0]]})
    //          0    1      2      3     4
    //         '행', 'UID', 'DOI', '제목', '링크',
    //          5        6          7      8
    //         '교신저자', '저자 상태', '저자', '인용수',
    //          9        10      11   12    13
    //         '발행년월', '저녈명', '권', '호', '페이지',
    //          14        15     16    17       18
    //         '월별피인용', '등급', 'IF', '백분율', '파싱 상태',
  },
  computed: {
    isReadyToLoading(){
      return  this.$store.getters.END_PAGE_GETTER
    },
    completeCompute(){
      return this.$store.getters.GRADE_GETTER
    },
    isLoading () {
      return this.$store.getters.MEMBER_PAPER_GETTER
    },
    loadPaper () {
      return this.allPaperData
    },
    paging () {
      return this.page
    }
  },
  watch: {
    isReadyToLoading () {
      if(this.isComputed === 0){
        this.endPage = this.$store.getters.END_PAGE_GETTER
        this.$store.dispatch('COMPUTE_MY_PAPER_INFO_ACTION', {count: this.count , criteria: [this.criteria[0]]})
      }
      else {
        this.endPage = this.$store.getters.END_PAGE_GETTER
        this.$store.dispatch('NEW_MEMBER_PAPER_ACTION', {count : this.count, orderBy: this.orderBy, criteria: [this.criteria[this.pageFlag]]})
      }
    },
    completeCompute(){
      this.$store.dispatch('SET_END_PAGE_ACTION', {count: this.count, criteria: [this.criteria[this.pageFlag]]})
      this.isComputed = 1
    },
    isLoading () {
      this.paperData = this.$store.getters.MEMBER_PAPER_GETTER
      this.countCited()
      this.loading = 1
      console.log("paper Data",this.paperData)
    },
    paging () {
      this.$store.dispatch('MEMBER_PAPER_PAGING_ACTION', this.page)
    }
  },
  methods: {
    countCited () {
      for (var i in this.paperData) {
        this.citedStack += this.paperData[i][5]
      }
      if (this.citedStack !== 0) {
        this.$store.dispatch('CITE_COUNT_ACTION', this.citedStack)
      }
    }
  }
}
</script>

<style lang="scss">
  @import './paperStaticsLayout.scss';
</style>
