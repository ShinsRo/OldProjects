<template>
    <div>
      <my-list id="myListOuterLayout"></my-list>
    </div>
</template>

<script>
import myList from '../components/Main/contents/MyList/MyListLayout'

export default {
  name: 'Main',
  components: {
    'my-list': myList
  }
}
</script>

<style lang="scss">
  @import './Main.scss';

</style>
