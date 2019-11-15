<template>
  <div class="searchOnWOSWrapper">
    <div class="category">
      <select class="options" v-model="category">
        <option selected disabled value="">카테고리</option>
        <option value="title">논문제목</option>
        <option value="title">저자</option>
        <option value="DOI">DOI</option>
      </select>
      <select class="organization" v-model="organization">
        <option selected disabled value="">기관</option>
        <option value="sejong">세종대학교</option>s
        <option value="Konkuk">건국대학교</option>
        <option value="Seoul">서울대학교</option>
      </select>
    </div>
    <div class="inputs">
      <div class="searchInputContainer">
        <input class="searchInputBox" type="text" v-bind:placeholder="placeholder" v-model="searchData">
      </div>
      <div class="dateInputContainer">
        <input class="startDate" type="text" placeholder="시작날짜 yyyy-mm-dd" v-model="startDate">
        <input class="endDate" type="text" placeholder="끝 날짜 yyyy-mm-dd" v-model="endDate">
      </div>
    </div>
    <div class="button">
      <button class="searchButton" type="button">Search</button>
    </div>
  </div>
</template>

<script>
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
      // searchInput: {
      //   title: false,
      //   authorName: false,
      //   DOI: false,
      //   organization: false
      // },
      // searchPaperInput: ''
    }
  },
  methods: {
    searchOnWos () {
      console.log(this.searchInput.title)
      console.log(this.searchPaperInput)
    },
    setTrue () {
      return true
    }, // 드롭박스 보임
    setFalse () {
      return false
    }, // 드롭박스 보이지 않음
    selectCategory (val) {
      if (val === 'title') {
        this.placeholder = '논문 제목을 검색해주세요'
      } else if (val === 'authorName') {
        this.placeholder = '저자를 검색해주세요'
      } else if (val === 'DOI') {
        this.placeholder = 'DOI를 검색해주세요'
      } else {
        this.placeholder = '연구기관을 검색해주세요'
      } // 검색창에 해당 키워드를 검색할 수 있도록 placeholder를 바꿔줌
      for (var key in this.searchInput) {
        if (key === val) {
          this.searchInput[key] = true
        } else {
          this.searchInput[key] = false
        }
      }
      this.isDropBoxShow = this.setFalse() // 드롭박스 닫음
    }
    // 드롭박스에서 선택을 할 경우 검색 카테고리를 변경
  }
}
</script>

<style lang="scss">
  @import './searchAllPaperComponent.scss';
</style>
