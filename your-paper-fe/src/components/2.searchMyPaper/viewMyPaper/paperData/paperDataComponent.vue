<template>
  <div id="paperComponent">
    <div id="paperStatus">
      <div class="status first" v-if="viewToggle.authorStatus">
        주 저자
      </div>
      <div class="status" v-if="viewToggle.loadStatus">
        completed
      </div>
    </div><!--논문 상태-->
    <div id="paperTitle">
      선행 관광행동 연구의 비판적 고찰: Annals of Tourism Research 의 연구논문을 중심으로
    </div> <!--논문 제목-->
    <div id="paperInfo">
      <p class="info">
        고용석
      </p>
      <p class="info">
        - 1999
      </p>
      <p class="info" v-if="viewToggle.quotation">
        / 83회 인용
      </p>
      <p class="info" v-if="viewToggle.pages">
        / 1권, 3호, 299페이지
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
  mount(){
    console.log("hi", paper)
  }

}
</script>

<style lang="scss">
  @import './paperDataComponent.scss';

</style>
