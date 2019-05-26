<template>
      <v-container fluid>
        <v-layout row wrap>
          <v-flex v-if="getCart.length === 0" xs12 sm6 offset-sm3 mt-5 class="text-xs-center">
            <h1>Your Cart is empty</h1>
          </v-flex>
          <v-flex v-else xs12 class="text-xs-center" mt-5>
            <h1>Your Cart</h1>
          </v-flex>
        </v-layout>
        <v-flex xs12 sm6 offset-sm3 mt-3>
          <form @submit.prevent="true" :disabled="loading">
            <v-layout row v-for="(item, index) in getCart" xs12 class="text-xs-center">
              <v-flex>
                <div>
                  Menu : {{ item.name }}
                </div>
              </v-flex>
              <v-flex>
                <div align="right">
                  Quantity :
                  {{ quantity[index] }}
                </div>
              </v-flex>
            </v-layout>
            <v-flex class="text-xs-center" mt-5>
              Total Price : {{ getTotal }}
            </v-flex>
            <v-flex v-if="getCart.length !== 0" class="text-xs-center" mt-5 mb-5>
              <v-btn color="primary" type="submit">Order It</v-btn>
              <v-btn color="primary" @click.stop="">Refresh</v-btn>
            </v-flex>
          </form>
        </v-flex>
      </v-container>
</template>

<script>
/* eslint-disable */
import * as toastr from 'toastr';
import router from '@/router'
import Form from 'form-data'

  export default {
    data () {
      return {
        passwordConfirm: '',
        loading: '',
        alert: false,
        totalPrice: 0,
        quantity: []
      }
    },
    created () {
      let i;
      for(i = 0; i < this.$store.getters.getCart.length; i++)
        this.quantity.push(1)

    },
    computed: {
      getQuantity () {
        return this.quantity
      },
      isAuthenticated () {
        // console.log(this.$store.getters.isAuthenticated)
        return this.$store.getters.isAuthenticated
      },
      getCart () {
        console.log(`getChar ${this.$store.getters.getCart}`)
        return this.$store.getters.getCart
      },
      getTotal () {
        let cart = this.$store.getters.getCart
        let i, total = 0
        for (i = 0; i < cart.length; i++) {
          total = total + cart[i].price;
        }
        return total
      }
    },
    methods: {
      addQuantity (i) {
        this.quantity[i]++
        return 0
      }
    }
  }
</script>
