<template>
  <div class="wrapper">
    <div class="inputContainer">
      <input class="loginBox idInputBox" type="text" placeholder="ID">
      <input class="loginBox pwInputBox" type="password" placeholder="Password">
    </div>
    <div class="buttonContainer">
      <button class="BtnBox LoginBtn" type="button">Login</button>
      <button class="BtnBox SignUpBtn" type="button">SignUp</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'login'
}
</script>

<style lang="scss">
  @import './LoginComponent.scss';
</style>
