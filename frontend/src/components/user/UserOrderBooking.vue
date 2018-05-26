<template>
  <v-container>
    <div>
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
                <v-flex xs12 md4>
                  <v-flex v-for="orderList in userOrderList">
                    <v-flex v-for="cafeMenu in orderList.cafeMenuList">
                      <v-flex v-for="(cafeMenuAndOptions) in cafeMenu">
                        {{cafeMenuAndOptions.cafeMenuId}}
                        <v-flex v-for="optionList in cafeMenuAndOptions.optionList">
                          {{optionList.name}} / {{optionList.count}} / {{optionList.price}}
                        </v-flex>
                      </v-flex>
                    </v-flex>
                  </v-flex>
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
        :items="userOrderList"
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
          <td>{{ props.item.status }}</td>
          <td>{{ props.item.createdTime | convertDateTime(props.item.createdTime) }}</td>
          <td class="justify-center layout px-0">
            <v-btn icon class="mx-0" :disabled="props.item.status !== 'WAITING'" @click="editItem(props.item)">
              <v-icon color="teal">edit</v-icon>
            </v-btn>
            <v-btn icon class="mx-0" :disabled="props.item.status !== 'WAITING'" @click="deleteItem(props.item)">
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
        { text: 'state', value: 'status' },
        { text: 'datetime', value: 'datetime' },
        { text: 'Actions' }
      ],
      userOrderList: [],
      orderList:[],
      cafeMenuList:{},
      cafeMenuAndOptionsList:[],
      cafeMenuIdList:[],
      optionList:[],
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
      this.getUserOrderListBookingByUserId();
      // this.initialize()
    },

    methods: {
      initialize () {
        this.userOrderList = []
      },
      getUserOrderListBookingByUserId: function() {
        UserOrderApi.getUserOrderListBookingByUserId(2).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }
          console.log(res.data);

          this.userOrderList = res.data;
          for (let i = 0 ; i < this.userOrderList.length ; ++i) {
            console.log(`userOrderList ${this.userOrderList[i]}`);
            this.cafeMenuList = this.userOrderList[i].cafeMenuList;
            console.log(`cafeMenuList ${this.cafeMenuList}`);
            this.cafeMenuAndOptionsList = this.cafeMenuList.cafeMenuAndOptionsList;
            for (let j = 0 ; j < this.cafeMenuAndOptionsList.length ; ++j) {
              console.log(`cafeMenuAndOptionsList ${this.cafeMenuAndOptionsList[j]}`);
              }
            }

          console.log(this.cafeMenuIdList);
          console.log(this.optionList);

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
        this.editedIndex = this.userOrderList.indexOf(item)
        this.orderList = Object.assign({}, item)
        this.dialog = true
      },
      deleteItem (item) {
        const index = this.userOrderList.indexOf(item)
        if (!confirm('Did you finish this order?')) {
          return;
        }
        const userOrderVO = this.userOrderList[index];
        UserOrderApi.deleteUserOrder(userOrderVO).then((res) => {
          console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }
          console.log(res.data);
          this.userOrderList.splice(index, 1)
        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
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
          Object.assign(this.userOrderList[this.editedIndex], this.editedItem)
        } else {
          this.userOrderList.push(this.editedItem)
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
