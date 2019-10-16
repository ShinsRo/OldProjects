<template>
    <div id="mainOuterLayout">
      <header-layout id="headerLayoutRoute"></header-layout>
      <div id="mainContentLayout">
        <main-option></main-option>
      </div>

    </div>
</template>

<script>
  import headerLayout from '../components/Main/header/HeaderLayout'
  import mainOption from '../components/Main/contents/MainOption'
  import '../components/BasicStyle/BasicColor.scss'

  export default {
  name: "Main",
  components : {
    'header-layout': headerLayout,
    'main-option' : mainOption
  }
}
</script>

<style lang="scss">
  @import './Main.scss';


</style>
