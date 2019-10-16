<template>
    <div id="mainOuterLayout">
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
import headerLayout from '../components/Main/header/HeaderLayout'
import mainOption from '../components/Main/contents/MainOption'
import mainList from '../components/Main/contents/MainList'
import '../components/BasicStyle/BasicColor.scss'

export default {
  name: 'Main',
  components: {
    'header-layout': headerLayout,
    'main-option': mainOption,
    'main-list': mainList
  }
}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
