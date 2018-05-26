<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5 >
        <h1>Menu List</h1>
      </v-flex>
      <v-flex xs12 class="text-xs-right" v-if="isAuthenticated === 1 || isAuthenticated === 2">
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
      <v-flex v-for="i in 20" :key="`4${i}`" mt-5 pr-2>
        <v-card>
          <img src="@/assets/testimg.jpg" height="108px" width="192px">
          <v-card-title primary-title>
            <div>
              <h3 class="headline mb-0">Menu one</h3>
              <div>So ~ good. <br>choose it, right now!</div>
            </div>
          </v-card-title>
          <v-card-actions>
            <!-- user 조작 버튼 -->
            <v-flex class="text-xs-right" v-if="isAuthenticated === 0">
              <v-icon @click="snackbar = true" medium style="cursor: pointer">add_shopping_cart</v-icon>
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

export default {
    data () {
      return {
        showAddMenuDialog: false,
        snackbar: false,
        mode: '',
        timeout: 1000,
        text: '주문이 추가 되었습니다.'
      }
    },
    components: {
      AddMenuDialog
    },
    computed: {
      isAuthenticated () {
        // console.log(this.$store.getters.isAuthenticated)
        return this.$store.getters.isAuthenticated
      }
    }
  }
</script>
