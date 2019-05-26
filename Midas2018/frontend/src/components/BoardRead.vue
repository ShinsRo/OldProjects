<template>
  <v-container fluid>
    <div>
      <v-flex xs12>
        <v-text-field box label="title" :disabled="isDisabled" v-model="propBoard.title"> {{ propBoard.title }} </v-text-field>
      </v-flex>
      <v-flex xs12>
        <v-text-field box multi-line label="Content" :disabled="isDisabled" v-model="propBoard.content"> {{ propBoard.content }} </v-text-field>
      </v-flex>
      <div>
        <v-btn v-on:click="editBoard" color="primary">Edit</v-btn>
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
        },
        isDisabled: true
      }
    },
    methods: {
      getUnits: function () {
        this.board = this.propBoard;
      },
      beforeMount() {
        this.getUnits();
        console.log(this.board);
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
      editBoard(){
        this.isDisabled = !this.isDisabled;

        if(this.isDisabled) {
          console.log(this.propBoard);
          console.log(this.board);

          boardApi.updateBoard(this.propBoard).then((res) => {
            console.log(`res : ${res}`);

            if (res.code >= 400) {
              throw new Error(res.message);
            }
          }).fail((error) => {
            toastr.error(error.message, 'Oops!');
          })
        }
      }
    }
  }
</script>

<style>

</style>
