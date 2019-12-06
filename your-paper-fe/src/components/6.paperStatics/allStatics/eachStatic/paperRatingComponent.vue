<template>
  <div class="paperRatingWrapper">
    <div id="ratingContainer"></div>
  </div>
</template>

<script>
export default {
  name: 'paperRatingComponent',
  mounted () {
    var chart = c3.generate({
    bindto: '#ratingContainer',
    data: {
        columns: [
            ['SCI', 60],
            ['SCIE', 30],
            ['SCPIS', 30]
        ],
        type : 'donut',
        onclick: function (d, i) { console.log('onclick', d, i); },
        onmouseover: function (d, i) { console.log('onmouseover', d, i); },
        onmouseout: function (d, i) { console.log('onmouseout', d, i); }
    },
    donut: {
        title: '논문 등급 비율'
    }
    });
  }
}
</script>

<style lang="scss">
  @import './paperRatingComponent.scss';
</style>