<template>
  <v-container fluid>
    <div>
      <v-flex xs12>
        <v-text-field
          v-model="board.title"
          box
          label="title"
          persistent-hint
        ></v-text-field>
      </v-flex>
      <v-flex xs12>
        <v-text-field v-model="board.content" box multi-line label="Content"></v-text-field>
      </v-flex>
      <div>
        <v-btn v-on:click="insertBoard" color="primary">Submit</v-btn>
        <v-btn href="board" color="primary">Cancel</v-btn>
      </div>
    </div>
  </v-container>
</template>


<script>
  /* eslint-disable */
  import * as toastr from 'toastr';
  import boardApi from '../common/js/board-api';

  export default {
    comments: {
      boardApi,
    },
    data() {
      return {
        board:{
          title:'',
          content:''
        }
      }
    },
    methods: {
      insertBoard() {
        boardApi.insertBoard(this.board).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          this.$router.push('board');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      }
    }
  };
</script>

<style>

</style>
