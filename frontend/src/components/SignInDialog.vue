<template>
  <v-dialog v-model="show" max-width="600px">
      <v-card>
        <v-container fluid>
          <v-flex xs12 class="text-xs-center" mt-5>
            <h1>Sign In</h1>
          </v-flex>
          <v-layout row wrap>
            <v-flex xs12 sm6 offset-sm3 mt-3>
              <form @submit.prevent="userSignIn">
                <v-layout column>
                  <v-flex>
                    <v-text-field
                    name="email"
                    label="Email"
                    id="sign-in-email"
                    type="email"
                    v-model="email"
                    required></v-text-field>
                  </v-flex>
                  <v-flex>
                    <v-text-field
                    name="password"
                    label="Password"
                    id="sign-in-password"
                    type="password"
                    v-model="password"
                    required></v-text-field>
                  </v-flex>
                  <v-flex class="text-xs-center" mt-5>
                    <v-btn color="primary" type="submit" :disabled="loading">Sign In</v-btn>
                  </v-flex>
                </v-layout>
              </form>
            </v-flex>
          </v-layout>
        </v-container>
      </v-card>
  </v-dialog>
</template>

<script>
export default {
  data () {
    return {
      email: '',
      password: '',
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
    error () {
      return this.$store.state.error
    },
    loading () {
      return this.$store.state.loading
    }
  },
  methods: {
    userSignIn () {
      this.$store.state.usingModal = true
      this.$store.dispatch('userSignIn', {
        email: this.email,
        password: this.password
      }).then((resolv) => {
        this.email = ''
        this.password = ''
        this.show = false
      }).catch((refus) => {
      })
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
