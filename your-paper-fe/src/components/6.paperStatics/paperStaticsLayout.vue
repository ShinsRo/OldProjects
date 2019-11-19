<template>
<div>
  <allStatics id="allStatics"></allStatics>
  <allPaperList id="allPaperList"></allPaperList>
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
  }
}
</script>

<style scoped>
  @import './paperStaticsLayout.scss';

</style>
