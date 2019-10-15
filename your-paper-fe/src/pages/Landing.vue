<template>
  <div class="Landing">
    <LandingBackground></LandingBackground>
  </div>
</template>

<script>
import LandingBackground from '@/components/LandingBackground/LandingBasicBackground.vue'

export default {
  name: 'Landing',
  components: {
    LandingBackground
  }
}
</script>

<style lang="scss" scoped>
  .Landing {
    margin: 0;
    height: 100vh;
    width: 100vw;
  }
</style>
