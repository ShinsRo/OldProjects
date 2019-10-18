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
          <p class="BigName">Your Paper</p>
          <p class="BigIntroduce">Paper management system</p>
        </div>
      </div>
    </div>
    <div class="functionContainer">
      <div class="nameDecoration">
        <p class="smallName">Your Paper</p>
        <p class="smallIntroduce">Paper manamgement system</p>
      </div>
      <div class="formContainer">
        <!-- <LoginComponent></LoginComponent> -->
        <SignUpComponent></SignUpComponent>
      </div>
    </div>
  </div>
</template>

<script>
// import LoginComponent from '@/components/Login/LoginComponent.vue'
import SignUpComponent from '@/components/Landing/SignUp/SignUpComponent.vue'

export default {
  name: 'LandingBackground',
  components: {
    // LoginComponent
    SignUpComponent
  }
}
</script>

<style lang="scss">
  @import './LandingBasicBackground.scss';
</style>
