<template>
    <div>
      <headerComponent id="header" v-on:changeFlag="changeComponent"></headerComponent>
      <div id="contentOuter">
        <search-my-paper-layout id="searchMyPaper" v-if="flag===1"></search-my-paper-layout>
        <!-- <paperStatics></paperStatics> 내 논문 통계-->
        <paperEdit id="paperEdit" v-if="flag===2"></paperEdit>
        <!-- 내 논문 편집 -->
      </div>

      <!--<my-list id="myListOuterLayout"></my-list>-->
    </div>
</template>

<script>
import headerComponent from '../../components/1.header/headerComponent.vue'
import searchMyPaperLayout from '../../components/2.searchMyPaper/searchMyPaperLayout.vue'
import paperEdit from '../../components/4.paperEdit/paperEditLayout.vue'

export default {
  name: 'Main',
  components: {
    'headerComponent': headerComponent,
    'searchMyPaperLayout': searchMyPaperLayout,
    paperEdit
  },
  data () {
    return {
      flag: 2
    }
  },
  methods: {
    changeComponent (val) {
      this.flag = val
    }
  }
}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
