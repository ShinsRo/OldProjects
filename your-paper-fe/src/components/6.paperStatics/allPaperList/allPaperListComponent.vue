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
        <div class="cell link"  v-for="data in paperData">
          <a href="#" onClick="openWOSpage(data[1])">이동</a>
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
          저자
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
        <div class="cell" v-for="data in paperData">
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

import { PaperRecordContainer, SORT_MP_ENUM } from '../../../../public/apis/api/paper-api.js'
export default {
  name: 'allPaperListComponent',
  data () {
    return {
      paperData: [],
      headers: {},
      loading: 0
    }
  },
  methods: {
    openWOSpage(){

    }
  },

  mounted () {
    this.loading = 0;

    /*
    const token = sessionStorage.getItem('token')
    const session = JSON.parse(sessionStorage.getItem('data'))

    const username = session.username
    const authorization = token
    const SERVER_URL = 'http://www.siotman.com:19401/'

    const container = new PaperRecordContainer(username, authorization, SERVER_URL)

    container.listByPage(0, 10, SORT_MP_ENUM.TITLE, true)
      .then(res => {
        const records = container.getRecords([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18])
        const headers = container.getHeaders([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18])

        console.log(headers)

        this.paperData = records
        this.headers = headers
        console.log(this.paperData)
        console.log(this.headers)
      })*/
  },
  computed:{
    isLoading(){
      return this.$store.getters.MEMBER_PAPER_GETTER
    }
  },
  watch:{
    isLoading(newVal, oldVal){
      this.paperData = this.$store.getters.MEMBER_PAPER_GETTER
      console.log('allpeper',this.paperData);
      if(this.paperData.length === 0){
        this.loading = -1
      }
      else{
        this.loading = 1
      }
    }
  }

}
</script>

<style lang="scss">
@import './allPaperListComponent.scss';

</style>
