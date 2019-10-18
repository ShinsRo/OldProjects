<template>
  <div class="paperEdit">
    <searchComponent></searchComponent>
    <paperChangeComponent></paperChangeComponent>
  </div>
</template>

<script>
import searchComponent from '@/components/paperEdit/searchComponent.vue'
import paperChangeComponent from '@/components/paperEdit/paperChangeComponent.vue'

export default {
  name: 'paperEdit',
  components: {
    searchComponent,
    paperChangeComponent
  }
}
</script>

<style lang="scss">
  @import './paperEdit.scss';
</style>
