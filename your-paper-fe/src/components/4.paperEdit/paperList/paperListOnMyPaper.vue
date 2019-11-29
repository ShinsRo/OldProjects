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
      <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData[0] }} <hr>
          {{ this.showData[3].join(', ') }} <hr>
          {{ this.showData[4] }}
        </td>
      </tr>
      <tbody v-if="dataContainer==='fullSearch'">
        <tr class="tableResult" v-for="(object, index) in this.myPapers" :key="index" @click="showAll(object)" @mousewheel="unShowAll(object)">
          <td class="titleResult">{{ object[0] }}</td>
          <td class="authorResult">
            <select id="test" class="selectAuthorType" style="width: 98%;" v-bind="selectAuthorType(object[2])">
              <option id="refferingType" value="REFFERING" selected="selected">상관없음</option>
              <option id="generalType" value="GENERAL">공저자</option>
              <option id="reprintType" value="REPRINT">교신저자</option>
            </select>
          </td>
          <td class="dateResult">{{ object[4] }}</td>
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

export default {
  name: 'paperListOnMyPaper',
  data () {
    return {
      nowPage: 0,
      showFlag: false,
      flag: 2,
      myPapers: this.$store.getters.MEMBER_PAPER_GETTER,
      AllData: '',
      showData: '',
      dataContainer: 'emptySearch'
    }
  },
  mounted () {
    this.dataContainer = 'emptySearch'
  },
  // beforeMount () {
  //   this.$store.dispatch('MEMBER_INFO_SET_ACTION')
  // },
  // mounted () {
  //   // console.log('getter test')
  //   // console.log(this.$store.getters.API_OBJECT_GETTER)
  // },
  beforeUpdate () {
    this.isLoading = false
    if (this.myPapers.length !== 0) {
      this.isMyPaperEmpty = false
    } else {
      this.isMyPaperEmpty = true
    }
  },
  // computed: {
  //   isSearched () {
  //     return this.$store.getters.searchTriggerGetter
  //   }
  // },
  // watch: {
  //   'nowPage': function () {
  //     const paperContainer = this.$store.getters.memberPaperGetter
  //     this.myPapers = paperContainer.getRecords([3, 4, 6, 7, 9])
  //   },
  //   isSearched (newFlag, oldFlag) {
  //     this.isSearch = true
  //     this.isSearchResult = true
  //     console.log(this.$store.getters.searchedPaperGetter.records)
  //   }
  // 검색이 되었는지에 대한 감지
  // },
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
    selectAuthorType (val) {
      // 가져온 논문이 누구의 논문인지 selectBox에
      // 표시하기 위한 메서드
      // console.log(document.getElementById('generalType'))
      // if (val === 'REFFERING') {
      //   document.getElementById('test').option[0].selected = 'true'
      // } else if (val === 'GENERAL') {
      //   console.log(document.getElementById('test'))
      //   document.getElementById('test').option[1].selected = 'true'
      // } else {
      //   console.log('교신저자')
      //   document.getElementById('test').option[2].selected = 'true'
      // }
    },
    prePage () {
      if (this.nowPage === 0) {
        this.nowPage = this.nowPage
      } else {
        this.$store.dispatch('loadMyPaperAction', this.nowPage - 1)
        this.nowPage -= 1
      }
    },
    nextPage () {
      this.$store.dispatch('loadMyPaperAction', this.nowPage + 1)
      this.nowPage += 1
    }
  }
}
</script>

<style lang="scss">
  @import './paperListOnMyPaper.scss';
</style>
