<template>
  <div class="createWrapper">
    <div class="idContainer">
      <input class="inputForId" type="text" placeholder="ID">
      <button class="checkForId" type="button">ID 중복확인</button>
    </div> <!-- id container-->
    <div class="passwordContainer">
      <input class="inputForPassword" type="password" placeholder="Password">
      <p class="invalidPassword"></p>
      <input class="checkForPassword" type="password" placeholder="Confirm Password">
      <p class="differentPassword"></p>
    </div> <!-- password container-->
    <div class="authorContainer">
      <input class="inputForName" type="text" placeholder="Name">
      <input class="inputForAuthorName" type="text" placeholder="Author Name">
      <div class="inputForOrganization">
        <input class="organization" type="text" placeholder="Organization">
        <button class="buttonForAdd" type="button">+</button>
      </div>
      <textarea class="conditions" cols="30" rows="5" readonly="readonly">본 서비스를 이용하기 위한 약관</textarea>
      <p class="checkForCondition"><input type="checkbox">Agree</p>
    </div> <!-- author container-->
    <div class="buttonContainer">
      <button class="buttonForCreate" type="button">Create</button>
      <button class="buttonForBack" type="button">Cancel</button>
    </div> <!-- button container-->
  </div>
</template>

<script>
export default {
  name: 'create'
}
</script>

<style lang="scss">
  @import './createComponent.scss';
</style>
