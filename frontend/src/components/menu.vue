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
        <v-btn v-on:click="updateMenu" color="primary">update</v-btn>
        <v-btn v-on:click="deleteMenu" color="primary">delete</v-btn>
        <v-btn v-on:click="selectMenu" color="primary">select</v-btn>
        <v-btn v-on:click="selectAll" color="primary">selectAll</v-btn>
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
          id:3,
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
      },

      updateMenu() {
        console.log(this.menu);
        menuApi.updateMenu(this.menu).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          //this.$router.push('menu');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      },
      deleteMenu() {
        console.log(this.menu.id);
        menuApi.deleteMenu(this.menu.id).then((res) => {
          console.log(`res : ${res}`);
          console.log(res);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          //this.$router.push('menu');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      },
      selectMenu() {
        console.log(this.menu.id);
        menuApi.selectMenu(this.menu.id).then((res) => {
          console.log(`res : ${res}`);
          console.log(res);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          //this.$router.push('menu');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      },
      selectAll() {
        menuApi.selectAll().then((res) => {
          console.log(`res : ${res}`);
          console.log(res);

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
