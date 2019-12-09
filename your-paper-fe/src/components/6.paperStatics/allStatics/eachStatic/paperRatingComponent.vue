<template>
  <div class="paperRatingWrapper">
    <div id="ratingContainer"></div>
  </div>
</template>

<script>
export default {
  name: 'paperRatingComponent',
  mounted () {

  },
  computed: {
    getGrade () {
      return this.$store.getters.NONE_GRADE_GETTER
    },
  },
  watch: {
    getGrade () {
      console.log( this.$store.getters.NONE_GRADE_GETTER)
      var chart = c3.generate({

        bindto: '#ratingContainer',
        data: {
          columns: [
            ['SCI', this.$store.getters.SCI_GRADE_GETTER],
            ['SCIE',this.$store.getters.SCIE_GRADE_GETTER],
            ['SCPIS', this.$store.getters.SCPIS_GRADE_GETTER],
            ['NONE', this.$store.getters.NONE_GRADE_GETTER],
          ],
          type : 'donut',
          onclick: function (d, i) { console.log('onclick', d, i); },
          onmouseover: function (d, i) { console.log('onmouseover', d, i);},
          onmouseout: function (d, i) { console.log('onmouseout', d, i); }
        },
        donut: {
          title: '논문 등급 비율'
        }
      });

    }
  }
}
</script>

<style lang="scss">
  @import './paperRatingComponent.scss';
</style>
