<template>
  <div class="tableWrapper">
    <table class="searchOnMyPaper" border="3">
      <tr class="tableColumn">
        <th class="titleColumn">제목</th>
        <th class="authorColumn">저자 상태</th>
        <th class="dateColumn">날짜</th>
        <th class="URLColumn">URL</th>
        <th class="buttonColumn"></th>
      </tr>
      <tr>
        <th class="emptySearchContainer" v-if="dataContainer==='emptySearch'" colspan="5">등록된 논문이 없습니다</th>
      </tr>
      <tr>
        <th class="loadingSearchContainer"
        v-if="dataContainer==='loadingSearch'" colspan="5">논문을 불러오는 중입니다</th>
      </tr>
      <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData[1] }} <hr>
          {{ this.showData[3] }} <hr>
          {{ this.showData[5] }}
        </td>
      </tr>
      <tbody v-if="dataContainer==='fullSearch'">
        <tr class="tableResult" v-for="(object, index) in this.myPapers" :key="index">
          <td class="titleResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[1] }}</td>
          <td class="authorResult">
            <select id="authorType" class="selectAuthorType" style="width: 98%;" v-model="object[3]" v-on:change="authorTypeChange($event,object)">
              <option value="REFFERING">상관없음</option>
              <option value="GENERAL">공저자</option>
              <option value="REPRINT">교신저자</option>
            </select>
          </td>
          <td class="dateResult" @click="showAll(object)" @mousewheel="unShowAll(object)">{{ object[5] }}</td>
          <td class="URLResult" @click="showAll(object)" @mousewheel="unShowAll(object)"> {{ object[2] }} </td>
          <td class="buttonResult"><button class="paperAddButton" type="button" v-on:click="removePaper(object)">Remove</button></td>
        </tr>
      </tbody>
    </table>
    <div class="pageChangeContainer" v-if="dataContainer==='fullSearch'">
      <button class="preButton" type="button" v-on:click="prePage()">◀</button>
      <div class="pagingButtonContainer">
        <button class="pagingButton" v-for="index in ButtonCounter()" :key="index" v-on:click="selectPage(index)">{{ pageNum(index) }}</button>
      </div>
      <button class="nextButton" type="button" v-on:click="nextPage()">▶</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'paperListOnMyPaper',
  data () {
    return {
      nowPage: 1,
      endPage: null,
      pageDiv: null,
      buttonCounter: 1,
      showFlag: false,
      flag: 2,
      myPapers: [],
      AllData: '',
      showData: '',
      dataContainer: 'loadingSearch',
      objectContainer: null,
      testPage: null
    }
  },
  beforeCreate () {
    this.dataContainer = 'loadingSearch'
  },
  mounted () {
    this.objectContainer = this.$store.getters.MEMBER_OBJECT_GETTER
    this.endPage = this.$store.getters.MEMBER_PAGING_COUNT_GETTER
    this.pageDiv = parseInt(this.endPage / 10)
  },
  beforeUpdate () {
    this.myPapers = this.$store.getters.MEMBER_PAPER_GETTER
  },
  computed: {
    isSearched () {
      return this.$store.getters.MEMBER_PAPER_GETTER
    },
    getEndPage () {
      return this.$store.getters.MEMBER_PAGING_COUNT_GETTER
    }
  },
  watch: {
    isSearched (newVal, oldVal) {
      this.myPapers = this.$store.getters.MEMBER_PAPER_GETTER
      if (this.myPapers.length === 0) {
        this.dataContainer = 'emptySearch'
      } else {
        this.dataContainer = 'fullSearch'
      }
      this.$store.dispatch('MEMBER_PAGING_COUNT_ACTION')
      this.pageDiv = parseInt(this.endPage / 10)
    },
    getEndPage(){
      this.endPage = this.$store.getters.MEMBER_PAGING_COUNT_GETTER
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
    authorTypeChange (event, object) {
      const changeType = event.target.value
      this.objectContainer.addOrUpdateOne(object[0], changeType).then(res => {
        this.$store.dispatch('MEMBER_PAPER_ACTION', [1, 3, 4, 6, 7, 9])
        alert('변경 되었습니다')
      }).catch(err => {
        console.log(err)
      })
    },
    unShowAll (val) {
      this.showFlag = false
      this.showData = ''
    },
    removePaper (val) {
      var removeCheck = confirm('해당 논문을 삭제하시겠습니까?')

      if (removeCheck === true) {
        this.unShowAll()
        this.objectContainer.deleteOne(val[0]).then(res => {
          this.$store.dispatch('MEMBER_PAPER_ACTION', [1, 3, 4, 6, 7, 9])
        }).catch(err => {
          console.log(err)
        })
      }
    },
    ButtonCounter () {
      if (this.buttonCounter <= this.pageDiv) {
        return 10
      } else {
        return this.endPage - (this.buttonCounter - 1) * 10
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
      /*
      var page = ((this.buttonCounter - 1) * 10) + index
      this.objectContainer.retrive(page).then(res => {
        const retriveData = this.objectContainer.getRecords([1, 3, 4, 6, 7, 9])
        this.$store.dispatch('MEMBER_PAGING_ACTION', retriveData)
      })*/
      var page = ((this.buttonCounter - 1) * 10) + index
      this.$store.dispatch('MEMBER_PAPER_PAGING_ACTION', {payload: [1, 3, 4, 6, 7, 9], page: page})
    }
  }
}
</script>

<style lang="scss">
  @import './paperListOnMyPaper.scss';
</style>
