<template>
<div>
  <div id="table">
    <div id="tableInner">
     <div class="column">
        <div class="header">
          제목
        </div>
        <div class="body" v-for="data in paperData">
          <p> {{data[0]}} </p>

        </div>

      </div>
      <div class="column">
        <div class="header">
          링크
        </div>
        <div class="body"  v-for="data in paperData">
          <a href="#" onClick="openWOSpage(data[1])">이</a>
        </div>

      </div>
      <div class="column">
        <div class="header">
          교신 저자
        </div>
        <div class="body" v-for="data in paperData">
          <p>{{data[2]}}</p>
        </div>

      </div>

      <div class="column">
        <div class="header">
          저자 상태
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          저널명
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          인용수
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          발행년월
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          페이지
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          저널명
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          등급
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          백분율
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          파싱 상태
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          UID
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
        </div>
      </div>
      <div class="column">
        <div class="header">
          DOI
        </div>
        <div class="body">
          data
        </div>
        <div class="body">
          data
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
      headers: {}
    }
  },
  methods: {
    openWOSpage(){

    }
  },

  mounted () {

    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('MEMBER_PAPER_ACTION',[0,3,5,6,7,8,9,10,15,17,18,1,2])
      .then(res => {
        this.paperData = this.$store.getters.MEMBER_PAPER_GETTER
        console.log(this.paperData)

      })
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
  }
}
</script>

<style scoped>
@import './allPaperListComponent.scss';

</style>
