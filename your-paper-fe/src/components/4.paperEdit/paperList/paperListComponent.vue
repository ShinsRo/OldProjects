<template>
  <div class="paperListWrapper">
    <div class="paperColumnContainer">
      <div class="paperTitleContainer column">
        <p class="paperTitleColumn">제목</p>
      </div>
      <div class="paperAuthorContainer column" v-if="flag===1">
        <p class="paperAuthorColumn">저자</p>
      </div>
      <div class="paperAuthorStateContainer column" v-if="flag===2"></div>
      <div class="paperDOIContainer column">
        <p class="paperDOIColumn">DOI</p>
      </div>
      <div class="paperURLContainer column">
        <p class="paperURLColumn">URL</p>
      </div>
    </div>
    <div class="paperListContainer">

      <div class="paperList">
        <div class="paperTitle">
          <p class="showSomeTitle">ananananananananananananananananan</p>
          <p class="showAllTitle">amamamamamananananananaanananananan</p>
        </div>
        <div class="paperAuthor">
          <p class="showSomeAuthor">sdfsdddddddddddddddddddddddddddddddddddddd  </p>
          <p class="showAllAuthor">sdfffffffffffffffffffffffffffffffddd</p>
        </div>
        <div class="paperDOI">
          <p class="showSomeDOI">1231233333333333333333333333333333333333</p>
          <p class="showAllDOI">2222222222222222222222222222222222222222</p>
        </div>
        <div class="paperURL">
          <p class="showSomeURL">sddddddddddddddddddddddddddddssdfsfs</p>
          <p class="showAllURL">sdfffffffffffffffffffffffffffffffffffffff</p>
        </div>
        <div class="paperButton">
          <button class="addButton" type="button" v-on:click="test">Add</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'paperList',
  props: ['EditFlag'],
  methods: {
    test () {
      const myPapers = this.$store.getters.memberPaperGetter
      console.log(myPapers)
      document.getElementsByClassName('paperListContainer').item(0).innerHTML = `${myPapers}`
    }
  },
  data () {
    return {
      flag: this.EditFlag
    }
  }
}
</script>

<style lang="scss">
  @import './paperListComponent.scss';
</style>
