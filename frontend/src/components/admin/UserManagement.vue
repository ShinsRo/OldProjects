<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>Menu Management</h1>
      </v-flex>
      <v-flex xs12 class="text-xs-center" mt-3>
        <p>you can select here</p>
        <v-btn @click.stop="getUserList">asd</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
/* eslint-disable */
  export default {
    data () {
      return {
        pageNo: 1
      }
    },
    computed: {
      getUserList (pageNo) {
        let payload = {
          url: 'http://localhost:8888/api/admin/getUserList',
          params: {
            pageNo: pageNo
          }
        }
        this.$store.dispatch('commonGET', payload).then((response) => {
          console.log('in vue : ' + response)
        }).catch((error) => {
          console.log('in vue error : ' + error);
        })
      }
    }
  }
</script>
