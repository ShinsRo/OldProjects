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
    <div class="functionContainer"></div>
  </div>
</template>

<script>
export default {
  name: 'LandingBackground'
}
</script>

<style lang="scss">
  @import '/LandingBasicBackground.scss';
</style>
