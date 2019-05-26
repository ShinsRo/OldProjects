<template>
  <v-container fluid>
    <v-flex xs12 class="text-xs-center" mt-5>
      <h1>Sign In</h1>
    </v-flex>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3 mt-3>
        <!--<form @submit.prevent="userSignIn">-->
          <v-layout column>
            <v-flex>
              <v-alert type="error" dismissible v-model="alert">
                {{ error }}
              </v-alert>
            </v-flex>
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
            <v-flex class="text-xs-center" mt-5>
              <v-btn color="primary" @click="signin" :disabled="loading">Sign In</v-btn>
            </v-flex>
          </v-layout>
        <!--</form>-->
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import UserApi from '../../common/js/user-api'
import router from '@/router'

export default {
  comments: {
    UserApi,
  },
  data () {
    return {
      email: '',
      password: '',
      alert: false
    }
  },
  methods: {
    userSignIn () {
      this.$store.dispatch('userSignIn', { email: this.email, password: this.password })
    },
    signin() {
      UserApi.signin({ email: this.email, password: this.password }).then((res) => {
        if (res.code >= 400) {
          throw new Error();
        }
        const user = res.data;

        if (user.status === 'USER') user.auth = 0
        else if (user.status === 'SUB_ADMIN') user.auth = 1
        else if (user.status === 'ADMIN') user.auth = 2
        this.$store.commit('setUser', user);
        switch (user.auth) {
          case 0:
            router.push('/home')
            break
          case 1:
          case 2:
            router.push('/admin/home')
            break
          default : throw new Error('알 수 없는 사용자')
        }


      }).fail((error) => error.message);
    }
  },
  computed: {
    error () {
      return this.$store.state.error
    },
    loading () {
      return this.$store.state.loading
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
