<template>
  <div class="loginWrapper">
    <input class="formForId formElement" type="text" placeholder="ID">
    <input class="formForPassword formElement" type="password" placeholder="Password">
    <button class="buttonForLogin formElement" type="button">Login</button>
    <button class="buttonForSignUp formElement" type="button" v-on:click="clickForSignUp">SignUp</button>
  </div>
</template>

<script>
export default {
  name: 'login',
  methods: {
    clickForSignUp () {
      this.$emit('changeFlag')
    }
  }
}
</script>

<style lang="scss">
  @import './loginComponent.scss';
</style>
