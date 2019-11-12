<template>
  <div class="searchAllPaperWrapper">
    <div class="dropBoxContainer"       v-on:mouseover="isDropBoxShow=setTrue()"
    v-on:mouseleave="isDropBoxShow=setFalse()">
      <p class="dropBoxSelector">▼</p>
      <div class="dropBoxOptionContainer" v-show="isDropBoxShow">
        <div class="searchOption" v-on:click="selectCategory('title')">논문명</div>
        <div class="searchOption" v-on:click="selectCategory('authorName')">저자</div>
        <div class="searchOption" v-on:click="selectCategory('DOI')">DOI</div>
        <div class="searchOption organizationOption" v-on:click="selectCategory('organization')">연구기관</div>
      </div>
    </div>
    <div class="searchInputContainer">
      <input class="searchInput" type="text" v-bind:placeholder="placeholder" v-model="searchPaperInput">
    </div>
    <div class="buttonContainer">
      <button class="searchButton" type="button" v-on:click="searchOnWos()">Search</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'searchAllPaperComponent',
  data () {
    return {
      isDropBoxShow: false,
      placeholder: '제목, 저자, DOI, 연구기관',
      searchInput: {
        title: false,
        authorName: false,
        DOI: false,
        organization: false
      },
      searchPaperInput: ''
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
