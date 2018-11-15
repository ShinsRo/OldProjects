<template>
  <v-layout row wrap justify-center id="wrapper">
    <!-- <v-flex xs12 md4 offset-md1 class="text-xs-center centered">
      <img id="logo" class="logo" src="~@/assets/logo.png" alt="electron-vue">
    </v-flex>
    <v-flex xs12 md6 class="text-xs-center centered">
      <img id="logo" class="logo" src="/static/v.png" alt="Vuetifyjs">
    </v-flex> -->
    <v-flex xs10 class="mt-3">
      <v-card>
        <v-card-title class="headline">공지</v-card-title>
        <v-card-text>
          <p>세종대학교 논문 정보 검색 시스템 데스크탑 어플리케이션 테스트 버전입니다.</p>
          <p>이 페이지에서 프로그램 사용 가이드를 안내할 예정입니다.</p>
          <div class="text-xs-right">
            <em><small>&mdash; Seungshin Kim</small></em>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- <v-btn primary flat router to="/inspire">Continue</v-btn> -->
        </v-card-actions>
      </v-card>
    </v-flex>
    <v-flex xs10 class="mt-4">
      <system-information></system-information>
    </v-flex>
    <!-- <v-flex xs10 class="mt-4">
      <v-card>
        <v-card-title class="headline">Docs</v-card-title>
        <v-divider></v-divider>
        <v-card-actions class="pt-3 pb-3">
          <v-spacer></v-spacer>
          <v-btn class="link-btn" @click="open('https://vuejs.org/v2/guide/')">Vue</v-btn>
          <v-spacer></v-spacer>
          <v-btn class="link-btn" @click="open('https://electron.atom.io/docs/')">Electron</v-btn>
          <v-spacer></v-spacer>
          <v-btn class="link-btn" @click="open('https://vuetifyjs.com')">Vuetify</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-flex> -->
  </v-layout>
</template>

<script>
  import SystemInformation from './WelcomeView/SystemInformation';

  export default {
    name: 'welcome',
    components: { SystemInformation },
    methods: {
      open(link) {
        this.$electron.shell.openExternal(link);
      },
    },
  };
</script>

<style scoped>
  .centered
  {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .logo
  {
    max-width: 100%;
  }

  .link-btn
  {
    width: 150px;
  }
</style>
