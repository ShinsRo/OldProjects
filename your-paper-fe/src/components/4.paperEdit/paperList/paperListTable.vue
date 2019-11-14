<template>
  <div class="tableWrapper">
    <table class="searchOnWOS" border="3">
      <tr class="tableColumn">
        <th class="titleColumn">제목</th>
        <th class="authorColumn">저자</th>
        <th class="dateColumn">날짜</th>
        <th class="URLColumn">URL</th>
        <th class="buttonColumn"></th>
      </tr>
      <tr class="tableResult">
        <td class="titleResult">an</td>
        <td class="authorResult">승녕</td>
        <td class="dateResult">NOB</td>
        <td class="URLResult">www.siotman.com</td>
        <td class="buttonResult"><button class="paperAddButton" type="button">ADD</button></td>
      </tr>
      <tr class="hoveringResult" v-if="flag===2">
        <td class="hoverRender" colspan="5">호버링존</td>
      </tr>
    </table>
    <!------------------------------------  -->
    <table class="searchOnMyPaper" border="3">
      <tr class="tableColumn">
        <th class="titleColumn">제목</th>
        <th class="authorColumn">저자</th>
        <th class="dateColumn">날짜</th>
        <th class="URLColumn">URL</th>
        <th class="buttonColumn"></th>
      </tr>
      <tbody>
        <tr class="tableResult" v-for="(object, index) in this.myPapers" :key="index">
          <td class="titleResult">{{ object.paper.title }}</td>
          <td class="authorResult">{{ object.paper.authorListJson[0]}}</td>
          <td class="dateResult">{{ setDate(object.sourceInfoDto.paper.publishedYear,object.sourceInfoDto.paper.publishedDate) }}</td>
          <td class="URLResult"> {{ object.paper.paperUrlsDto.sourceUrl }} </td>
          <td class="buttonResult"><button class="paperAddButton" type="button">Remove</button></td>
        </tr>
        <tr>sdfsdfsdf</tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'paperList',
  mounted () {
    this.myPapers = this.$store.getters.memberPaperGetter
    console.log(this.myPapers)
    console.log('-------')
    console.log(this.myPapers[0].paper)
  },
  data () {
    return {
      isShow: false,
      flag: 2,
      myPapers: '',
      AllData: ''
    }
  },
  methods: {
    setDate (year, date) {
      return `${year || ''} ${date || ''}`
    }
  }
}
</script>

<style lang="scss">
  @import './paperListTable.scss';
</style>
