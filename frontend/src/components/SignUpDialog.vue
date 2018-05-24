<template>
  <v-dialog v-model="show" max-width="600px">
    <v-card>
      <v-container fluid>
        <v-layout row wrap>
          <v-flex xs12 class="text-xs-center" mt-5>
            <h1>Sign up</h1>
          </v-flex>
        </v-layout>
        <v-flex xs12 sm6 offset-sm3 mt-3>
          <form @submit.prevent="userSignUp" :disabled="loading">
            <v-layout column>
              <v-alert type="error" dismissible v-model="alert">
                {{ error }}
              </v-alert>
              <v-flex>
                <v-text-field
                name="email"
                label="Email"
                id="email"
                type="email"
                v-model="email"
                required></v-text-field>
              </v-flex>
              <v-flex>
                <v-text-field
                name="password"
                label="Password"
                id="password"
                type="password"
                v-model="password"
                required></v-text-field>
              </v-flex>
              <v-flex>
                <v-text-field
                name="confirmPassword"
                label="confirmPassword"
                id="confirmPassword"
                type="password"
                v-model="passwordConfirm"
                :rules="[comparePasswords]"
                required></v-text-field>
              </v-flex>
              <v-flex>
                <v-text-field
                name="name"
                label="Name"
                id="name"
                v-model="name"
                ></v-text-field>
              </v-flex>
              <v-flex class="text-xs-center" mt-5 mb-5>
                <v-btn color="primary" type="submit">Sign Up</v-btn>
                <v-btn color="primary" @click.stop="show=false">Close</v-btn>
              </v-flex>
            </v-layout>
          </form>
        </v-flex>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
/* eslint-disable */
import * as toastr from 'toastr';
import router from '@/router'

  export default {
    data () {
      return {
        email: '',
        name: 'unknown',
        password: '',
        passwordConfirm: '',
        alert: false
      }
    },
    props: ['visible'],
    computed: {
      show: {
        get () {
          return this.visible
        },
        set (value) {
          this.$emit('close')
        }
      },
      comparePasswords () {
        return this.password === this.passwordConfirm ? true : '비밀번호가 일치하지 않습니다.'
      },
      error () {
        return this.$store.state.error
      },
      loading () {
        return this.$store.state.loading
      }
    },
    methods: {
      userSignUp () {
        if (this.comparePasswords !== true) {
          return
        }
        this.$store.dispatch('userSignUp', { email: this.email, password: this.password, name: this.name})
        if (this.$store.state.error === null) {
          this.email = ''
          this.password = ''
          this.passwordConfirm = ''
          this.name = 'unknown'
          this.show = false
        }
      }
    },
    watch: {
      error (value) {
        if (value) {
          this.alert = 'error'
        }
      },
      alert (value) {
        if (!value) {
          this.$store.commit('setError', null)
        }
      }
    }
  }
</script>
