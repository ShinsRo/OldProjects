<template>
  <div id="searchMyPaperLayout">
    <div id="titleLayout">
      <p class="text">
        Search My Paper
      </p>
    </div>
    <searchMyPaperWithOption id="searchMyPaperWithOption"></searchMyPaperWithOption>
    <!--필터 레이아웃-->
    <viewMyPaperComponent id="viewMyPaperComponent"></viewMyPaperComponent>
  </div> <!--MyPaper 전체 레이아웃-->
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
