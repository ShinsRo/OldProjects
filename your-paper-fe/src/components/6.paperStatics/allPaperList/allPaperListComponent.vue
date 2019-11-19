<template>
<div>
  <table>
    <thead>
    <tr>
      <td>
        {{this.headers[0]}} <!--행-->
      </td>
      <td>
        {{this.headers[3]}} <!--제목-->
      </td>
      <td>
        {{this.headers[5]}} <!--교신저자-->
      </td>
      <td>
        {{this.headers[6]}} <!--저자 상태-->
      </td>
      <td>
        {{this.headers[7]}} <!--저널명-->
      </td>
      <td>
        {{this.headers[8]}} <!--인용수-->
      </td>
      <td>
        {{this.headers[9]}} <!--발행년월-->
      </td>
      <td>
        권, 호, 페이지
      </td>
      <td>
        {{this.headers[10]}} <!--저널명-->
      </td>
      <td>
        {{this.headers[15]}} <!--등급-->
      </td>
      <td>
        {{this.headers[17]}} <!--백분율-->
      </td>
      <td>
        {{this.headers[18]}} <!--파싱 상태-->
      </td>
      <td>
        {{this.headers[1]}} <!--UID-->
      </td>
      <td>
        {{this.headers[2]}} <!--DOI-->
      </td>

    </tr>
    </thead>
    <tbody >
    <tr v-for="datas in paperData">
      <td>
        {{datas[0]}}
      </td>
      <td>
        {{datas[3]}}
      </td>
    </tr>

    </tbody>

  </table>

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
  mounted () {
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
      })
  }
}
</script>

<style scoped>
@import './allPaperListComponent.scss';

</style>
