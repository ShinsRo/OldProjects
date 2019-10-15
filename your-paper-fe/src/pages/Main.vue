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

<style scoped>
  #mainOuterLayout{
    justify-items: center;
    position: absolute;
    overflow-y: hidden;
    height: 100vh;
    background-color: rgb(242,242,242);
  }
  #headerLayoutRoute{
    display: inline-flex;
    justify-content: space-between;
    background-color : rgb(46,53,78);
    height: 60px;
    width: 100vw;
  }

  #mainContentLayout{
    width: 80%;
    height: calc(100vh - 60px);
    margin-left: 10%;
  }

</style>
