<template>
  <div class="landingBackgroundWrapper">
    <div class="leftContainer">
      <div class="flagImageContainer">
        <img class="flagImage" src="@/assets/images/decoration.png" alt="flagImage">
      </div>
      <div class="logoImageContainer">
        <img class="logoImage" src="@/assets/images/paperLogo.png" alt="logoImage">
      </div>
      <div class="descriptionContainer">
        <p class="siteName">Your Paper</p>
        <p class="siteDescription">Paper Management System</p>
      </div>
    </div>
    <div class="rightContainer">
    </div>
  </div>
</template>

<script>
export default {
  name: 'landingBackground'
}
</script>

<style lang="scss">
  @import './landingBackground.scss';
</style>
