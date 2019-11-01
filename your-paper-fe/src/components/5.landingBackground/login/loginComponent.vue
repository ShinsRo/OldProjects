<template>
  <div class="loginWrapper">
    <input class="formForId formElement" type="text" placeholder="ID" v-model="customer.id" v-on:keyup.enter="clickForLogin">
    <input class="formForPassword formElement" type="password" placeholder="Password" v-model="customer.password" v-on:keyup.enter="clickForLogin">
    <p class="loginErrorMessage">{{ wrongMessage }}</p>
    <button class="buttonForLogin formElement" type="button" v-on:click="clickForLogin">Login</button>
    <button class="buttonForSignUp formElement" type="button" v-on:click="clickForSignUp">SignUp</button>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      customer: {
        id: '',
        password: ''
      },
      wrongMessage: ''
    }
  },
  methods: {
    clickForSignUp () {
      this.wrongMessage = ''
      this.$emit('changeFlag')
    },
    clickForLogin () {
      if (this.customer.id === '' || this.customer.password === '') {
        this.wrongMessage = '로그인 정보를 입력해주세요'
      } else if (this.customer.id !== '' && this.customer.password !== '') {
        this.$axios({
          method: 'POST',
          url: 'http://172.16.21.6:9401/auth/login',
          data: {
            'username': this.customer.id,
            'password': this.customer.password
          },
          header: { Authorization: `Basic ${this.customer.id}:${this.customer.password}` }
        }).then(res => {
          this.$router.push('/main')
        }).catch(error => {
          console.log(error)
          this.wrongMessage = '아이디와 비밀번호를 확인해주세요'
        })
      }
    }
  }
}
</script>

<style lang="scss">
  @import './loginComponent.scss';
</style>
