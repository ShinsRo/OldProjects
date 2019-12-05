<template>
<div>
  <allStatics id="allStatics"></allStatics>
  <allPaperList id="allPaperList"></allPaperList>
  <div id="buttonWrap">
    <div class="button"> < </div>
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
      page: 1
    }
  },
  mounted(){
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('MEMBER_PAPER_ACTION',  [3, 4, 5, 6, 7, 8, 9, 13, 10, 15, 17, 18, 1, 2])
    //          0    1      2      3     4
    //         '행', 'UID', 'DOI', '제목', '링크',
    //          5        6          7      8
    //         '교신저자', '저자 상태', '저자', '인용수',
    //          9        10      11   12    13
    //         '발행년월', '저녈명', '권', '호', '페이지',
    //          14        15     16    17       18
    //         '월별피인용', '등급', 'IF', '백분율', '파싱 상태',
  }
}
</script>

<style lang="scss">
  @import './paperStaticsLayout.scss';

</style>
