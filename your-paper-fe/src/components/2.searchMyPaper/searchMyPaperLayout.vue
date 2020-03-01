<template>
  <div id="searchMyPaperLayout">
    <div id="titleLayout">
      <p class="text">
        Search My Paper
      </p>
    </div> <!--페이지 설명 제목-->
    <searchMyPaperWithOption id="searchMyPaperWithOption"></searchMyPaperWithOption>
    <viewMyPaperComponent id="viewMyPaperComponent" :paperData="paperData"></viewMyPaperComponent>
  </div> <!--searchMyPaper 전체 레이아웃-->
</template>

<script>
import searchMyPaperWithOption from './searchMyPaperWithOption/searchMyPaperWithOptionComponent.vue'
import viewMyPaperComponent from './viewMyPaper/viewMyPaperComponent.vue'
export default {
  name: 'MyListLayout',
  components: {
    'searchMyPaperWithOption': searchMyPaperWithOption,
    'viewMyPaperComponent': viewMyPaperComponent
  },
  props: ['page'],
  data(){
    return{
      paperData: [],
      count: 10,
      orderBy: this.$FIELD.YEAR,
      value: ' '
    }
  },
  beforeCreate () {
    const isToken = sessionStorage.getItem('token')
    if (isToken === null) {
      this.$router.push('./')
    }
  },
  mounted(){
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('NEW_MEMBER_PAPER_ACTION', {
      count: this.count,
      orderBy: this.orderBy,
      criteria: [{'field': this.$FIELD.TITLE, 'operation': this.$CRITERIA.LIKE, 'value': ' '}]
    })
  },
  methods: {

  },
  computed:{
    isLoading(){
      return this.$store.getters.MEMBER_PAPER_GETTER
    },
    loadNewPage(){
      return this.page
    },
  },
  watch:{
    isLoading(){
      let loadData = this.$store.getters.MEMBER_PAPER_GETTER

      if(this.$store.getters.SEARCH_FLAG_GETTER === 1){
        this.paperData = []
        this.$store.dispatch('SET_SEARCH_FLAG_ACTION')
      }

      for(let i = 0; i < loadData.length; i++){
        this.paperData.push(loadData[i])
      }
    },
    loadNewPage(){
      this.$store.dispatch('MEMBER_PAPER_PAGING_ACTION', this.page)
    }
  }
}
</script>

<style lang="scss">
  @import './searchMyPaperLayout.scss';

</style>
