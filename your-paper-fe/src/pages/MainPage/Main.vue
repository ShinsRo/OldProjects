<template>
    <div>
      <headerComponent id="header"></headerComponent>
      <!-- <myList></myList> 내 노문 검색-->
      <!-- <paperStatics></paperStatics> 내 논문 통계-->
      <!-- <paperEdit></paperEdit> 내 논문 편집 -->
      <!--<my-list id="myListOuterLayout"></my-list>-->
    </div>
</template>

<script>
//import myList from '../components/Main/contents/MyList/MyListLayout'
import headerComponent from '../../components/1.header/headerComponent.vue'
export default {
  name: 'Main',
  components: {
    //'my-list': myList
    'headerComponent': headerComponent
  }
}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
