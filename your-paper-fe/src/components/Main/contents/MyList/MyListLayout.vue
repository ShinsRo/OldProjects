<template>
  <div>
    <header-layout id="headerLayoutRoute"></header-layout>
    <div id="mainContentLayout">
      <div id="mainFilterLayout">
        <main-option></main-option>
      </div> <!--필터 레이아웃-->
      <div id="mainListLayout">
        <main-list></main-list>
      </div> <!--글목록 레이아웃-->
    </div>
  </div>
</template>

<script>
import headerLayout from '../../header/HeaderLayout'
import mainOption from './MainOption'
import mainList from './MainList'

  export default {
  name: 'MyListLayout',
  components: {
    'header-layout': headerLayout,
    'main-option': mainOption,
    'main-list': mainList,
  }
}
</script>

<style lang="scss">
@import './MyListLayout.scss';

</style>
