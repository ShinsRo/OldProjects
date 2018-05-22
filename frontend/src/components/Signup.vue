<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>Sign up</h1>
      </v-flex>
    </v-layout>
    <v-flex xs12 sm6 offset-sm3 mt-3>
      <form @submit.prevent="userSignUp" :disabled="loading">
        <v-layout column>
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
          <v-flex class="text-xs-center" mt-5>
            <v-btn color="primary" type="submit">Sign Up</v-btn>
          </v-flex>
        </v-layout>
      </form>
    </v-flex>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        email: '',
        password: '',
        passwordConfirm: ''
      }
    },
    computed: {
      comparePasswords () {
        return this.password === this.passwordConfirm ? true : '비밀번호가 일치하지 않습니다.'
      }
    },
    methods: {
      userSignUp () {
        if (this.comparePasswords !== true) {
          return
        }
        this.$store.dispatch('userSignUp', { email: this.email, password: this.password })
      }
    }
  }
</script>
