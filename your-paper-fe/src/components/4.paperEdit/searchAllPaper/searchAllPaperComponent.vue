<template>
  <div class="searchOnWOSWrapper">
    <div class="category">
      <select class="options" v-model="category">
        <option selected disabled value="">카테고리</option>
        <option value="TI">논문제목</option>
        <option value="AU">저자</option>
        <option value="DO">DOI</option>
      </select>
      <select class="organization" v-model="organization">
        <option selected disabled value="">기관</option>
        <option value="Sejong Univ">세종대학교</option>s
        <option value="Konkuk Univ">건국대학교</option>
        <option value="Seoul Univ">서울대학교</option>
        <option value="">없음</option>
      </select>
    </div>
    <div class="inputs">
      <div class="searchInputContainer">
        <input class="searchInputBox" type="text" v-bind:placeholder="this.placeholder" v-model="searchData">
      </div>
      <div class="dateInputContainer">
        <input class="startDate" type="text" placeholder="시작날짜 yyyy-mm-dd" v-model="startDate">
        <input class="endDate" type="text" placeholder="끝 날짜 yyyy-mm-dd" v-on:keyup.enter="makeQuery()" v-model="endDate">
      </div>
    </div>
    <div class="button">
      <button class="searchButton" type="button" v-on:click="makeQuery()">Search</button>
    </div>
  </div>
</template>

<script>
// import { WokSearchClient } from '../../../../public/apis/api/wos-api.js'

export default {
  name: 'searchAllPaperComponent',
  data () {
    return {
      category: '',
      organization: '',
      searchData: '',
      startDate: '',
      endDate: '',
      placeholder: '제목, 저자, DOI를 입력해주세요'
    }
  },
  methods: {
    makeQuery () {
      const WOSObject = this.$store.getters.WOS_OBJECT_GETTER

      const pageSize = 10
      const category = this.category
      const query = this.searchData
      const begin = this.startDate
      const end = this.endDate
      const organizations = this.organization

      if (organizations[0] === '') organizations.pop()

      if (query.length < 4) {
        return alert('검색어가 너무 짧습니다.')
      }
      if (!(new Date(begin))) {
        return alert('시작 날짜가 비정상적입니다.')
      }
      if (!(new Date(end))) {
        return alert('끝 날짜가 비정상 적입니다.')
      }

      WOSObject.setPageSize(pageSize)
      WOSObject.setSortField('TC', false)

      const userQuery = WOSObject.buildUserQuery(category, query, organizations)
      WOSObject.search(userQuery, begin, end, false, false).then(res => {
        this.$store.dispatch('SEARCH_ON_WOS_ACTION', WOSObject.getRecords([1, 3, 4, 7, 9]))
        this.$store.dispatch('SEARCH_PAGING_ACTION', WOSObject.getPageState().endPage)
        console.log(this.$store.getters.SEARCH_PAGE_GETTER)
      }).catch(error => {
        console.log('Search, if error :')
        console.log(error)
      })
    }
  }
}
</script>

<style lang="scss">
  @import './searchAllPaperComponent.scss';
</style>
