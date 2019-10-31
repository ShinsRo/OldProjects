<template>
  <div class="loginWrapper">
    <input class="formForId formElement" type="text" placeholder="ID" v-model="customer.id">
    <input class="formForPassword formElement" type="password" placeholder="Password" v-model="customer.password">
    <!-- <p class="inputErrorMessage">{{ wrongMessage.inputError }}</p> -->
    <p class="loginErrorMessage">{{ wrongMessage.loginError}}</p>
    <button class="buttonForLogin formElement" type="button" v-on:click="clickForLogin">Login</button>
    <button class="buttonForSignUp formElement" type="button" v-on:click="clickForSignUp">SignUp</button>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      customer : {
        id : '',
        password : ''
      },
      wrongMessage : {
        inputError : '',
        loginError : ''
      }
    }
  },
  methods: {
    clickForSignUp () {
      this.$emit('changeFlag')
    },
    clickForLogin () {
      if(this.customer.id !== '' && this.customer.password !== '') {
        this.$router.push('/main')
      } else {
        this.wrongMessage.loginError = '아이디와 비밀번호를 확인해주세요'
      }
    }
  }
}
</script>

<style lang="scss">
  @import './loginComponent.scss';
</style>
