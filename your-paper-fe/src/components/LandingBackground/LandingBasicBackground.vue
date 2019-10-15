<template>
  <div class="LandingBackground">
    <div class="introduceContainer">
      <div class="decorationContainer">
        <div class="flagDecoration">
          <img class="flagImage" src="@/assets/images/decoration.png" alt="flagDecoration">
        </div>
        <div class="logoDecoration">
          <img class="logoImage" src="@/assets/images/paperLogo.png" alt="logoDecoration">
        </div>
        <div class="nameDecoration">
          <p class="siteName">Your Paper</p>
          <p class="siteIntroduce">Paper management system</p>
        </div>
      </div>
    </div>
    <div class="functionContainer">
      <div class="nameDecoration">
        <p class="siteName">Your Paper</p>
        <p class="siteIntroduce">Paper manamgement system</p>
      </div>
      <div class="formContainer">
        <LoginComponent></LoginComponent>
      </div>
    </div>
  </div>
</template>

<script>
import LoginComponent from '@/components/Login/LoginComponent.vue'

export default {
  name: 'LandingBackground',
  components: {
    LoginComponent
  }
}
</script>

<style lang="scss">
  @import '/LandingBasicBackground.scss';
</style>
