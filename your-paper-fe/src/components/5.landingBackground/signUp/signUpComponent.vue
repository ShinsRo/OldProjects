<template>
  <div class="signUpWrapper">
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
      <div class="inputForAuthorName">
        <input class="AuthorName" type="text" placeholder="Author Name">
        <button class="buttonForAuthorName" type="button">+</button>
      </div>
      <div class="selectBoxForOrganization">
        <select class="organization">
          <option value="sejongUniv" selected>세종대학교</option>
          <option value="konkukUniv">건국대학교</option>
          <option value="seoulUniv">서울대학교</option>
        </select>
        <!-- <input class="organization" type="text" placeholder="Organization">
        <button class="buttonForOrganization" type="button">▽</button> -->
      </div>
    </div> <!-- author container-->
    <div class="conditionContainer">
      <textarea class="conditions" cols="40" rows="5" readonly="readonly">본 서비스를 이용하기 위한 약관</textarea>
      <p class="checkForCondition"><input type="checkbox">Agree</p>
    </div>
    <div class="buttonContainer">
      <button class="buttonForCreate" type="button" v-on:click="clickForSignUp">Create</button>
      <button class="buttonForBack" type="button" v-on:click="clickForBack">Cancel</button>
    </div> <!-- button container-->
  </div>
</template>

<script>
export default {
  name: 'signUp',
  data () {
    return {
      signUpData: {
        id: '',
        password: '',
        name: '',
        authorName: '',
        organization: '',
        agree: false
      }
    }
  },
  methods: {
    clickForSignUp () {
      console.log(this.signUpData)
    },
    clickForBack () {
      this.$emit('changeFlag')
    }
  }
}
</script>

<style lang="scss">
  @import './signUpComponent.scss';
</style>
