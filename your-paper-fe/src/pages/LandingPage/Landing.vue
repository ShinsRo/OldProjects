<template>
  <div class="LandingWrapper">
    <landingBackground class="landingBackground"></landingBackground>
  </div>
</template>

<script>
import landingBackground from '../../components/5.landingBackground/landingBackground.vue'

export default {
  name: 'Landing',
  components: {
    landingBackground
  }
}
</script>

<style lang="scss" scoped>
  @import './Landing.scss';
</style>
