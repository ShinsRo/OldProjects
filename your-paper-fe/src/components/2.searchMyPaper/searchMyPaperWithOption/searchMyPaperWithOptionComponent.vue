<template>

  <div id="mainOption">
    <div id="mainOptionSearchLayout">
      <input class="searchInput"/>
      <div id="mainOptionSearchButton" @click="searchMyPaper">
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
          <input class="check" type="radio" name="duration" v-model="duration" value="all"/>
          <p class="text">
            상관없음
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="duration" v-model="duration" value="oneMonth"/>
          <p class="text">
            최근 1개월
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="duration" v-model="duration" value="sixMonth"/>
          <p class="text">
            최근 6개월
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="duration" v-model="duration" value="oneYear"/>
          <p class="text">
            최근 1년
          </p>
        </div>
      </div> <!--메인 옵션 필터 : 기간-->

      <div class="mainOptionFilter" style="border-bottom: 2px solid rgb(46,53,78);">
        <div class="mainOptionFilterName">
          <p class="text">
            저자
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="author" v-model="author" value="all"/>
          <p class="text">
            상관없음
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="author" v-model="author" value="main"/>
          <p class="text">
            주 저자
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="radio" name="author" v-model="author" value="sub"/>
          <p class="text">
            부 저자
          </p>
        </div>
      </div> <!--메인 옵션 필터 : 저자-->
    </div> <!--메인 옵션 필터 레이아웃-->

  </div> <!--메인 옵션 검색/필터 레이아웃-->
</template>

<script>
export default {
  name: 'MainOption',
  data () {
    return {
      duration: 'all',
      author: 'all'
    }
  },
  methods: {
    searchMyPaper () {
      console.log(this.duration, this.author)
    }
  }
}
</script>

<style lang="scss">
  @import './searchMyPaperWithOptionComponent.scss';

</style>
