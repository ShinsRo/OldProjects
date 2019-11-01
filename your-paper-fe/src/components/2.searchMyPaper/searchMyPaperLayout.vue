<template>
  <div id="searchMyPaperLayout">
    <div id="titleLayout">
      <p class="text">
        Search My Paper
      </p>
    </div> <!--페이지 설명 제목-->
    <searchMyPaperWithOption id="searchMyPaperWithOption"></searchMyPaperWithOption>
    <viewMyPaperComponent id="viewMyPaperComponent"></viewMyPaperComponent>
  </div> <!--searchMyPaper 전체 레이아웃-->
</template>

<script>
import searchMyPaperWithOption from './searchMyPaperWithOption/searchMyPaperWithOptionComponent.vue'
import viewMyPaperComponent from './viewMyPaper/viewMyPaperComponent.vue'
export default {
  name: 'MyListLayout',
  components: {
    'searchMyPaperWithOption': searchMyPaperWithOption,
    'viewMyPaperComponent': viewMyPaperComponent

  }
}
</script>

<style lang="scss">
  @import './searchMyPaperLayout.scss';

</style>
