<template>
  <div class="paperListWrapper">
    <div class="paperColumnContainer">
      <div class="ColumnPaperName">
        <p class="paperNameColumn">제목</p>
      </div>
      <div class="ColumnAuthorName">
        <p class="authorNameColumn" v-if="flag===1">저자</p>
        <p class="authorNameColumn" v-if="flag===2">주 저자 여부</p>
      </div>
      <div class="ColumnDOI">
        <p class="DOIColumn">DOI</p>
      </div>
      <div class="ColumnURL">
        <p class="URLColumn">URL</p>
      </div>
    </div>
    <div class="paperListContainer">
      <div class="emptyContainer">
        <p class="emptyMessage">논문 정보가 없습니다</p>
      </div>
      <!-- 논문 관련 정보가 없을 경우 -->
      <div class="list" v-for="(paper, index) in papers" :key="index">
        <div class="paperName">{{ paper.name }}</div>
        <div class="authorName" v-if="flag===1">{{ paper.authorName }}</div>
        <div class="authorName" v-if="flag===2">
         <select class="isRepresent">
            <option disabled selected value="">저자 여부를 선택</option>
            <option value="RepresentAuthor">주 저자</option>
            <option value="CommonAuthor">공 저자</option>
          </select>
        </div>
        <div class="DOI">{{ paper.DOI }}</div>
        <div class="URL">{{ paper.URL }}</div>
        <button class="paperAddButton" type="button" v-if="flag===1">Add</button>
        <button class="paperRemoveButton" type="button" v-if="flag===2">Remove</button>
      </div>
      <!-- v-for를 이용하여 논문 리스트 업 -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'paperList',
  props: ['EditFlag'],
  data () {
    return {
      flag: this.EditFlag,
      papers: [
        {
          name: 'ComputerEngineering',
          authorName: 'JohanAn',
          DOI: '1234',
          URL: 'http://123.456.789'
        },
        {
          name: 'ChickenMukja',
          authorName: 'GangEunLee',
          DOI: '4567',
          URL: 'http://789.456.123'
        }
      ]
    }
  }
}
</script>

<style lang="scss">
  @import './paperListComponent.scss';
</style>
