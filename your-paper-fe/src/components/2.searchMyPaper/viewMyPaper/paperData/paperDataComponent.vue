<template>
  <div id="paperComponent">
    <div id="paperStatus">
      <div class="status first" v-if="viewToggle.authorStatus">
        {{paper[3]}}
      </div>
      <div class="status" v-if="viewToggle.loadStatus">
        {{paper[12]}}
      </div>
    </div><!--논문 상태-->
    <div id="paperTitle">
      {{paper[0]}}
    </div> <!--논문 제목-->
    <div id="paperInfo">
      <p v-if='paper[2] !==""' class="info mainAuthor">
        "{{paper[2]}}"
      </p>
      <p v-for="i in paper[4].length" v-if='i<3 && i<=paper[4].length'> "{{paper[4][i-1]}}"&nbsp</p>
      <p v-if="paper[4].length-2>0"> 외 {{paper[4].length -2}} 명</p>
      <p class="info" v-if="paper[6] != ''">
        / {{paper[6]}}
      </p>
      <p class="info" v-if="viewToggle.quotation">
        / {{paper[5]}}회 인용
      </p>
      <p class="info" v-if="viewToggle.pages">
        / {{paper[8]}}권, {{paper[9]}}호, {{paper[10]}}페이지
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
