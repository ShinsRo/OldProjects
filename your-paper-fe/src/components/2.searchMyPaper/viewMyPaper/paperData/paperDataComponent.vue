<template>
  <div id="paperComponent">
    <div id="paperStatus">
      <div class="status first" v-if="viewToggle.authorStatus">
        {{paper.authorType}}
      </div>
      <div class="status" v-if="viewToggle.loadStatus">
        completed
      </div>
    </div><!--논문 상태-->
    <div id="paperTitle">
      {{paper.title}}
    </div> <!--논문 제목-->
    <div id="paperInfo">
      <p v-for="i in paper.authorList.length" v-if="i<=3"  class="info">
        "{{paper.authorList[i-1]}}"
      </p>
      <p v-if="paper.authorList.length>3">
        외 {{paper.authorList.length - 3}}명
      </p>
      <p class="info">
        - 1999
      </p>
      <p class="info" v-if="viewToggle.quotation">
        / 83회 인용
      </p>
      <p class="info" v-if="viewToggle.pages && paper.sourceInfoDto.pages>=1" >
        / {{paper.sourceInfoDto.pages}}page
      </p>
    </div><!--논문 세부 정보-->
    <div id="paperURL" v-if="viewToggle.url">
      <p class="url">http://www.papersearch.net/thesis/article.asp?key=155660</p>
    </div><!--논문 url-->
  </div><!--논문 전체 레이아웃-->
</template>

<script>
export default {
  name: 'PaperComponent',
  props: ['viewToggle', 'paper'],


}
</script>

<style lang="scss">
  @import './paperDataComponent.scss';

</style>
