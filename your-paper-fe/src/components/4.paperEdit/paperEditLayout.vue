<template>
  <div class="paperEditWrapper">
    <div class="componentTitleContainer">
      <p class="title">
        WOS에서 검색하여 내 논문으로 추가하기
      </p>
    </div>
    <search-all-paper class="searchOnWOSComponent"></search-all-paper>
    <paperListOnWOS></paperListOnWOS>
    <paperListOnMyPaper></paperListOnMyPaper>
  </div>
</template>

<script>
import searchAllPaper from './searchAllPaper/searchAllPaperComponent.vue'
import paperListOnMyPaper from './paperList/paperListOnMyPaper.vue'
import paperListOnWOS from './paperList/paperListOnWOS.vue'

export default {
  name: 'paperEditLayout',
  components: {
    searchAllPaper,
    paperListOnMyPaper,
    paperListOnWOS
  },
  mounted () {
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('MEMBER_PAPER_ACTION', [1, 3, 4, 6, 7, 9])
  }
}
</script>

<style lang="scss">
  @import './paperEditLayout.scss';
</style>
