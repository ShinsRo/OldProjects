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
        <LoginComponent v-show="flag===1"></LoginComponent>
        <SignUpComponent v-show="flag===2"></SignUpComponent>
      </div>
    </div>
  </div>
</template>

<script>
import LoginComponent from '@/components/Landing/Login/LoginComponent.vue'
import SignUpComponent from '@/components/Landing/SignUp/SignUpComponent.vue'

export default {
  name: 'LandingBackground',
  components: {
    LoginComponent,
    SignUpComponent
  },
  data () {
    return {
      flag: 1
    }
  }
}

</script>

<style lang="scss">
  @import './LandingBasicBackground.scss';
</style>
