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
          <div class="text-xs-right">현재 버전 : {{version.recent_full}}</div>
          <h2>주의사항</h2>
          <hr>
          <br>
          <ul>
            <li >프로그램을 시작하면 1분 ~ 2분 간 초기화 과정이 필요합니다.</li>
            <li >이 시간 동안 검색 서비스는 사용할 수 없습니다.</li>
            <li >초기화 과정이 완료되면 모든 로딩을 비활성화합니다.</li>
            <li >이후 사용에 대해서는 도움말을 참조하시면 도움이 됩니다.</li>
            <li ><v-icon>chevron_right</v-icon>버튼을 눌러 원하시는 서비스를 이용하세요.</li>
            <li >프로그램의 자세한 진행상황은 맨 아래 진행로그를 확인해주세요.</li>
          </ul>
          <br>
          <h2>시스템 제한 사항</h2>
          <hr>
          <br>
          <ul>
            <li ><font class='red' >주의! 1800개 이상의 검색을 연달아 실행할 경우 WoS 서버가 공격으로 인식합니다.</font></li>
            <ul>
              <li ><small><font color="grey">한 번 공격으로 인식하면 일정 시간 동안 모든 WoS 서비스 이용이 불가능하니 주의하세요.</font></small></li>
            </ul>
            <li >본 프로그램은 Window 전용 프로그램입니다.</li>
            <li >검색은 세종대학교 관련 논문으로 제한하고 있습니다.</li>
            <li >검색은 세종대학교 IP를 통해 이루어져야합니다.</li>
            <li >본 프로그램의 검색은 Web of Science(이하 WoS) 검색을 기반으로 합니다.</li>
            <li >본 프로그램의 검색은 SCOPUS 및 한국연구재단 자료를 포함하지 않습니다.</li>
            <li >'엑셀로 검색하기' 기능에서 인용 논문이 5000개 이상이면 5000개 까지의 정보만을 가져옵니다.</li>
            <li >'중복을 허용해 검색하기' 기능에서 논문이 50개 이상 검색되면 50개까지의 정보만을 가져옵니다.</li>
            <!-- <li >현재 피인용 논문게재장려금 및 공동연구(공동논문) 장려금 예상 산정 기능은 포함하지 않습니다.</li>
            <li >게재 장려금 산정에서 기준 저자(산정받을 분의 WoS상 표기명)가 부정확하면 결과가 올바르지 않습니다.</li>
            <li >교신저자와 제1저자가 본교 소속일 경우 기입하신 기준 저자를 대표저자로 판단해 산정을 예상합니다.</li>
            <li >게재 장려금 산정에서 본 프로그램의 결과가 실제 결과와 항상 일치하지 않을 수 있음에 주의해주십시오.</li>
            <ul>
              <li ><small><font color="grey">2017년 개정 기준(2017년 투고 논문부터 적용)</font></small></li>
              <li ><small><font color="grey">제도유예 : 2017년 투고된 이공계열 하위 50% 학술지 논문에 대해서는 장려금 100만원 지급</font></small></li>
            </ul>
            <li >등급을 확인할 수 없는 경우, 주저자(제1저자, 교신저자) 확인이 불가능한 경우 등 예외적 조건을 포함한 경우는 장려금 산정 예상액 정보를 제공하지 않습니다.</li> -->
          </ul>
          <!-- <br>
          <h2>업데이트 노트 v{{ version }}</h2>
          <hr>
          <br>
          {{ update_note }} -->
          <br>
          <h2>기타</h2>
          <hr>
          <br>
          <p>
            문제가 발생하면 왼쪽 하단 메뉴인 메일 보내기를 통해 문의해주십시오.
          </p>
          <p>혹은 sandan@sejong.ac.kr로 직접 메일을 보내실 수 있겠습니다. 감사합니다.</p>
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
           <v-expansion-panel>
            <!-- 빠른 검색 도움말 -->
            <v-expansion-panel-content>
              <div slot="header"><v-icon small class="mr-1">search</v-icon>: 빠른 검색</div>
              <v-card>
                <v-card-text>
                  <h2>키워드로 여러 논문 빠른 검색해보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide0_1.png" alt='그림0-1'>
                  <ul>
                    <li >먼저 [1] 에 검색하실 논문 제목을 입력합니다.
                      <ul>
                        <li><small><font color="grey">한글 입력, '='문자는 지원하지 않으니 주의해주세요.</font></small></li>
                      </ul>
                    </li>
                    <li >[2], [3]에 검색에 포함할 기간을 연도단위로 입력하세요.</li>
                    <li >[4]에 검색구분할 조건을 선택해주세요.
                      <ul>
                        <li><small><font color="grey">논문 제목, DOI로 검색 가능합니다.</font></small></li>
                      </ul>
                    </li>
                    <li >검색 버튼을 눌러 검색을 시작하세요.</li>
                  </ul>
                  <br>
                  <h2>검색 결과 보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide0_2.png" alt='그림0-2'>
                  <ul>
                    <li >[1]과 같은 행을 클릭하면, 해당 행에 대한 자세한 정보가 아래로 펼쳐집니다.</li>
                    <li >[2]버튼을 클릭하면 해당 행을 지울 수 있습니다. 복구가 불가능하니 주의하십시오.</li>
                    <li >[3]에서는 [2]버튼을 클릭하면 해당 논문에 대한 정보를 볼 수 있습니다.</li>
                    <li >[4]버튼을 클릭하면 검색한 전체결과를 지울 수 있습니다. 복구가 불가능하니 주의하십시오.</li>
                    <li >[5]에 키워드를 입력해 특정한 검색 결과들을 찾아볼 수 있습니다.</li>
                  </ul>
                </v-card-text>
              </v-card>
            </v-expansion-panel-content>
            <!-- END 빠른 검색 도움말 -->
            <!-- 검색하기 도움말 -->
            <v-expansion-panel-content>
              <div slot="header"><v-icon small class="mr-1">zoom_in</v-icon>: 상세 검색하기</div>
              <v-card>
                <v-card-text>
                  <h2>하나의 논문 제목으로 검색해보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide1_1.png" alt='그림1-1'>
                  <ul>
                    <li >먼저 [1] 에 검색하실 논문 제목을 입력합니다.
                      <ul>
                        <li><small><font color="grey">한글 입력, '='문자는 지원하지 않으니 주의해주세요.</font></small></li>
                      </ul>
                    </li>
                    <li >[2], [3]에 검색에 포함할 기간을 연도단위로 입력하세요.</li>
                    <li >(선택항목) [4]에 기준으로 삼으실 WoS 상 저자 표기명을 입력합니다.
                      <ul>
                        <li><small><font color="grey">ex) Kim, Seungshin; Kim, SS; Kim, S;</font></small></li>
                        <li><small><font color="grey">예상 장려금 산정, 본인인용/타인인용 구분에 사용합니다.</font></small></li>
                        <li><small><font color="grey">WoS 상 저자 표기가 ,(쉼표) 등을 포함한다면 그대로 포함하십시오.</font></small></li>
                      </ul>
                    </li>
                    <li >검색 버튼을 눌러 검색을 시작하세요.</li>
                  </ul>
                  <br>
                  <h2>검색 결과 보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide1_2.png" alt='그림1-2'>
                  <ul>
                    <li >[1]는 검색을 완료한 항목을 나타낸 표입니다.</li>
                    <li >[2]는 특정 검색어에 대한 오류 결과를 나타내는 표입니다.</li>
                    <li >[3]과 같은 행을 클릭하면, 해당 행에 대한 자세한 정보가 아래로 펼쳐집니다.
                      <ul>
                        <li><small><font color="grey">세부 정보에서 Impact Factor, JCR Rank, 저자목록, 피/타인용 구분 등을 알 수 있습니다.</font></small></li>
                        <li><small><font color="grey">단, 엑셀로 다운로드 시 펼쳐진 창이 나타내는 정보를 다운로드한 파일에 모두 포함하지는 않습니다.</font></small></li>
                      </ul>
                    </li>
                    <li >[4]버튼을 클릭하면 해당 행을 지울 수 있습니다. 복구가 불가능하니 주의하십시오.</li>
                    <li>[5]의 버튼을 클릭하면 [1]의 표를 엑셀로 다운로드할 수 있습니다.<ul>
                        <li><small><font color="grey">클릭 시 팝업 다이얼로그가 나타납니다.</font></small></li>
                        <li><small><font color="grey">해당 다이얼로그에서 경로를 나타내드리니, 파일 위치는 다이얼로그의 메세지를 참조하세요.</font></small></li>
                      </ul>
                    </li>
                    <li >[6]에 키워드를 입력해 특정한 검색 결과들을 찾아볼 수 있습니다.</li>
                  </ul>
                </v-card-text>
              </v-card>
            </v-expansion-panel-content>
            <!-- END 검색하기 도움말 -->
            <!-- 엑셀로 검색하기 도움말 -->
            <v-expansion-panel-content>
              <div slot="header"><v-icon small class="mr-1">format_quote</v-icon>: 엑셀로 검색하기</div>
              <v-card>
                <v-card-text>
                  <h2>여러 논문 제목을 담은 엑셀 파일로 검색해보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide2_1.png" alt='그림2-1'>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide2_0.png" alt='그림2-0'>
                  <ul>
                    <li >먼저 [1] 에 엑셀파일의 첫번째 열의 구분을 선택하세요.
                      <ul>
                        <li><small><font color="grey">논문 제목, DOI로 검색 가능합니다.</font></small></li>
                      </ul>
                    </li>
                    <li >[2]에 검색에 포함할 기간을 연도단위로 입력하세요.</li>
                    <li >[3]을 클릭하여 검색 내용이 담긴 엑셀 파일을 선택합니다.</li>
                    <li >엑셀에서 첫번째 열은 [1]의 구분에 따른 필수 값이므로 공백이 아니도록 주의해주십시오.<ul>
                        <li><small><font color="grey">두번째 열은 기준저자를 나타냅니다.</font></small></li>
                        <li><small><font color="grey">세번째 열은 같이 검색할 연구기관을 나타냅니다.</font></small></li>
                        <li><small><font color="grey">첫번째 열을 제외한 모든 열은 비워두실 수 있습니다.</font></small></li>
                      </ul>
                    </li>
                    <li >검색 버튼을 눌러 검색을 시작하세요.
                      <ul>
                        <li><small><font color="grey">검색의 결과는 "검색하기 기능"과 같습니다.</font></small></li>
                        <li><small><font color="grey">검색하기 기능 도움말에서 "검색 결과 보기" 항목을 참조하십시오.</font></small></li>
                      </ul>
                    </li>
                  </ul>
                  <br>
                </v-card-text>
              </v-card>
            </v-expansion-panel-content>
            <!-- END 엑셀로 검색하기 도움말 -->
            <!-- 중복을 허용해 검색하기 도움말 -->
            <v-expansion-panel-content>
              <div slot="header"><v-icon small class="mr-1">done_all</v-icon>: 중복을 허용해 검색하기</div>
              <v-card>
                <v-card-text>
                  <h2>키워드로 여러 논문 상세 검색해보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide6_1.png" alt='그림6-1'>
                  <ul>
                    <li >먼저 [1] 에 검색하실 논문 제목을 입력합니다.
                      <ul>
                        <li><small><font color="grey">한글 입력, '='문자는 지원하지 않으니 주의해주세요.</font></small></li>
                      </ul>
                    </li>
                    <li >[2], [3]에 검색에 포함할 기간을 연도단위로 입력하세요.</li>
                    <li >(선택항목) [4]에 기준으로 삼으실 WoS 상 저자 표기명을 입력합니다.
                      <ul>
                        <li><small><font color="grey">ex) Kim, Seungshin; Kim, SS; Kim, S;</font></small></li>
                        <li><small><font color="grey">예상 장려금 산정, 본인인용/타인인용 구분에 사용합니다.</font></small></li>
                        <li><small><font color="grey">WoS 상 저자 표기가 ,(쉼표) 등을 포함한다면 그대로 포함하십시오.</font></small></li>
                      </ul>
                    </li>
                    <li >[5]에 검색구분할 조건을 선택해주세요.
                      <ul>
                        <li><small><font color="grey">논문 제목, DOI로 검색 가능합니다.</font></small></li>
                      </ul>
                    </li>
                    <li >검색 버튼을 눌러 검색을 시작하세요.</li>
                    <li >[6]과 같은 행을 클릭하면, 해당 행에 대한 자세한 정보가 아래로 펼쳐집니다.</li>
                    <li >[7]버튼을 클릭하면 해당 행을 지울 수 있습니다. 복구가 불가능하니 주의하십시오.</li>
                    <li >[8]버튼을 클릭하면 검색한 전체결과를 지울 수 있습니다. 복구가 불가능하니 주의하십시오.</li>
                    <li >[9]의 버튼을 클릭하면 [6]의 표를 엑셀로 다운로드할 수 있습니다.<ul>
                        <li><small><font color="grey">클릭 시 팝업 다이얼로그가 나타납니다.</font></small></li>
                        <li><small><font color="grey">해당 다이얼로그에서 경로를 나타내드리니, 파일 위치는 다이얼로그의 메세지를 참조하세요.</font></small></li>
                      </ul>
                    </li>
                    <li >[10]에 키워드를 입력해 특정한 검색 결과들을 찾아볼 수 있습니다.</li>
                  </ul>
                  <br>
                  <h2>검색 결과 보기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide6_2.png" alt='그림6-2'>
                  <ul>
                    <li >[11]은 [6]을 클릭하면 각 논문에 대한 상세정보를 볼 수 있습니다.</li>
                  </ul>
                </v-card-text>
              </v-card>
            </v-expansion-panel-content>
            <!-- END 중복을 허용해 검색하기 도움말 -->
            <!-- 엑셀 취합하기 도움말 -->
            <v-expansion-panel-content>
              <div slot="header"><v-icon small class="mr-1">library_books</v-icon>: 엑셀 취합하기</div>
              <v-card>
                <v-card-text>
                  <h2>다운로드 받은 여러 엑셀 파일을 하나로 합치기</h2>
                  <hr>
                  <br>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide5_1.png" alt='그림5-1'>
                  <img class='logo elevation-10 mb-3' src="~@/assets/guide5_2.png" alt='그림5-1'>
                  <ul>
                    <li >[1] 버튼을 통해 파일을 그림과 같이 선택하고 열기를 누릅니다.</li>
                    <li >선택한 파일 목록은 [2] 에서 확인하실 수 있습니다.</li>
                    <li >목록을 확인하시고 [3] 버튼을 눌러 취합합니다.</li>
                    <li >취합하기가 성공적으로 작동하면 [4]에 파일들이 포함하는 행들이 나타납니다.</li>
                    <li >데이터가 올바른 지 확인하고 불필요한 행이 있다면 좌측 휴지통 아이콘을 클릭해 지웁니다.</li>
                    <li >[5] 버튼을 눌러 취합한 파일을 다운로드 받습니다.</li>
                  </ul>
                </v-card-text>
              </v-card>
            </v-expansion-panel-content>
            <!-- END 엑셀 취합하기 도움말 -->
          </v-expansion-panel>
        </v-card-text>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  import SystemInformation from './WelcomeView/SystemInformation';

  export default {
    props: ['version'],
    data: () => ({
      update_note: '',
      guideWindow: 3,
      guideLength: 3,
    }),
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
