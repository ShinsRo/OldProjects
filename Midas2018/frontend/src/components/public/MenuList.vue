<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5 >
        <h1> Menu List </h1>
      </v-flex>
      <v-flex xs10 class="text-xs-right" v-if="isAuthenticated === 1 || isAuthenticated === 2">
        <v-card-text style="height: 50px; position: relative;">
          <v-btn
          absolute
          dark
          fab
          top
          right
          color="pink"
          @click.stop="showAddMenuDialog=true"
          >
          <v-icon>add</v-icon>
        </v-btn>
        <AddMenuDialog :visible="showAddMenuDialog" @close="showAddMenuDialog=false"></AddMenuDialog>
      </v-card-text>
      </v-flex>
      <v-flex v-for="(i, index) in menuList" :key="index" mt-5 pr-2>
        <v-card>
          <v-card-title primary-title>
            <div>
              <!--<img src="..."></img>-->
              <h3 class="headline mb-0">{{i.name}}</h3>
              <div>{{i.price}}<br>{{i.content}}</div>
            </div>
          </v-card-title>
          <v-card-actions>
            <!-- user 조작 버튼 -->
            <v-flex class="text-xs-right" v-if="isAuthenticated === 0">
              <v-icon @click.stop="addToCart(i)" medium style="cursor: pointer">add_shopping_cart</v-icon>
            </v-flex>
            <!-- admin 조작 버튼 -->
            <v-flex class="text-xs-right" v-if="isAuthenticated === 1 || isAuthenticated === 2">
              <v-icon @click="" medium style="cursor: pointer">note_add</v-icon>
              <v-icon @click="" medium style="cursor: pointer">delete_forever</v-icon>
            </v-flex>
            <!-- public 조작 버튼 -->
            <v-flex class="text-xs-right" v-if="isAuthenticated === 9">
              <v-icon @click="" medium style="cursor: pointer">public</v-icon>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-flex>
      <v-flex xs2 mt-5 pl-3 class="text-xs-center">
          <v-flex v-for="(item, index) in getCart" :key="index" elevation-2 mt-1 class="text-xs-left">
              <v-flex pl-1 pt-1> {{ item.name }} </v-flex>
          </v-flex>
      </v-flex>

    </v-layout>
    <!-- snackbar -->
  <v-snackbar
    :timeout="timeout"
    :top="true"
    :multi-line="mode === 'multi-line'"
    :vertical="mode === 'vertical'"
    v-model="snackbar"
  >
  {{ text }}
  <v-btn flat color="pink" @click.native="snackbar = false">Close</v-btn>
  </v-snackbar>
  </v-container>
</template>

<script>
/* eslint-disable */
import AddMenuDialog from '../admin/AddMenuDialog'
import menuApi from '../../common/js/menu-api';

  export default {
    data () {
      return {
        showAddMenuDialog: false,
        snackbar: false,
        mode: '',
        timeout: 1000,
        text: '주문이 추가 되었습니다. 주문 내역을 확인하세요',
        menu:{
          id:3,
          name: '',
          price: '',
          content: '',
          category: '',
          status: '',
          imageURL: ''
        },
        userCart: '',
        menuList: ''
      }
    },
    components: {
      AddMenuDialog
    },
    computed: {
      isAuthenticated () {
        // console.log(this.$store.getters.isAuthenticated)
        return this.$store.getters.isAuthenticated
      },
      getCart () {
        console.log(this.$store.getters.getCart)
        return this.$store.getters.getCart
      }

    },
    methods:{
      addToCart:function (i) {
        this.snackbar = true
        this.$store.dispatch('addToCart', i)
      },
      selectAll:function() {
        menuApi.selectAll().then((res) => {
          //console.log(`res : ${res}`);

          if (res.code >= 400) {
            throw new Error(res.message);
          }
          //this.$router.push('menu');

          this.menuList = res;
        }).fail((error) => {
          toastr.error(error.message, 'Oops!');
        })
      }
    },
    created () {
       this.selectAll()
    }
  }
</script>
