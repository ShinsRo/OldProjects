<template>
  <div class="paperListWrapper">
    <div class="paperColumnContainer">
      <div class="paperTitleContainer column">
        <p class="paperTitleColumn">제목</p>
      </div>
      <div class="paperAuthorContainer column">
        <p class="paperAuthorColumn" v-if="flag===1">저자</p>
        <p class="paperAuthorColumn" v-if="flag===2">저자 상태</p>
      </div>
      <div class="paperDateContainer column">
        <p class="paperDateColumn">발행일</p>
      </div>
      <div class="paperURLContainer column">
        <p class="paperURLColumn">URL</p>
      </div>
    </div>
    <!-- --------------------------------------- -->
    <div class="paperListContainer" v-if="flag===1">
      <div class="resultSearch" v-if="searchFlag===1">
        <p class="notFound">검색 결과가 없습니다</p>
      </div>
      <!-- ------------------------------------------ -->
      <div class="paperList" v-if="searchFlag===2">
        <div class="paperTitle">
          <p class="showSomeTitle">abc</p>
          <p class="showAllTitle">abc</p>
        </div>
        <div class="paperAuthor">
          <p class="showSomeAuthor">def</p>
          <p class="showAllAuthor">def</p>
        </div>
        <div class="paperDate">
          <p class="showSomeDate">hgi</p>
        </div>
        <div class="paperURL">
          <p class="showSomeURL">kfg</p>
          <p class="showAllURL">kfg</p>
        </div>
        <div class="paperButton">
          <button class="addButton" type="button">Add</button>
        </div>
      </div>
      <!-- ----------------------------------------------- -->
    </div>
    <div class="paperListContainer" v-if="flag===2">
      <div class="resultSearch" v-if="myPapers===''">
        <p class="notFound">검색 결과가 없습니다</p>
      </div>
      <div class="paperList" v-for="(object,index) in this.myPapers" :key="index">
        <div class="paperTitle">
          <p class="showSomeTitle"> {{ object.title }} </p>
          <p class="showAllTitle"> {{ object.title }} </p>
        </div>
        <div class="paperAuthor">
          <p class="showSomeAuthor"> {{ object.authorList[0]}}</p>
          <p class="showAllAuthor"> {{ object.authorList.join(', ')}} </p>
        </div>
        <div class="paperDate">
          <p class="showSomeDate">
            {{ setDate(object.sourceInfoDto.publishedYear,object.sourceInfoDto.publishedDate) }} </p>
        </div>
        <div class="paperURL">
          <p class="showSomeURL"> {{ object.paperUrlsDto.sourceUrl }} </p>
          <p class="showAllURL"> {{ object.paperUrlsDto.sourceUrl }} </p>
        </div>
        <div class="paperButton">
          <button class="removeButton" type="button">Remove</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'paperList',
  props: ['EditFlag'],
  mounted () {
    this.myPapers = this.$store.getters.memberPaperGetter
  },
  data () {
    return {
      flag: this.EditFlag,
      searchFlag: 1,
      myPapers: ''
    }
  },
  methods: {
    setDate (year, date) {
      if (year === null && date === null) {
        return ''
      } else if (year === null && date !== null) {
        return date
      } else if (year !== null && date === null) {
        return year
      } else {
        return year + date
      }
    }
  }
}
</script>

<style lang="scss">
  @import './paperListComponent.scss';
</style>
