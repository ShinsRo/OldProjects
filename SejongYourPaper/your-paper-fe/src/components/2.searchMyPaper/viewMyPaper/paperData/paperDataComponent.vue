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

      <p v-for="i in author.length" v-if="author[i-1] !== undefined"> "{{author[i-1]}}" &nbsp</p>

      <p v-if="ect>0"> 외 {{ect}} 명</p>
      <p class="info" v-if="paper[6] !== ''">
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
      <p class="url">{{paper[1]}}</p>
    </div><!--논문 url-->
  </div><!--논문 전체 레이아웃-->
</template>

<script>
export default {
  name: 'PaperComponent',
  props: ['viewToggle', 'paper'],
  data () {
    return {
      author: [],
      ect: 0,
    }
  },
  mounted () {
    let i = 0
    const authorList = this.paper[4].split('; ')

    this.ect = authorList.length-3
    if (this.paper[2] !== ""){
      i = 1
      this.author[0] = this.paper[2]
      this.ect += 1
    }

    for (; i < 3 ; i++){
      this.author.push(authorList[i])
    }
  }
}
</script>

<style lang="scss">
  @import './paperDataComponent.scss';

</style>
