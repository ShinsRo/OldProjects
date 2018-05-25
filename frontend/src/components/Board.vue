<template>
  <v-container fluid>
    <v-btn href="boardwrite" color="primary">write</v-btn>
    <div>
      <v-data-table
        :headers="headers"
        :items="list"
        :search="search"
        :pagination.sync="pagination"
        hide-actions
        class="elevation-1"
      >
        <template slot="headerCell" slot-scope="props">
          <v-tooltip bottom>
          <span slot="activator"> {{ props.header.text }} </span>
          </v-tooltip>
        </template>
        <template slot="items" slot-scope="props">
          <td hidden>{{ props.item.boardNo }}</td>
          <td class="text-xs-center" v-on:click="selectOne(props.item)">{{ props.item.boardNo }}</td>
          <td class="text-xs-center" v-on:click="selectOne(props.item)">{{ props.item.title }}</td>
          <td class="text-xs-center" v-on:click="selectOne(props.item)">{{ props.item.userId }}</td>
          <td class="text-xs-center" v-on:click="selectOne(props.item)">{{ props.item.registerDate }}</td>
        </template>
      </v-data-table>
      <div class="text-xs-center pt-2">
        <v-pagination v-model="pagination.page" :length="pages"></v-pagination>
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
    data () {
      return {
        search: '',
        pagination: {},
        selected: [],
        headers: [
          { text: 'No', align: 'center', sortable: false },
          { text: 'title', align: 'center', sortable: false },
          { text: 'user ID', align: 'center', sortable: false },
          { text: 'register date', align: 'center', sortable: false }
        ],
        list: [],
      }
    },
    methods: {
      selectOne(board) {
        console.log(board);
        this.$router.push({
          name: 'boardRead',
          params: {
            propBoard: board
          }
        });
      },
      getUnits: function() {
        boardApi.selectAllBoard(this.board).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }

          this.list = res;

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      }
    },
    beforeMount() {
      this.getUnits();
    },
    computed: {
      pages () {
        if (this.pagination.rowsPerPage == null ||
          this.pagination.totalItems == null
        ) return 0

        return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage)
      }
    },
  }
</script>

<style>

</style>
