<template>
  <v-container fluid>
    <b-button variant="primary" class="text-right" @click="getUserById">button</b-button>
    <div>
      {{user}}
    </div>
  </v-container>
</template>


<script>
import * as toastr from 'toastr';
import DemoApi from '../../common/js/demo-api';

export default {
  comments: {
    DemoApi,
  },
  data() {
    return {
      user: null
    }
  },
  methods: {
    getUserById() {
      DemoApi.getUserById(1).then((res) => {
        console.log(`res : ${res}`);

        if (res.code >= 400) {
          throw new Error(res.message);
        }

        this.user = res.data;

      }).fail((error) => {
        toastr.error(error.message, 'Oops!');
      })
    }
  }
};
</script>

<style>

</style>
