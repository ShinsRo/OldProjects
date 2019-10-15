<template>
    <div id="mainOption">
      <div id="mainOptionSearchLayout">
        <input/>
        <div id="mainOptionSearchButton">
          <p>
            search
          </p>
        </div>
      </div> <!--메인 옵션 검색창-->
      <div id="mainOptionFilterLayout">
        <div class="mainOptionFilter">
          <div class="mainOptionFilterName">
            <p>
              기간
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              상관없음
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              최근 1개월
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              최근 6개월
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              최근 1년
            </p>
          </div>
        </div> <!--메인 옵션 필터-->
        <div class="mainOptionFilter" style="border-bottom: 2px solid rgb(46,53,78);">
          <div class="mainOptionFilterName">
            <p>
              저자
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              상관없음
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              주 저자
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="radio"/>
            <p>
              부 저자
            </p>
          </div>
        </div> <!--메인 옵션 필터-->
        <div class="mainOptionFilter optionCheck">
          <div class="mainOptionFilterContent">
            <input type="checkbox"/>
            <p>
              주 저자 여부
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="checkbox"/>
            <p>
              피 인용수
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="checkbox"/>
            <p>
              권, 호, 페이지
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="checkbox"/>
            <p>
              상태 표시
            </p>
          </div>
          <div class="mainOptionFilterContent">
            <input type="checkbox"/>
            <p>
              URL
            </p>
          </div>

        </div><!--메인 옵션 필터-->
        <div class="mainOptionFilter" style="border-bottom: none; padding-left: 40px;">
          <div class="mainOptionOrderName">
            <p>
              최신 순
            </p>
          </div>
          <div class="mainOptionOrderName">
            <p>
              인용 순
            </p>
          </div>
        </div>




      </div> <!--메인 옵션 필터 레이아웃-->
   </div> <!--메인 옵션 검색/필터 레이아웃-->
</template>

<script>
    export default {
        name: "MainOption"
    }
</script>

<style scoped>
  #mainOption{
    width: 100%;
    margin-top: 80px;
    height: 310px;
  }

  #mainOptionSearchLayout{
    display: flex;
    align-items: center;
    background-color: rgb(242,242,242);
    border: 3px solid rgb(46,53,78);
    width: 100%;
    height: 40px;
  }

  #mainOptionSearchLayout > input{
    font-size: 15px;
    width: 90%;
    padding-left: 20px;
    background-color: rgb(242,242,242);
    border: none;
    height: 15px;
    outline: none;
  }

  #mainOptionSearchButton{
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgb(46,53,78);
    width: 10%;
    border: 1px solid;
    height: 100%;
  }
  #mainOptionSearchButton > p{
    color: white;
    font-size: 20px;
  }

  #mainOptionFilterLayout{
    width: 100%;
    margin-top: 10px;
    height: calc(100% - 46px);
  }

  .mainOptionFilter{
    display: flex;
    width: 100%;
    border-bottom: 1px solid rgb(46,53,78);
    height: calc(25% - 1px);
  }

  .mainOptionFilterName{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 10%;
  }

  .mainOptionFilterName > p{
    font-size: 18px;
    font-weight: bold;
    color: rgb(46,53,78);
  }

  .mainOptionFilterContent{
    display: flex;
    align-items: center;
    width: 15%;
  }

  .mainOptionFilterContent > input {
    outline: none;
    width: 20%;
  }

  .optionCheck{
    justify-content: center;
  }
  .mainOptionOrderName{
    display: flex;
    width: 7%;
    align-items: center;

  }

</style>
