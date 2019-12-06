<template>
<div>
  <allStatics id="allStatics"></allStatics>
  <allPaperList id="allPaperList" :loading="loading" :paperData="paperData"></allPaperList>
  <div id="buttonWrap">
    <div class="button" > < </div>
    <div v-for="i in endPage-1" class="page" v-on:click="page = i-1">{{i}}</div>
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
      reprint: 'REPRINT',
      endPage: 1,
      loading: 0,
      allPaperData: [],
      paperData: [],
    }
  },
  mounted(){
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('SET_END_PAGE_ACTION', this.reprint)
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
    isLoading(){
      return this.$store.getters.MEMBER_PAPER_GETTER
    },
    loadPaper(){
      return this.allPaperData
    },
    paging(){
      return this.page
    }
  },
  watch: {
    isReadyToLoading(){
      this.endPage = this.$store.getters.END_PAGE_GETTER
      this.$store.dispatch('ALL_PAPER_ACTION', {payload : [3, 4, 5, 6, 7, 8, 9, 13, 10, 15, 17, 18, 1, 2, 0],  value : 'REPRINT'})
    },
    isLoading() {
        this.allPaperData = this.$store.getters.MEMBER_PAPER_GETTER
    },
    loadPaper(){
      if(this.allPaperData === []){
        this.loading = -1
      }
      else{
        this.paperData = this.allPaperData[this.page]
        this.loading = 1
        console.log(this.paperData)
      }
    },
    paging(){
      this.paperData = this.allPaperData[this.page]
    }

  }
}
</script>

<style lang="scss">
  @import './paperStaticsLayout.scss';
</style>
