<template>
  <div id="searchMyPaperLayout">
    <div id="titleLayout">
      <p class="text">
        Search My Paper
      </p>
    </div>
    <searchMyPaperWithOption id="searchMyPaperWithOption"></searchMyPaperWithOption>
    <!--필터 레이아웃-->
      <div id="mainListLayout">
        <!--<main-list></main-list>-->
      </div> <!--글목록 레이아웃-->
  </div> <!--MyPaper 전체 레이아웃-->
</template>

<script>
import searchMyPaperWithOption from './searchMyPaperWithOption/searchMyPaperWithOptionComponent.vue'

  export default {
    name: 'MyListLayout',
    components: {
      'searchMyPaperWithOption' : searchMyPaperWithOption

    }
  }
</script>

<style lang="scss">
  @import './searchMyPaperLayout.scss';

</style>
