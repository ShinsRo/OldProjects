<template>
    <div>

      <headerComponent id="header"></headerComponent>
      <!--<searchMyPaperLayout id="searchMyPaperLayout"></searchMyPaperLayout>-->
      <!-- <myList></myList> 내 노문 검색-->
      <!-- <paperStatics></paperStatics> 내 논문 통계-->
      <!-- <paperEdit></paperEdit> 내 논문 편집 -->
      <!--<my-list id="myListOuterLayout"></my-list>-->
    </div>
</template>

<script>
//import myList from '../components/Main/contents/MyList/MyListLayout'
import headerComponent from '../../components/1.header/headerComponent.vue'
import searchMyPaperLayout from '../../components/2.searchMyPaper/searchMyPaperLayout.vue'
export default {
  name: 'Main',
  components: {
      //'my-list': myList
      'headerComponent': headerComponent,
    'searchMyPaperLayout' : searchMyPaperLayout
  }
}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
