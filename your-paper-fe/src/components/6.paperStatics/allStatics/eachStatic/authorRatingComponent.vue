<template>
  <div class="authorTypeWrapper">
    <div id="typeContainer"></div>
  </div>
</template>

<script>
export default {
  name: 'authorRatingComponent',
  mounted () {

  },
  computed: {
    getAuthorType(){
      return this.$store.getters.REPRINT_COUNT_GETTER
    }
  },
  watch: {
    getAuthorType(){
      var chart = c3.generate({
        bindto: '#typeContainer',
        data: {
          columns: [
            ['교신저자', this.$store.getters.REPRINT_COUNT_GETTER],
            ['공저자',  this.$store.getters.GENERAL_COUNT_GETTER],
            ['타인논문',  this.$store.getters.REF_COUNT_GETTER]
          ],
          type : 'donut',
          onclick: function (d, i) { console.log('onclick', d, i); },
          onmouseover: function (d, i) { console.log('onmouseover', d, i); },
          onmouseout: function (d, i) { console.log('onmouseout', d, i); }
        },
        donut: {
          title: '교신 저자 비율'
        }
      });
    }

  }
}
</script>

<style lang="scss">
  @import './authorRatingComponent.scss';
</style>
