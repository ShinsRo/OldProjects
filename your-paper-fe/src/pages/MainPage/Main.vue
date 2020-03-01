<template>
    <div >
      <headerComponent id="header"  :username="memberInfoDto"></headerComponent>
      <div class="contentOuter" id="searchMyPaperWrap" v-if="componentFlag===1">
        <search-my-paper-layout id="searchMyPaper" :page="page" ></search-my-paper-layout>
        <!-- <paperStatics></paperStatics> 내 논문 통계-->
      </div>
      <div class="contentOuter" v-if="componentFlag !== 1">
        <paperStaticsLayout id="paperStatics" v-if="componentFlag === 2"></paperStaticsLayout>
        <!--논문 통계-->
        <paperEdit id="paperEdit" v-if="componentFlag===3"></paperEdit>
        <!-- 내 논문 편집 -->
      </div>
      <!--<my-list id="myListOuterLayout"></my-list>-->
    </div>
</template>

<script>
import headerComponent from '../../components/1.header/headerComponent.vue'
import searchMyPaperLayout from '../../components/2.searchMyPaper/searchMyPaperLayout.vue'
import paperEdit from '../../components/4.paperEdit/paperEditLayout.vue'
import paperStaticsLayout from '../../components/6.paperStatics/paperStaticsLayout.vue'

export default {
  name: 'Main',
  components: {
    'headerComponent': headerComponent,
    'searchMyPaperLayout': searchMyPaperLayout,
    paperEdit,
    'paperStaticsLayout': paperStaticsLayout
  },
  data () {
    return {
      componentFlag: 1,
      token: '',
      session: {},
      memberInfoDto: {},
      page: 1
    }
  },
  mounted(){
    this.$store.dispatch('WOS_OBJECT_SET_ACTION')

    const contentOuter = document.querySelector('#searchMyPaperWrap');
    contentOuter.addEventListener('scroll', e => {
      if(contentOuter.scrollTop + contentOuter.clientHeight >= contentOuter.scrollHeight) {
        this.page+=1
      }
    }); // 무한스크롤 event listener
  },
  computed: {
    changePageFlag(){
      return this.$store.getters.PAGE_FLAG_GETTER
    } // component 변경 event 발생 감시
  },
  watch: {
    changePageFlag(){
      this.componentFlag = this.$store.getters.PAGE_FLAG_GETTER
    } //  event 발생 시 flag 변환
  }

}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
