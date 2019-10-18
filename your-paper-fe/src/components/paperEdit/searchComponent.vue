<template>
  <div class="wrapper">
    <div class="searchContainer">
      <input class="searchInputBox" type="text" placeholder="논문명, 저자, DOI, 연구기관">
      <button class="searchButton" type="button">Search</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'searchComponent'
}
</script>

<style lang="scss">
  @import './searchComponent';
</style>
