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
        <div class="mainOptionFilter optionCheck">
          <div class="mainOptionFilterContent">
            <input class="check" type="checkbox"/>
            <p class="text">
              주 저자 여부
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input class="check" type="checkbox"/>
            <p class="text">
              피 인용수
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input class="check" type="checkbox"/>
            <p class="text">
              권, 호, 페이지
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input class="check" type="checkbox"/>
            <p class="text">
              상태 표시
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input class="check" type="checkbox"/>
            <p class="text">
              URL
            </p>
          </div>

        </div><!--메인 옵션 필터-->
        <div class="mainOptionFilter" style="border-bottom: none; padding-left: 40px;">
          <div class="mainOptionOrderName">
            <p class="text">
              최신 순
            </p>
          </div>
          <div class="mainOptionOrderName">
            <p class="text">
              인용 순
            </p>
          </div>
        </div>
      </div> <!--메인 옵션 필터 레이아웃-->
   </div> <!--메인 옵션 검색/필터 레이아웃-->
</template>

<script>
export default {
  name: 'MainOption'
}
</script>

<style lang="scss">
@import './MainOption.scss';

</style>
