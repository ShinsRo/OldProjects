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
        <v-divider></v-divider>
        <v-card-text>
          <p>
            &nbsp;&nbsp;프로그램을 시작하면, 2분 ~ 3분 간 초기화 과정이 필요합니다. 
            이 시간 동안 검색 서비스는 사용할 수 없습니다.
            초기화 과정이 완료되면, 가장 하단에 위치한 진행로그에
            "dispatcher 가 준비되었다" 는 문구와 함께
            모든 검색 서비스의 Loading bar 및 Loading이 없어집니다.
            이후 시스템을 사용하실 수 있습니다.
            좌측 상단의 '>' 표시를 눌러 서비스 이름을 확인하시고,
            원하시는 검색을 이용하세요.
          </p>
          
          <p>
            &nbsp;&nbsp;프로그램을 강제종료 시, 진행 중인 검색 프로세스가 잔류할 수 있습니다.
            프로세스는 검색을 마치면 자동적으로 종료하며, 불필요한 경우 강제로 종료시켜주십시오.
            해당 이슈는 부모 프로세스 강제 종료 이벤트에 자식 프로세스가 종료하지 못하는 현상으로 정의하고 해결 중에 있습니다.
          </p>
          <p>
            문제가 발생하면 siotman0728@gmail.com로 연락부탁드립니다.
          </p>
          <p>
             감사합니다.
          </p>
          <div class="text-xs-right">
            <em><small>&mdash; Seungshin Kim</small></em>
          </div>
        </v-card-text>
        <!-- <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn primary flat router to="/inspire">Continue</v-btn>
        </v-card-actions> -->
      </v-card>
    </v-flex>
        <v-flex xs10 class="mt-3">
      <v-card>
        <v-card-title class="headline">기능별 가이드</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <h3 class='mb-3'>1. 상세 단일 검색</h3>
          <img class='logo' src="~@/assets/guide1_1.png" alt='그림1-1'>
          <p>
            설명설명
          </p>
          <h3 class='mb-3'>2. 상세 엑셀 검색</h3>
          <img class='logo' src="~@/assets/guide2_1.png" alt='그림2-1'>
          <p>
            설명설명
          </p>
          <h3 class='mb-3'>3. 일반 엑셀 검색</h3>
          <img class='logo' src="~@/assets/guide3_1.png" alt='그림3-1'>
          <p>
            설명설명
          </p>
        </v-card-text>
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
