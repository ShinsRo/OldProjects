<template>
  <div class="tableWrapper">
    <table class="searchOnMyPaper" border="3">
      <tr class="tableColumn">
        <th class="titleColumn">제목</th>
        <th class="authorColumn">저자</th>
        <th class="dateColumn">날짜</th>
        <th class="URLColumn">URL</th>
        <th class="buttonColumn"></th>
      </tr>
      <tr>
        <th class="loadingMessageContainer" colspan="5" v-if="!this.isSearch && !this.isSearchResult">검색 해주세요</th>
      </tr>
      <tr>
        <th class="emptyMessageContainer" colspan="5" v-if="this.isSearch && !this.isSearchResult">해당되는 정보가 없습니다</th>
      </tr>
      <!-- <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData.title }} <hr>
          {{ this.showData.authors.join(', ') }} <hr>
          {{ setDate(this.showData.source.publishedYear, this.showData.source.publishedDate) }}
        </td>
      </tr> -->
      <tbody v-if="this.isSearchResult">
        <tr class="tableResult" v-for="(object,index) in this.$store.getters.searchedPaperGetter.records" :key="index" @click="showAll(object)" @mousewheel="unShowAll(object)">
          <td class="titleResult">{{ object.title }}</td>
          <td class="authorResult">{{ object.authors[0] }}</td>
          <td class="dateResult">{{ setDate(object.source.publishedYear, object.source.publishedDate )}}</td>
          <td class="URLResult">{{ object.lamrData.sourceURL }}</td>
          <td class="buttonResult"><button class="paperAddButton" type="button">Add</button></td>
        </tr>
      </tbody>
    </table>
    <!------------------------------------  -->
    <table class="searchOnMyPaper" border="3">
      <tr class="tableColumn">
        <th class="titleColumn">제목</th>
        <th class="authorColumn">저자 상태</th>
        <th class="dateColumn">날짜</th>
        <th class="URLColumn">URL</th>
        <th class="buttonColumn"></th>
      </tr>
      <tr>
        <th class="loadingMessageContainer" v-if="this.isLoading" colspan="5">등록된 논문 정보를 불러오는 중 입니다</th>
      </tr>
      <tr>
        <th class="emptyMessageContainer" v-if="this.isMyPaperEmpty" colspan="5">등록된 논문이 없습니다</th>
      </tr>
      <tr v-if="showFlag">
        <td class="allData" colspan="5">
          {{ this.showData[0] }} <hr>
          {{ this.showData[3].join(', ') }} <hr>
          {{ this.showData[4] }}
        </td>
      </tr>
      <tbody v-if="!this.isMyPaperEmpty">
        <tr class="tableResult" v-for="(object, index) in this.myPapers" :key="index" @click="showAll(object)" @mousewheel="unShowAll(object)">
          <td class="titleResult">{{ object[0] }}</td>
          <td class="authorResult">
            <select id="test" class="selectAuthorType" style="width: 98%;" v-bind="selectAuthorType(object[2])">
              <option id="refferingType" value="REFFERING" selected="selected">상관없음</option>
              <option id="generalType" value="GENERAL">공저자</option>
              <option id="reprintType" value="REPRINT">교신저자</option>
            </select>
          </td>
          <!-- <td class="authorResult">{{ object[3][0] }}</td> -->
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
  name: 'paperList',
  beforeMount () {
    this.$store.dispatch('loadMyPaperAction', 0)
    setTimeout(() => {
      const paperContainer = this.$store.getters.memberPaperGetter
      this.myPapers = paperContainer.getRecords([3, 4, 6, 7, 9])
    }, 2000)
  },
  beforeUpdate () {
    this.isLoading = false
    if (this.myPapers.length !== 0) {
      this.isMyPaperEmpty = false
    } else {
      this.isMyPaperEmpty = true
    }
  },
  data () {
    return {
      nowPage: 0,
      showFlag: false,
      flag: 2,
      myPapers: '',
      AllData: '',
      showData: '',
      isLoading: true,
      isMyPaperEmpty: false,
      isSearch: false,
      isSearchResult: false
    }
  },
  computed: {
    isSearched () {
      return this.$store.getters.searchTriggerGetter
    }
  },
  watch: {
    'nowPage': function () {
      const paperContainer = this.$store.getters.memberPaperGetter
      this.myPapers = paperContainer.getRecords([3, 4, 6, 7, 9])
    },
    isSearched (newFlag, oldFlag) {
      this.isSearch = true
      this.isSearchResult = true
      console.log(this.$store.getters.searchedPaperGetter.records)
    } // 검색이 되었는지에 대한 감지
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
  @import './paperListTable.scss';
</style>
