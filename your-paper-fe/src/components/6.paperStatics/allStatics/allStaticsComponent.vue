<template>
  <div id="allStaticsComponent">
    <timesCitedComponent class="eachStaticComponent"></timesCitedComponent>
    <authorTypeComponent class="eachStaticComponent"></authorTypeComponent>
    <timesCitedComponent class="eachStaticComponent"></timesCitedComponent>
  </div>
</template>

<script>
import timesCitedComponent from './eachStatic/timesCitedComponent.vue'
import authorTypeComponent from './eachStatic/authorRatingComponent.vue'

export default {
  name: 'allStaticsComponent',
  components: {
    timesCitedComponent,
    authorTypeComponent
  }
}
</script>

<style scoped>
  @import './allStaticsComponent.scss';

</style>
