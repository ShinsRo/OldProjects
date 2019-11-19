<template>
<div id="allStaticsComponent">
  <eachStaticComponent class="eachStaticComponent"></eachStaticComponent>
  <eachStaticComponent class="eachStaticComponent"></eachStaticComponent>
  <eachStaticComponent class="eachStaticComponent"></eachStaticComponent>
  <eachStaticComponent class="eachStaticComponent"></eachStaticComponent>
</div>
</template>

<script>
import eachStaticComponent from './eachStatic/eachStaticComponent.vue'
export default {
  name: 'allStaticsComponent',
  components: {
    'eachStaticComponent': eachStaticComponent
  }
}
</script>

<style scoped>
  @import './allStaticsComponent.scss';

</style>
