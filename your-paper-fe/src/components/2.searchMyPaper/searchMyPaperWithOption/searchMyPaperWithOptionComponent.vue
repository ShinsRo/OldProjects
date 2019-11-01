<template>

  <div id="mainOption">
    <div id="mainOptionSearchLayout">
      <input class="searchInput"/>
      <div id="mainOptionSearchButton">
        <p class="text">
          search
        </p>
      </div>
    </div> <!--메인 옵션 검색창-->
    <div id="mainOptionFilterLayout">
      <div class="mainOptionFilter">
        <div class="mainOptionFilterName">
          <p class="text">
            기간
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            상관없음
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            최근 1개월
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            최근 6개월
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            최근 1년
          </p>
        </div>
      </div> <!--메인 옵션 필터-->

      <div class="mainOptionFilter" style="border-bottom: 2px solid rgb(46,53,78);">
        <div class="mainOptionFilterName">
          <p class="text">
            저자
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            상관없음
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            주 저자
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio"/>
          <p class="text">
            부 저자
          </p>
        </div>
      </div> <!--메인 옵션 필터-->
<!--메인 옵션 필터-->
    </div> <!--메인 옵션 필터 레이아웃-->
  </div> <!--메인 옵션 검색/필터 레이아웃-->
</template>

<script>
  export default {
    name: 'MainOption'
  }
</script>

<style lang="scss">
  @import './searchMyPaperWithOptionComponent.scss';

</style>
