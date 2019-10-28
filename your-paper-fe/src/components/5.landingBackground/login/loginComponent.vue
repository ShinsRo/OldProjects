<template>
  <div class="loginWrapper">
    <input class="formForId formElement" type="text" placeholder="ID">
    <input class="formForPassword formElement" type="password" placeholder="Password">
    <button class="buttonForLogin formElement" type="button">Login</button>
    <button class="buttonForSignUp formElement" type="button">SignUp</button>
  </div>
</template>

<script>
export default {
  name: 'login'
}
</script>

<style lang="scss">
  @import './loginComponent.scss';
</style>
