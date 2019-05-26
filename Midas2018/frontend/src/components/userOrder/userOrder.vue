<template>
  <v-container fluid>
    <b-button variant="primary" class="text-right" @click="insertUserOrder">button</b-button>
    <div>
      {{userOrderVO}}
      {{userOrderVOList}}
    </div>
  </v-container>
</template>
<script>
/* eslint-disable */
import * as toastr from 'toastr';
import UserOrderApi from '../../common/js/user-order-api';

export default {
  comments: {
    UserOrderApi
  },
  data() {
    return {
      userOrderVOList:[],
      userOrderVO: {
        cafeMenuList: {
          cafeMenuAndOptionsList:[
          {
            cafeMenuId: '1',
            optionList: [
              {
                name: '1',
                count: '2',
                price: '3'
              }
            ]
          },
          {
            cafeMenuId: '2',
            optionList: [
              {
                name: '1',
                count: '2',
                price: '3'
              }
            ]
          }
        ]},
        totalPrice: '1',
        content: '2',
        status: '0'
      }
    }
  },
  methods: {
    insertUserOrder() {
      UserOrderApi.insertUserOrder(this.userOrderVO).then((res) => {
        console.log(`res : ${res}`);
        if (res.code >= 400) {
          throw new Error(res.message);
        }

      }).fail((error) => {
        toastr.error(error.message, 'Oops!');
      })
    }
    ,
    getUserOrderList() {
      UserOrderApi.getUserOrderList().then((res) => {
        console.log(`res ${res}`);
        if (res.code >= 400) {
          throw new Error(res.message);
        }
        this.userOrderVOList = res.data;

      }).fail((error) => {
        toastr.error(error.message, 'Oops!');
      })
    }
  },
  created() {
    this.getUserOrderList();
  }

}
</script>
