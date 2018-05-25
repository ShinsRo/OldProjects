<template>
  <v-container fluid>
    <div>
      <v-flex xs12>
        <v-text-field box label="title" disabled v-model="board.title"> {{ board.title }} </v-text-field>
      </v-flex>
      <v-flex xs12>
        <v-text-field box multi-line label="Content" disabled v-model="board.content"> {{ board.content }} </v-text-field>
      </v-flex>
      <div>
        <v-btn color="primary">Edit</v-btn>
        <v-btn v-on:click="deleteBoard" color="primary">Delete</v-btn>
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
    props: {
      propBoard: {
        type: Object
      }
    },
    data() {
      return {
        board: {
          boardNo: '',
          title: '',
          content: '',
          userId: '',
          registerDate: ''
        }
      }
    },
    methods: {
      getUnits: function () {
        this.board = this.propBoard;
      },
      deleteBoard() {
        boardApi.deleteBoard(this.propBoard.boardNo).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          this.$router.push('board');

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      },
      beforeMount() {
        this.getUnits();
        console.log(this.board);
      }
    }
  }
</script>

<style>

</style>
