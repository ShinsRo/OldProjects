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
    this.$store.dispatch('MEMBER_PAPER_ACTION',[3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])
      .then(res =>{
        this.loading = 1;
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
    isLoading(newVal, oldVal){
      let loadData = this.$store.getters.MEMBER_PAPER_GETTER
      for(let i = 0; i < loadData.length; i++){
        this.paperData.push(loadData[i])
      }
      console.log('watch',this.paperData);
    },
    loadNewPage(){
      this.$store.dispatch('MEMBER_PAPER_PAGING_ACTION', { payload: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18], page: this.page})
    }
  }
}
</script>

<style lang="scss">
  @import './searchMyPaperLayout.scss';

</style>
