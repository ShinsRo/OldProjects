<template>
  <v-container fluid>
    <!--<v-btn href="boardwrite" color="primary">write</v-btn>-->
    <div>
      <v-dialog v-model="dialog" max-width="500px">
        <v-btn slot="activator" color="primary" dark class="mb-2">New User</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">{{ formTitle }}</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedUser.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedUser.email" label="Email"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-select v-model="editedUser.status" :items="userStatus" label="Status">{{editedUser.status}}
                  </v-select>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedUser.phoneNumber" label="PhoneNumber"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedUser.password" label="Password"></v-text-field>
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
      <v-card-title>
        totalUser : {{totalCount}}
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="search"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="userList"
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
          <td class="text-xs-center" v-on:click="selectOne(props.item)">{{ props.item.id }}</td>
          <td class="text-xs-center">{{ props.item.name }}</td>
          <td class="text-xs-center">{{ props.item.email }}</td>
          <td class="text-xs-center">{{ props.item.status }}</td>
          <td class="text-xs-center">{{ props.item.phoneNumber }}</td>
          <td class="text-xs-center">{{ props.item.createdTime | convertDateTime(props.item.createdTime) }}</td>
          <td class="justify-center layout px-0">
            <v-btn icon class="mx-0" @click="editItem(props.item)">
              <v-icon color="teal">edit</v-icon>
            </v-btn>
            <v-btn icon class="mx-0" @click="deleteItem(props.item)">
              <v-icon color="pink">delete</v-icon>
            </v-btn>
          </td>
        </template>

        <v-alert slot="no-results" :value="true" color="error" icon="warning">
          Your search for "{{ search }}" found no results.
        </v-alert>

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
import UserApi from '../../common/js/user-api';

export default {
  comments: {
    UserApi,
  },
  data() {
    return {
      dialog: false,
      search: '',
      pagination: {
        rowsPerPage: 30
      },
      selected: [],
      headers: [
        {text: 'Id', value: 'id', align: 'center'},
        {text: 'Name', value: 'name', align: 'center'},
        {text: 'Email', value: 'email', align: 'center'},
        {text: 'Status', value: 'status', align: 'center'},
        {text: 'PhoneNumber', value: 'phoneNumber', align: 'center'},
        {text: 'createdTime', value: 'created_time', align: 'center'},
        {text: 'Actions', align: 'center'}
      ],
      userList: [],
      editedIndex: -1,
      editedUser: {
        id: '',
        name: '',
        email: '',
        status: 'USER',
        phoneNumber: '',
        password: '1234512345'
      },
      defaultItem: {
        name: '',
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0
      },
      totalCount: 0,
      userStatus:
        [{text: 'USER', value: 0}, {text: 'USB_ADMIN', value: 1}, {text: 'ADMIN', value:2}],

    }
  },
  methods: {
    getUserListAll() {
      UserApi.getUserListAll().then((res) => {
        console.log(`res ${res}`);
        if (res.code >= 400) {
          throw new Error(res.message);
        }
        this.userList = res.data;
        this.totalCount = this.userList.length;
      }).fail((error) => {
        toastr.error(error.message, 'Oops!');
      })
    },
    editItem(item) {
      console.log(`item ${item.status}`);
      this.editedIndex = this.userList.indexOf(item)
      console.log(`editedUser ${this.editedUser.status}`);
      this.editedUser = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem(item) {
      // delete 호출
      const index = this.userList.indexOf(item)
      const user = this.userList[index];
      if (!confirm('Are you sure you want to delete this user?')) {
        return ;
      }

      UserApi.deleteUser(user).then((res) => {
        console.log(`res ${res}`);
        if (res.code >= 400) {
          throw new Error(res.message);
        }
        user.status = 'DELETE';
        Object.assign(this.userList[index], user);
        this.init();
      }).fail((error) => {
        toastr.error(error.message, 'Oops!');
        this.init();
      })
    },
    close() {
      console.log(`test!!!`);
      this.dialog = false;
    },
    init() {
      this.editedUser = Object.assign({}, this.userList)
      this.editedIndex = -1
    },
    save() {
      if (this.editedIndex > -1) {
        //update 호출
        console.log(`updateUser : ${this.editedUser.phoneNumber}`);
        UserApi.updateUser(this.editedUser).then((res) => {
          console.log(`res ${res}`);
          if (res.code >= 400) {
            throw new Error(res.message);
          }
          console.log(`this.editedUser : ${this.editedUser}`)
          console.log(`this.editedIndex: ${this.editedIndex}`)
          Object.assign(this.userList[this.editedIndex], this.editedUser);
          this.init();
        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
          this.init();
        })
      } else {
        // create 호출
        const user = this.editedUser;
        UserApi.insertUser(user).then((res) => {
          console.log(`res ${res}`);
          if (res.code >= 400) {
            throw new Error(res.message);
          }
          this.userList.push(this.editedUser);
          this.init();
        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
          this.init();
        })
      }
      this.close()
    }
  },
  created() {
    this.getUserListAll();
  },
  computed: {
    pages() {
      if (this.pagination.rowsPerPage == null ||
          this.pagination.totalItems == null
      ) {
        return 0
      }

      return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage)
    },
    formTitle() {
      return this.editedIndex === -1 ? 'New User' : 'Edit User'
    }
  },
  watch: {
    dialog(val) {
      val || this.close()
    }
  },
}
</script>
