<template>
  <div id="viewMyPaperComponentLayout">
    <div>
      <div class="mainOptionFilter optionCheck">
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.authorStatus" type="checkbox"/>
          <p class="text">
            저자 상태
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.loadStatus" type="checkbox"/>
          <p class="text">
            논문 상태
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.quotation" type="checkbox"/>
          <p class="text">
            피 인용수
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.pages" type="checkbox"/>
          <p class="text">
            권, 호, 페이지
          </p>
        </div>

        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.url" type="checkbox"/>
          <p class="text">
            URL
          </p>
        </div>
      </div>

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
    </div>
    <paperDataComponent class="paperComponentLayout"
    :view-toggle="viewToggle"
    v-for="(paper, index) in paperData" :key="index" :paper="paper"></paperDataComponent>
  </div>
</template>

<script>
import { PaperRecordContainer, SORT_MP_ENUM } from '../../../../public/apis/api/paper-api.js'
import paperDataComponent from './paperData/paperDataComponent.vue'
export default {
  name: 'MainList',
  components: {
    'paperDataComponent': paperDataComponent
  },
  data () {
    return {
      viewToggle: {
        authorStatus: true,
        loadStatus: true,
        quotation: true,
        pages: true,
        url: true
      },
      paperData: {}.constructor,
      loading: 0,

    }
  },
  mounted() {
    this.$store.dispatch('MEMBER_OBJECT_SET_ACTION')
    this.$store.dispatch('MEMBER_PAPER_ACTION',[3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])
      .then(res =>{
        this.loading = 1;
      })
  },
    //          0    1      2      3     4
    //         '행', 'UID', 'DOI', '제목', '링크',
    //          5        6          7      8
    //         '교신저자', '저자 상태', '저자', '인용수',
    //          9        10      11   12    13
    //         '발행년월', '저녈명', '권', '호', '페이지',
    //          14        15     16    17       18
    //         '월별피인용', '등급', 'IF', '백분율', '파싱 상태',

    /*
    const token = sessionStorage.getItem('token')
    const session = JSON.parse(sessionStorage.getItem('data'))

    const username = session.username
    const authorization = token
    const SERVER_URL = 'http://www.siotman.com:19401/'
    // console.log(username, authorization)
    const container = new PaperRecordContainer(username, authorization, SERVER_URL)

    let save = this.$store.dispatch('MEMBER_PAPER_ACTION')
    console.log(save);
*/
/*
    container.listByPage(0, 10, SORT_MP_ENUM.TITLE, true)
      .then(res => {
        const records = container.getRecords([3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])
        // const headers = container.getHeaders([3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])

        this.paperData = records
        // console.log(this.paperData)
        // console.log(headers)
      })*/

    /* this.$axios.post('http://172.16.21.6:9401/myPaper/list', {
      username: 'admin' },
    {
      headers: {
        'Authorization': 'Basic YWRtaW46YWRtaW4=',
        'Content-Type': 'application/json'
      } })
      .then(response => {
        this.paperData = response.data
      })
  }*/
    computed:{
      isLoading(){
        return this.$store.getters.MEMBER_PAPER_GETTER
      }
    },
    watch:{
      isLoading(newVal, oldVal){
        this.paperData = this.$store.getters.MEMBER_PAPER_GETTER
        console.log('watch',this.paperData);
      }
    }
}

</script>

<style lang="scss">
  @import './viewMyPaperComponent.scss';

</style>
