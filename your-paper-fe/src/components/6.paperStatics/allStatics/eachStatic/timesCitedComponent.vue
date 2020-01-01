<template>
  <div class="timesCitedWrapper">
    <div class="timesContainer">
      <p class="timesTitle">총 피인용수</p>
      <p class="timesData">{{ countedCite }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'timeCitedComponent',
  data () {
    return {
      countedCite: 0
    }
  },
  computed: {
    isCited () {
      return this.$store.getters.CITE_COUNTER_GETTER
    }
  },
  watch: {
    isCited (newVal, oldVal) {
      this.countedCite = newVal
      console.log('couterCite change')
    }
  }
}
</script>

<style lang="scss">
  @import './timesCitedComponent.scss';
</style>
