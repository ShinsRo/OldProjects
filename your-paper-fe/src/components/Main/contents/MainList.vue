<template>
  <div id="mainList">
    <paper-component class="paperComponentLayout"></paper-component>
    <paper-component class="paperComponentLayout"></paper-component>
    <paper-component class="paperComponentLayout"></paper-component>
  </div>
</template>

<script>
import paperComponent from './PaperComponent'
export default {
  name: 'MainList',
  components: {
    'paper-component': paperComponent
  }
}
</script>

<style lang="scss">
  @import './MainList.scss';

</style>
