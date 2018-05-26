<template>
  <v-container fluid>
    <h1>test</h1>
    <div>
      <v-flex xs12>
        <v-text-field v-model="menu.name" box></v-text-field>
        <v-text-field v-model="menu.price" box></v-text-field>
        <v-text-field v-model="menu.content" box></v-text-field>
        <v-text-field v-model="menu.category" box></v-text-field>
        <v-text-field v-model="menu.status" box ></v-text-field>
        <v-text-field v-model="menu.imageURL" box></v-text-field>
      </v-flex>
      <div>
        <v-btn v-on:click="insertMenu" color="primary">Submit</v-btn>
      </div>
    </div>
  </v-container>
</template>


<script>
  /* eslint-disable */
  import * as toastr from 'toastr';
  import menuApi from '../common/js/menu-api';

  export default {
    comments: {
      menuApi,
    },

    data() {
      return {
        menu:{
          name: '',
          price: '',
          content: '',
          category: '',
          status: '',
          imageURL: ''
        }
      }
    },
    methods: {
      insertMenu() {
        console.log(this.menu);
        menuApi.insertMenu(this.menu).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          //this.$router.push('menu');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      }
    }
  };
</script>

<style>

</style>
