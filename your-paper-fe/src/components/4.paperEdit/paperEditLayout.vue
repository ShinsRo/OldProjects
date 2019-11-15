<template>
  <div class="paperEditWrapper">
    <div class="componentTitleContainer">
      <p class="title">
        WOS에서 검색하여 내 논문으로 추가하기
      </p>
    </div>
    <search-all-paper class="searchOnWOSComponent"></search-all-paper>
    <paperList></paperList>
    <!-- <button class="saveButton" type="button">Save</button> -->
  </div>
</template>

<script>
import searchAllPaper from './searchAllPaper/searchAllPaperComponent.vue'
import paperList from './paperList/paperListTable.vue'

export default {
  name: 'paperEditLayout',
  components: {
    searchAllPaper,
    paperList
  }
}
</script>

<style lang="scss">
  @import './paperEditLayout.scss';
</style>
