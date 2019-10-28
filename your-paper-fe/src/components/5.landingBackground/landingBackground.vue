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
        <login v-show="flag===1"></login>
        <create v-show="flag===2"></create>
      </div>
    </div>
  </div>
</template>

<script>
import login from '../5.landingBackground/login/loginComponent.vue'
import create from '../5.landingBackground/create/createComponent.vue'

export default {

  name: 'landingBackground',
  components: {
    login,
    create
  },
  data () {
    return {
      flag: 2
    }
  }
}
</script>

<style lang="scss">
  @import './landingBackground.scss';
</style>
