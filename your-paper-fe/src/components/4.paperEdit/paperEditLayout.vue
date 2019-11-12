<template>
  <div class="paperEditWrapper">
    <div class="componentTitleContainer">
      <p class="title">
        WOS에서 검색하여 내 논문으로 추가하기
      </p>
    </div>
    <search-all-paper class="searchAllPaperComponent"></search-all-paper>
    <paper-list class="allPaperList" v-bind:EditFlag="1"></paper-list>
    <!-- <paper-list class="myPaperList" v-bind:EditFlag="2"></paper-list> -->
    <!-- <button class="saveButton" type="button">Save</button> -->
  </div>
</template>

<script>
import searchAllPaper from './searchAllPaper/searchAllPaperComponent.vue'
import paperList from './paperList/paperListComponent.vue'

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
