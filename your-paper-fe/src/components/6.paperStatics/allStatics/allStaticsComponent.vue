<template>
  <div id="allStaticsComponent">
    <timesCitedComponent class="eachStaticComponent"></timesCitedComponent>
    <authorTypeComponent class="eachStaticComponent"></authorTypeComponent>
    <paperRatingCompnent class="eachStaticComponent"></paperRatingCompnent>
  </div>
</template>

<script>
import timesCitedComponent from './eachStatic/timesCitedComponent.vue'
import authorTypeComponent from './eachStatic/authorRatingComponent.vue'
import paperRatingCompnent from './eachStatic/paperRatingComponent.vue'

export default {
  name: 'allStaticsComponent',
  components: {
    timesCitedComponent,
    authorTypeComponent,
    paperRatingCompnent
  }
}
</script>

<style scoped>
  @import './allStaticsComponent.scss';
</style>
