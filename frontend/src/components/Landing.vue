<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>Welcome to Midas App</h1>
      </v-flex>
      <v-flex xs12 class="text-xs-center">
        <lottie :options="defaultOptions" :height="800" :width="800" v-on:animCreated="handleAnimation"/>
      </v-flex>
      <v-flex xs12 sm6 offset-sm3 class="text-xs-center" mt-5>
        <v-btn color="primary" to="/signup">Sign Up</v-btn>
        <v-btn to="/signin">Sign In</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
/* eslint-disable */
import Lottie from './LottieContainer.vue';
import * as animationData from '../assets/rainOnCloud.json';
export default {
  name: 'app',
  components: {
    'lottie': Lottie
  },
  data() {
    return {
      defaultOptions: {animationData: animationData},
      animationSpeed: 1
    }
  },
  methods: {
    handleAnimation: function (anim) {
      this.anim = anim;
    },
    stop: function () {
      this.anim.stop();
    },
    play: function () {
      this.anim.play();
    },
    pause: function () {
      this.anim.pause();
    },
    onSpeedChange: function () {
      this.anim.setSpeed(this.animationSpeed);
    }
  }
}

</script>
