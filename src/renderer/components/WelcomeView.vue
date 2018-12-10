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
        <v-card-title class="headline blue-grey darken-2">시작에 앞서</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <h2>초기화 과정</h2>
          <hr>
          <br>
          <ul>
            <li >프로그램을 시작하면 1분 ~ 2분 간 초기화 과정이 필요합니다.</li>
            <li >이 시간 동안 검색 서비스는 사용할 수 없습니다.</li>
            <li >초기화 과정이 완료되면 모든 로딩을 비활성화합니다.</li>
            <li >이후 사용에 대해서는 도움말을 참조하시면 도움이 됩니다.</li>
            <li >프로그램의 자세한 진행상황은 맨 아래 진행로그를 확인해주세요.</li>
          </ul>
          <br>
          <h2>시스템 제한 사항</h2>
          <hr>
          <br>
          <ul>
            <li >본 프로그램은 Window 전용 프로그램입니다.</li>
            <li >본 프로그램의 검색은 Web of Science(이하 WoS) 검색을 기반으로 합니다.</li>
            <li >본 프로그램의 검색은 SCOPUS 및 한국연구재단 자료를 포함하지 않습니다.</li>
            <li >'엑셀로 검색하기' 기능에서 인용 논문이 5000개 이상인 타겟 논문은 검색이 원활하지 않을 수 있습니다.</li>
            <li >'엑셀로 검색하기' 기능은 분당 20개 검색 처리 속도를 기대할 수 있습니다.</li>
            <li >현재 피인용 논문게재장려금 및 공동연구(공동논문) 장려금 예상 산정 기능은 포함하지 않습니다.</li>
            <li >게재 장려금 산정에서 기준 저자(산정받을 분의 WoS상 표기명)가 부정확하면 결과가 올바르지 않습니다.</li>
            <li >교신저자와 제1저자가 본교 소속일 경우 기입하신 기준 저자를 대표저자로 판단해 산정을 예상합니다.</li>
            <li >게재 장려금 산정에서 본 프로그램의 결과가 실제 결과와 항상 일치하지 않을 수 있음에 주의해주십시오.</li>
            <ul>
              <li ><small>2017년 개정 기준(2017년 투고 논문부터 적용)</small></li>
              <li ><small>제도유예 : 2017년 투고된 이공계열 하위 50% 학술지 논문에 대해서는 장려금 100만원 지급</small></li>
            </ul>
            <li >등급을 확인할 수 없는 경우, 주저자(제1저자, 교신저자) 확인이 불가능한 경우 등 예외적 정보에 대한 장려금 산정 예상은 제공하지 않습니다.</li>
          </ul>
          <br>
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
        <v-card-title class="headline blue-grey darken-2">도움말</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <!-- <h3 class='mb-3 mt-3'>1. 상세 단일 검색</h3>
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
          <p>[6] : 해당 논문의 저자 목록</p> -->
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
