<template>
  <v-flex xs12 mt-255>
    <v-btn @click="commonPOST">POST</v-btn>
    <v-btn @click="commonGET" type="submit">GET</v-btn>
  </v-flex>
</template>


<script>
/* eslint-disable */
import Form from 'form-data'

export default {
  data (){
    return {
      getSomething: "",
      postSomething: ""
    }
  },
  methods: {
    commonGET :
      this.$store.dispatch( 'commonGET',
                            'axiosDemo/getSomething',
                            {
                              param1 : '1',
                              param2 : '2'
                            }
    ).then((response) => {
      alert(response)
    }).catch((response) => {
      alert(response)
    }),
    commonPOST :
      this.$store.dispatch( 'commonPOST',
                            'axiosDemo/postSomething',
                            new Form()
                            .append('param1', 1)
                            .append('param2', 2)
    ).then((response) => {

    }).catch((response) => {

    })
  }
}

</script>
