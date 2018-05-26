<template>
  <v-container>
    <div>
      <button v-on:click="modify"> modify </button>
      <v-flex xs12 class="text-xs-center" mt-5 >
        <h1>Order List</h1>
      </v-flex>
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline"> Order List </span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-flex v-for="orders in orderList.cafeMenuList">
                    <v-flex v-for="order in orders">
                      {{order.optionList}}
                    </v-flex>
                    </v-flex>
                  <v-text-field v-model="orderList" :disabled="isDisabled" label="no"> {{}}</v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="orderList" :disabled="isDisabled" label="order">{{ editedItem.no }}</v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.order" :disabled="isDisabled" label="Fat (g)"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem" :disabled="isDisabled" label="Carbs (g)"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem" :disabled="isDisabled" label="Protein (g)"></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click.native="close">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click.native="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-data-table
        :headers="headers"
        :items="list"
        :search="search"
        :pagination.sync="pagination"
        hide-actions
        class="elevation-1"
      >
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td>{{ props.item.totalPrice }}</td>
          <td>{{ props.item.userId }}</td>
          <td>{{ props.item.content }}</td>
          <td>{{ props.item.createdTime }}</td>
          <td>{{ props.item.status }}</td>
          <td class="justify-center layout px-0">
            <v-btn icon class="mx-0" @click="editItem(props.item)">
              <v-icon color="teal">edit</v-icon>
            </v-btn>
            <v-btn icon class="mx-0" @click="deleteItem(props.item)">
              <v-icon color="pink">delete</v-icon>
            </v-btn>
          </td>
        </template>
        <template slot="no-data">
          <v-btn color="primary" @click="initialize">Reset</v-btn>
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
  import UserOrderApi from '../../common/js/user-order-api';

  export default {
    comments:{
      UserOrderApi,
    },
    data: () => ({
      isDisabled: true,
      pagination: {},
      search:'',
      selected: [],
      dialog: false,
      lists:[[1,2,3],[2,3,4]],
      another_list:[[213,123, {hello:'sdf'}], 12, 123],
      headers: [
        { text: 'no', value: 'no' },
        { text: 'order', value: 'order' },
        { text: 'userName', value: 'userName' },
        { text: 'content', value: 'content' },
        { text: 'datetime', value: 'datetime' },
        { text: 'state', value: 'status' },
        { text: '', value: '' }
      ],
      list: [],
      orderList:[],
      editedIndex: -1,
      editedItem: {
        no: '',
        order: {},
      },
      defaultItem: {
        no: 0,
        order: {},
      },
    }),

    watch: {
      dialog (val) {
        val || this.close()
      }
    },

    created () {
      console.log('created');
      this.getAllOrders();
      this.initialize()
    },

    methods: {
      initialize () {
        this.list = []
      },
      getAllOrders: function() {
        UserOrderApi.getUserOrderLisAll().then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }
          console.log(res.data);

          this.list = res.data;

        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      },
      modify: function() {
        console.log(this.lists)
        this.lists[0].splice(2,2,3)
        console.log(this.lists)
      },
      editItem (item) {
        this.orderList = item.cafeMenuList;
        this.editedIndex = this.list.indexOf(item)
        this.orderList = Object.assign({}, item)
        this.dialog = true

      },

      deleteItem (item) {
        const index = this.list.indexOf(item)
        confirm('Did you finish this order?') && this.list.splice(index, 1)
      },

      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.list[this.editedIndex], this.editedItem)
        } else {
          this.list.push(this.editedItem)
        }
        this.close()
      }
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
