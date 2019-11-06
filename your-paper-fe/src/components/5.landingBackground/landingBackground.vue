<template>
  <div class="landingBackgroundWrapper">
    <div class="leftContainer">
      <div class="flagImageContainer">
        <img class="flagImage" src="@/assets/images/decoration.png" alt="flagImage">
      </div>
      <div class="logoImageContainer">
        <img class="logoImage" src="@/assets/images/paperLogo.png" alt="logoImage">
      </div>
      <div class="leftDescriptionContainer">
        <p class="leftSiteName">Your Paper</p>
        <p class="leftSiteDescription">Paper Management System</p>
      </div>
    </div>
    <div class="rightContainer">
      <div class="rightDescritionContainer">
        <p class="rightSiteName">Your Paper</p>
        <p class="rightSiteDescription">Paper Management System</p>
      </div>
      <div class="formContainer">
        <login v-on:changeFlag="changeSignUp" v-if="flag===1"></login>
        <signUp v-on:changeFlag="changeLogin" v-if="flag===2"></signUp>
      </div>
    </div>
  </div>
</template>

<script>
import login from '../5.landingBackground/login/loginComponent.vue'
import signUp from '../5.landingBackground/signUp/signUpComponent.vue'

export default {

  name: 'landingBackground',
  components: {
    login,
    signUp
  },
  data () {
    return {
      flag: 1
    }
  },
  methods: {
    changeSignUp () {
      this.flag = 2
    },
    changeLogin () {
      this.flag = 1
    }
  }
}
</script>

<style lang="scss">
  @import './landingBackground.scss';
</style>
