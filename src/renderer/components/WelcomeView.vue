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
        <v-card-title class="headline">기능별 주석</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <h3 class='mb-3 mt-3'>1. 상세 단일 검색</h3>
          <img class='logo' src="~@/assets/guide1_1.png" alt='그림1-1'>
          <p>[1] : 검색할 논문 제목 필드 : 2자 이상</p>
          <p>[2], [3] : 연도 - 1900 ~ 금년 사이</p>
          <p>[4] : 검색 시작 버튼</p>
          <img class='logo' src="~@/assets/guide1_2.png" alt='그림1-2'>
          <p>해당 논문 행을 클릭 시 하단으로 펼쳐진 상세 정보를 볼 수 있습니다.</p>
          <p>[5] : 해당 논문 제목</p>
          <p>[6] : 연도별 Impact Factor - 없을 시 나타나지 않음</p>
          <p>[7] : JCR 카테고리별 랭킹과 Quartile - 없을 시 나타나지 않음</p>
          <p>[8] : 저자별 소속 연구기관들 - 없을 시 나타나지 않음</p>
          <p>[9] : 저장된 파일 아이디 - 바탕화면의 '인용중인논문들' 폴더 내 저장됨</p>
          <p>[10] : 이 논문을 인용하는 논문과 그 저자. 10개 이상이면 나타나지 않음</p>
          <v-divider></v-divider>
          <h3 class='mb-3 mt-3'>2. 상세 엑셀 검색</h3>
          <img class='logo' src="~@/assets/guide2_1.png" alt='그림2-1'>
          <p>[1] : 검색어의 구분 - 논문제목, DOI : 논문 하나를 특정할 수 있는 검색어</p>
          <p>[2] : 파일 선택 - csv, xls, xlsx의 파일만 허용됨. 양식은 아래 참조</p>
          <p>[3] : 연도 - 1900 ~ 금년 사이</p>
          <p>[4] : 검색 시작 버튼</p>
          <v-divider></v-divider>
          <h3 class='mb-3 mt-3'>3. 일반 엑셀 검색</h3>
          <img class='logo' src="~@/assets/guide3_1.png" alt='그림3-1'>
          <p>[1 ~ 4] : 상세 엑셀 검색과 상동</p>
          <img class='logo' src="~@/assets/guide3_2.png" alt='그림3-2'>
          <p>[5] : 해당 논문 제목</p>
          <p>[6] : 해당 논문의 저자 목록</p>
          <p>
            설명설명
          </p>
        </v-card-text>
      </v-card>
    </v-flex>
    <!-- <v-flex xs10 class="mt-4">
      <system-information></system-information>
    </v-flex> -->
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
