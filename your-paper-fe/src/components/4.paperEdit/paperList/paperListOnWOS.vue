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
          {{ this.showData.title }} <hr>
          {{ this.showData.authors.join(', ') }} <hr>
          {{ setDate(this.showData.source.publishedYear, this.showData.source.publishedDate) }}
        </td>
      </tr>
      <tbody v-if="dataContainer==='fullSearch'">
        <tr class="tableResult" v-for="(object,index) in this.$store.getters.searchedPaperGetter.records" :key="index" @click="showAll(object)" @mousewheel="unShowAll(object)">
          <td class="titleResult">{{ object.title }}</td>
          <td class="authorResult">{{ object.authors[0] }}</td>
          <td class="dateResult">{{ setDate(object.source.publishedYear, object.source.publishedDate )}}</td>
          <td class="URLResult">{{ object.lamrData.sourceURL }}</td>
          <td class="buttonResult"><button class="paperAddButton" type="button">Add</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

export default {
  name: 'paperListOnMyPaper',
  data () {
    return {
      showFlag: false,
      searchedPaper: {},
      AllData: '',
      showData: '',
      dataContainer: 'beforeSearch'
    }
  },
  mounted () {
    this.dataContainer = 'beforeSearch'
  },
  computed: {
    isSearched () {
      return this.$store.getters.SEARCH_TRIGGER_GETTER
    }
  },
  watch: {
    isSearched (newFlag, oldFlag) {
      this.dataContainer = 'fullSearch'
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
    }
  }
}
</script>

<style lang="scss">
  @import './paperListOnWOS.scss';
</style>
