<template>
<div>
  <div id="table">
    <div v-if="loading === 0" class="loadingInner">
        <p class="loadingText">로딩중입니다.</p>
    </div>
    <div v-if="loading === -1" class="loadingInner">
      <p class="loadingText">등록된 주저자 논문이 없습니다.</p>
    </div>
    <div v-if="loading === 1" id="tableInner">
      <!--<div class="column">
        <div class="header">
          No.
        </div>
        <div class="cell center" v-for="data in paperData">
          <div class="content">
            <p>{{data[14]+1}}</p>
          </div>
        </div>
      </div>-->
     <div class="column">
        <div class="header">
          제목
        </div>
        <div class="cell title" v-for="data in paperData">
          <div class="content">
            <p>{{data[0]}}</p>
          </div>
        </div>
      </div>

      <div class="column">
        <div class="header">
          링크
        </div>
        <div class="cell center"  v-for="data in paperData">
          <a href="#" v-on:click="openWOSPage(data[1])">이동</a>
        </div>

      </div>
      <div class="column">
        <div class="header">
          교신 저자
        </div>
        <div class="cell author" v-for="data in paperData">
          <div class="content">
            <p>{{data[2]}}</p>
          </div>
        </div>
      </div>

      <div class="column">
        <div class="header">
          저자 상태
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[3]}}</p>
          </div>
        </div>

      </div>
      <div class="column">
        <div class="header">
          저널명
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[4]}}</p>
          </div>
        </div>

      </div>
      <div class="column">
        <div class="header">
          인용수
        </div>
        <div class="cell center" v-for="data in paperData">
          <div class="content">
            <p>{{data[5]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          발행년월
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[6]}}</p>
          </div>
        </div>

      </div>
      <div class="column">
        <div class="header">
          페이지
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[7]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          저널명
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[8]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          등급
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[9]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          백분율
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[10]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          파싱 상태
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[11]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          UID
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[12]}}</p>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="header">
          DOI
        </div>
        <div class="cell" v-for="data in paperData">
          <div class="content">
            <p>{{data[13]}}</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>
</template>

<script>

export default {
  name: 'allPaperListComponent',
  props:['loading','paperData'],
  methods: {
    openWOSPage(url){
      window.open(url)
    }
  },

}
</script>

<style lang="scss">
@import './allPaperListComponent.scss';

</style>
