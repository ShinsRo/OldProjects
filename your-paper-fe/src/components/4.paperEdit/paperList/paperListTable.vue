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
      <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData[0] }} <br>
          {{ this.showData[2].join(', ') }} <br>
          {{ this.showData[3] }} <br>
          {{ this.showData[1] }}
        </td>
      </tr>
      <tbody>
        <tr class="tableResult" v-for="(object, index) in this.myPapers" :key="index" @mouseover="showAll(object)" @mouseleave="unShowAll(object)">
          <td class="titleResult">{{ object[0] }}</td>
          <td class="authorResult">{{ object[2][0] }}</td>
          <td class="dateResult">{{ object[3] }}</td>
          <td class="URLResult"> {{ object[1] }} </td>
          <td class="buttonResult"><button class="paperAddButton" type="button">Remove</button></td>
        </tr>
      </tbody>
    </table>
    <div class="pageChangeContainer">
      <button class="preButton" type="button" v-on:click="prePage()">◀</button>
      <button class="nextButton" type="button" v-on:click="nextPage()">▶</button>
    </div>
  </div>
</template>

<script>
// import { PaperRecordContainer } from '../../../../public/apis/api/paper-api.js'

export default {
  name: 'paperList',
  mounted () {
    const paperContainer = this.$store.getters.memberPaperGetter
    this.myPapers = paperContainer.getRecords([3, 4, 7, 9])
  },
  data () {
    return {
      nowPage: 0,
      showFlag: false,
      flag: 2,
      myPapers: '',
      AllData: '',
      showData: ''
    }
  },
  watch: {
    'nowPage': function () {
      const paperContainer = this.$store.getters.memberPaperGetter
      this.myPapers = paperContainer.getRecords([3, 4, 7, 9])
    }
  },
  methods: {
    setDate (year, date) {
      return `${year || ''} ${date || ''}`
    },
    showAll (val) {
      this.showFlag = true
      this.showData = val
    },
    unShowAll (val) {
      this.showFlag = false
      this.showData = ''
    },
    prePage () {
      // const paperContainer = new PaperRecordContainer('data5000', 'data5000', 'http://www.siotman.com:19401/')
      // console.log(paperContainer.getRawResponse())
      if (this.nowPage === 0) {
        this.nowPage = this.nowPage
      } else {
        this.$store.dispatch('loadMyPaperAction', this.nowPage - 1)
        this.nowPage -= 1
      }
    },
    nextPage () {
      // const paperContainer = new PaperRecordContainer('data5000', 'data5000', 'http://www.siotman.com:19401/')
      // console.log(paperContainer.getRawResponse())
      this.$store.dispatch('loadMyPaperAction', this.nowPage + 1)
      this.nowPage += 1
    }
  }
}
</script>

<style lang="scss">
  @import './paperListTable.scss';
</style>
