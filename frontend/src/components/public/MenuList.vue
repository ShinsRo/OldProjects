<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5 >
        <h1>Menu List</h1>
      </v-flex>
      <v-flex v-for="i in 3" :key="`4${i}`" mt-5 pr-2>
        <v-card>
          <v-card-media src="../../assets/testimg.jpg" height="100px" width="100px">
          </v-card-media>
          <v-card-title primary-title>
            <div>
              <h3 class="headline mb-0">Menu one</h3>
              <div>So ~ good. <br>choose it, right now!</div>
            </div>
          </v-card-title>
          <v-card-actions>
            <v-btn flat color="orange">Share</v-btn>
            <v-btn flat color="orange">Explore</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {}
</script>
