<template>
<div>
  <table>
    <thead>
    <tr>
      <th>
        no
      </th>
      <td>
        제목
      </td>
    </tr>
    </thead>
    <tbody>
    <tr v-for="paper in paperData">
      <td v-for="data in paper">
        {{data}}
      </td>
    </tr>


    </tbody>

  </table>


</div>
</template>

<script>
import { PaperRecordContainer, SORT_MP_ENUM } from '../../../../public/apis/api/paper-api.js'
export default {
  name: "allPaperListComponent",
  data(){
    return{
      paperData: [],
    }
  },
  mounted(){
    const username = 'data5000'
    const password = 'data5000'
    const SERVER_URL = 'http://www.siotman.com:19401/'
    const container = new PaperRecordContainer(username, password, SERVER_URL);

    container.listByPage(0, 10 ,SORT_MP_ENUM.TITLE, true)
      .then(res =>{
        const records = container.getRecords([3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])
        const headers = container.getHeaders([3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18])

        this.paperData = records
        console.log(this.paperData);
        console.log(headers)
      })
  }
}
</script>

<style scoped>
@import './allPaperListComponent.scss';


</style>
