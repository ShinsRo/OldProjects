<template>
  <div class="wrapper">
    <form action="post">
      <input class="idInputBox" type="text" placeholder="ID">
      <input class="pwInputBox" type="password" placeholder="Password">
    </form>
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
