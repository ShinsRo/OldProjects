<template>
  <div class="wrapper">
    <div class="paperContentBox">
      <div class="paperCategorys">
        <p class="paperName">제목</p>
        <p class="paperAuthor">주저자</p>
        <p class="paperDOI">DOI</p>
        <p class="paperURL">URL</p>
      </div>
      <div class="paperContents">
        <li class="dataList" v-for="paper in papers"
        :key="paper.name">
          <p class="dataName">{{paper.name}}</p>
          <p class="dataAuthor">{{ paper.author}}</p>
          <p class="dataDOI">{{ paper.DOI}}</p>
          <p class="dataURL">{{paper.URL}}</p>
          <button class="paperEditButton" type="button">Add</button>
        </li>
      </div>
    </div>
    <div class="paperContentBox">
      <div class="paperCategorys">
        <p class="paperName">제목</p>
        <p class="paperAuthor">저자 상태</p>
        <p class="paperDOI">DOI</p>
        <p class="paperURL">URL</p>
      </div>
      <div class="paperContents">
        <li class="dataList" v-for="paper in papers"
        :key="paper.name">
          <p class="dataName">{{ paper.name }}</p>
          <p class="dataAuthor">{{ paper.author }}</p>
          <p class="dataDOI">{{ paper.DOI }}</p>
          <p class="dataURL">{{ paper.URL }}</p>
          <button class="paperEditButton" type="button">remove</button>
        </li>
      </div>
    </div>
    <div class="saveContainer">
      <button class="saveButton" type="button">Save</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'paperChangeComponent',
  data () {
    return {
      papers: [
        {
          name: 'computer',
          author: 'An',
          DOI: 'I Dont know number',
          URL: 'www.sejong.ac.kr'
        },
        {
          name: 'computer',
          author: 'An',
          DOI: 'I Dont know number',
          URL: 'www.sejong.ac.kr'
        },
        {
          name: 'computer',
          author: 'An',
          DOI: 'I Dont know number',
          URL: 'www.sejong.ac.kr'
        },
        {
          name: 'computer',
          author: 'An',
          DOI: 'I Dont know number',
          URL: 'www.sejong.ac.kr'
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  @import './paperChangeComponent';
</style>
