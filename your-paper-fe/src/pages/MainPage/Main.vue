<template>
    <div>
      <headerComponent id="header"></headerComponent>
      <div id="contentOuter">
        <search-my-paper-layout id="searchMyPaper"></search-my-paper-layout>
        <!-- <paperStatics></paperStatics> 내 논문 통계-->
        <!-- <paperEdit></paperEdit> 내 논문 편집 -->
      </div>

      <!--<my-list id="myListOuterLayout"></my-list>-->
    </div>
</template>

<script>
import headerComponent from '../../components/1.header/headerComponent.vue'
import searchMyPaperLayout from '../../components/2.searchMyPaper/searchMyPaperLayout.vue'

export default {
  name: 'Main',
  components: {
    'headerComponent': headerComponent,
    'searchMyPaperLayout': searchMyPaperLayout
  }
}

</script>

<style lang="scss">
  @import './Main.scss';

</style>
