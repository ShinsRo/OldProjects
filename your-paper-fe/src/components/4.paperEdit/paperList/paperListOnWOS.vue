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
      <tr>
        <th class="beforeSearchContainer" colspan="5" v-if="dataContainer==='beforeSearch'">검색 해주세요</th>
      </tr>
      <tr>
        <th class="emptyMessageContainer" colspan="5" v-if="dataContainer==='emptySearch'">해당되는 정보가 없습니다</th>
      </tr>
      <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData[0] }} <hr>
          {{ this.showData[3].split(';').join(' ') }} <hr>
          {{ this.showData[4] }}
        </td>
      </tr>
      <tbody v-if="dataContainer==='fullSearch'">
        <tr class="tableResult" v-for="(object,index) in searchedPaper" :key="index">
          <td class="titleResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[1] }}</td>
          <td class="authorResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[3].split(';')[0] }}</td>
          <td class="dateResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[4] }}</td>
          <td class="URLResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[2] }}</td>
          <td class="buttonResult"><button class="paperAddButton" type="button" v-on:click="addOnMyPaper(object, index)">Add</button></td>
        </tr>
      </tbody>
    </table>
    <div class="pageChangeContainer" v-if="dataContainer==='fullSearch'">
      <button class="preButton" type="button">◀</button>
      <div class="pagingButtonContainer">
        <button class="pagingButton" v-for="index in ButtonCounter()" :key="index" v-on:click="selectPage(index)">{{ pageNum(index) }}</button>
      </div>
      <button class="nextButton" type="button">▶</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'paperListOnMyPaper',
  data () {
    return {
      nowPage: 1,
      endPage: 0,
      pageDiv: null,
      buttonCounter: 1,
      showFlag: false,
      searchedPaper: {},
      AllData: '',
      showData: '',
      dataContainer: 'beforeSearch',
      WOSContainer: null
    }
  },
  mounted () {
    this.dataContainer = 'beforeSearch'
    this.endPage = this.$store.getters.SEARCH_PAGE_GETTER
    this.pageDiv = 0
    this.WOSContainer = this.$store.getters.WOS_OBJECT_GETTER
  },
  computed: {
    getSearchedData () {
      return this.$store.getters.SEARCH_ON_WOS_GETTER
    },
    getEndPage () {
      return this.$store.getters.SEARCH_PAGE_GETTER
    }
  },
  watch: {
    getSearchedData (newVal, oldVal) {
      this.searchedPaper = newVal
      if (this.searchedPaper.length === 0) {
        this.dataContainer = 'emptySearch'
      } else {
        this.dataContainer = 'fullSearch'
      }
    },
    getEndPage (newVal, oldVal) {
      this.endPage = newVal
      this.pageDiv = parseInt(this.endPage / 10)
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
    addOnMyPaper (object, index) {
      var inputType = prompt('저자 상태를 입력해주세요 (교신저자, 공저자, 상관없음) :')

      var authorType = ''

      if (inputType === '교신저자') {
        authorType = 'REPRINT'
      } else if (inputType === '공저자') {
        authorType = 'GENERAL'
      } else if (inputType === '상관없음') {
        authorType = 'REFFERING'
      } else {
        return alert('잘못 입력했습니다')
      }
      const container = this.$store.getters.MEMBER_OBJECT_GETTER
      container.addOrUpdateOne(object[0], authorType).then(res => {
        this.$store.dispatch('MEMBER_PAPER_ACTION', [1, 3, 4, 6, 7, 9])
        alert('논문이 추가되었습니다')
        this.unShowAll(object)
      }).catch(err => {
        console.log(err)
      })
      this.unShowAll(object)
    },
    ButtonCounter () {
      if (this.endPage !== 0) {
        if (this.buttonCounter <= this.pageDiv) {
          return 10
        } else {
          return this.endPage - (this.buttonCounter - 1) * 10
        }
      }
    },
    prePage () {
      if (this.buttonCounter > 1) {
        this.buttonCounter -= 1
      }
    },
    nextPage () {
      if (this.buttonCounter <= this.pageDiv) {
        this.buttonCounter += 1
      }
    },
    pageNum (index) {
      return (this.buttonCounter - 1) * 10 + index
    },
    selectPage (index) {
      var page = ((this.buttonCounter - 1) * 10) + index
      this.WOSContainer.retrive(page).then(res => {
        const retriveData = this.WOSContainer.getRecords([1, 3, 4, 7, 9])
        this.$store.dispatch('SEARCH_ON_WOS_ACTION', retriveData)
      })
    }
  }
}
</script>

<style lang="scss">
  @import './paperListOnWOS.scss';
</style>
