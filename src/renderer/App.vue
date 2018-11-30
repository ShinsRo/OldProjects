<template>
  <div id="app">
    <v-app dark>
      <!-- 드로워 정의 -->
      <v-navigation-drawer
        fixed
        :mini-variant="miniVariant"
        :clipped="clipped"
        v-model="drawer"
        app
        permanent
      >
        <v-list>
          <v-list-tile>
            <v-list-tile-action>
              <v-btn 
                icon
                @click.native.stop="miniVariant = !miniVariant"
              >
                <v-icon v-html="miniVariant ? 'chevron_right' : 'chevron_left'"></v-icon>
              </v-btn>
            </v-list-tile-action>
          </v-list-tile>
        </v-list>
        <v-list>
          <v-list-tile 
            router
            :to="item.to"
            :key="i"
            v-for="(item, i) in items"
            exact
          >
            <v-list-tile-action>
              <v-icon v-html="item.icon"></v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title v-text="item.title"></v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list>
      </v-navigation-drawer>
      <!-- END 드로워 정의 -->

      <!-- 헤더 라인 정의 -->
      <v-toolbar fixed app :clipped-left="clipped" dense style="-webkit-app-region: drag">
        <v-toolbar-title v-text="title"></v-toolbar-title>
        <v-spacer></v-spacer>
        <v-tooltip bottom style="-webkit-app-region: no-drag">
            <v-btn
            icon
            slot="activator"
            @click="winMinimize"
            >
              <v-icon v-html="'minimize'"></v-icon>
            </v-btn>
            <v-btn
            icon
            slot="activator"
            @click="winClose"
            >
              <v-icon v-html="'close'"></v-icon>
            </v-btn>

        </v-tooltip>
        
      </v-toolbar>
      <!-- END 헤더 라인 정의 -->
      <!-- content -->
      <v-content>
        <!-- sysErr alert -->
        <v-flex xs12>
          <v-alert
          v-model="errAlert.show"
          dismissible
          type="error"
          >
            {{ decodeURIComponent(errAlert.msg) }}
          </v-alert> 
        </v-flex>
        <!-- END sysErr alert -->
        <v-container fluid fill-height>
          <v-slide-y-transition mode="out-in">
            <router-view 
            :loading="loading"
            :errQuery="errQuery"
            :resList="resList"
            :mErrQuery="mErrQuery"
            :mResList="mResList"
            :cErrQuery="cErrQuery"
            :cResList="cResList"
            :executer="executer"
            :log="log"
            v-on:stdin="stdinAll"
            ></router-view>
          </v-slide-y-transition>
      <!-- END content -->
        </v-container>
      </v-content>
      <v-content>
        <v-container fluid fill-height>
          <v-flex xs12>
            <h3>진행 로그</h3><br>
            <v-card style="height:250px; overflow-y: scroll">
              <v-card-title>
                <span v-html="log"></span>
              </v-card-title>
            </v-card>
          </v-flex>
        </v-container>
      <v-footer :fixed="fixed" app>
        <v-spacer></v-spacer>
        <span>&copy; 2018 Sejong Univ.</span>
        <v-spacer></v-spacer>
      </v-footer>

      </v-content>
    </v-app>
  </div>
</template>

<script>
  import { remote } from 'electron';
  import { spawn } from 'child_process';
  // import path from 'path';
  export default {
    name: 'sejong-wos',
    data: () => ({
      errAlert: {
        show: false,
        msg: '',
      },
      log: '',
      executer: '',
      clipped: true,
      drawer: true,
      fixed: false,
      resIndex: 0,
      mResIndex: 0,
      items: [
        { icon: 'info', title: '가이드', to: '/' },
        { icon: 'search', title: '상세 단일 검색', to: '/citationSearch' },
        { icon: 'format_quote', title: '상세 엑셀 검색', to: '/citationSearchMulti' },
        { icon: 'person', title: '저자로 검색', to: '/citationSearchByAuthor' },
        { icon: 'description', title: '일반 엑셀 검색', to: '/commonSearchMulti' },
        { icon: 'test', title: '테스트', to: '/TEST' },
      ],
      miniVariant: true,
      title: '세종대학교 논문 정보 검색 시스템',
      loading: true,
      cErrQuery: [],
      cResList: [
        {
          id: 102020,
          Title: 'example',
          Authors: ['저자, A', '저자, B'],
          'Source Title': 'LWT-FOOD SCIENCE AND TECHNOLOGY',
          'Publication Date': 'NOV 2014',
          Volume: '59',
          Issue: '1',
          'Beginning Page': '115',
          'Ending Page': '121',
          ivp: ['1/59', '115-121'],
          DOI: '10.1016/j.lwt.2014.04.058',
          'Total Citations': '71',
        },
      ],
      mErrQuery: [
        // {
        //   query: 'example2',
        //   msg: '검색결과가 없습니다.',
        // },
      ],
      mResList: [
        {
          id: 102020,
          index: 0,
          title: 'example',
          authors: ['저자, A', '저자, B'],
          firstAuthor: '저자, A',
          authorsCnt: '2',
          addresses: {
            '저자, A': ['[1] 연구기관 A', '[2] 연구기관 B'],
            '저자, B': ['[1] 연구기관 A'],
          },
          doi: '000011',
          capedGrades: ['SCIE', 'SCI'],
          volume: '4',
          issue: '5',
          pages: '333-1414',
          ivp: ['4/5', '333-1414'],
          published: '',
          publishedMonth: 'NOV 2020',
          publisher: ['Sejong Docs.'],
          impact_factor: { 2017: '2.111', '5 year': '1.442' },
          timesCited: '2',
          grades: ['asdasd', 'asdasdasdasdasdasd'],
          docType: 'Article',
          researchAreas: '',
          language: 'English',
          reprint: '저자, A',
          goodRank: '0.34',
          prevYearIF: '2.111',
          issn: 'issn-0000',
          jcr: [
            ['JCR® Category', 'Rank in Category', 'Quartile in Category', 'Percentage'],
            ['BIOCHEMISTRY & MOLECULAR BIOLOGY', '1 of 293', 'Q1', '0.34'],
            ['CELL BIOLOGY', '2 of 190', 'Q1', '1.05'],
            ['MEDICINE, RESEARCH & EXPERIMENTAL', '1 of 133', 'Q1', '0.75'],
          ],
          citingArticles: {
            id: 102020,
            titles: ['논문 A', '논문 B'],
            authors: ['저자, C; 저자, D; 저자, E;', '저자, F; 저자, G;'],
            isSelf: ['Self', 'Others'],
            selfCitation: 1,
            othersCitation: 1,
          },
        },
      ],
      errQuery: [
        // {
        //   query: 'example2',
        //   msg: '검색결과가 없습니다.',
        // },
      ],
      resList: [
        {
          id: 102020,
          index: 0,
          title: 'example',
          authors: ['저자, A', '저자, B'],
          firstAuthor: '저자, A',
          authorsCnt: '2',
          addresses: {
            '저자, A': ['[1] 연구기관 A', '[2] 연구기관 B'],
            '저자, B': ['[1] 연구기관 A'],
          },
          doi: '000011',
          capedGrades: ['SCIE', 'SCI'],
          volume: '4',
          issue: '5',
          pages: '333-1414',
          ivp: ['4/5', '333-1414'],
          published: '',
          publishedMonth: 'NOV 2020',
          publisher: ['Sejong Docs.'],
          impact_factor: { 2017: '2.111', '5 year': '1.442' },
          timesCited: '2',
          grades: ['asdasd', 'asdasdasdasdasdasd'],
          docType: 'Article',
          researchAreas: '',
          language: 'English',
          reprint: '저자, A',
          goodRank: '0.34',
          prevYearIF: '2.111',
          issn: 'issn-0000',
          jcr: [
            ['JCR® Category', 'Rank in Category', 'Quartile in Category', 'Percentage'],
            ['BIOCHEMISTRY & MOLECULAR BIOLOGY', '1 of 293', 'Q1', '0.34'],
            ['CELL BIOLOGY', '2 of 190', 'Q1', '1.05'],
            ['MEDICINE, RESEARCH & EXPERIMENTAL', '1 of 133', 'Q1', '0.75'],
          ],
          citingArticles: {
            id: 102020,
            titles: ['논문 A', '논문 B'],
            authors: ['저자, A; 저자, D; 저자, E;', '저자, F; 저자, G;'],
            isSelf: ['Self', 'Others'],
            selfCitation: 1,
            othersCitation: 1,
          },
        },
      ],
    }),
    methods: {
      stdinAll: (payload) => {
        if (payload.scope.loading) {
          return;
        }
        for (let idx = 0; idx < payload.inputs.length; idx += 1) {
          payload.scope.executer.stdin.write(`${payload.inputs[idx]}\n`);
        }
      },
      winClose: () => {
        window.close();
      },
      winMinimize: () => {
        remote.BrowserWindow.getFocusedWindow().minimize();
      },
    },
    mounted() {
      if (this.executer === '') {
        const rInFormat = /time:(.+)#@lineout:(.+)/gm;
        this.loading = true;
        const cmd = spawn('cmd.exe');
        // 빌드 전용 spawn
        // const cmd = spawn(`${path.dirname(process.execPath)}/dispatcher.exe`);
        cmd.stdin.setDefaultEncoding('utf-8');
        cmd.stdout.setDefaultEncoding('utf-8');
        cmd.stderr.setDefaultEncoding('utf-8');

        cmd.stdin.on('data', (data) => {
          console.log(`cmd stdin: ${data.toString()}`);
        });
        cmd.stdout.on('data', (data) => {
          console.log(`cmd stdout: ${data.toString()}`);
        });
        cmd.stderr.on('data', (data) => {
          const output = data.toString().replace(/\n/ig, '').split('#&');
          try {
            console.log(decodeURIComponent(output));
          } catch (e) {
            console.log(output);
          }
          let time = '';
          let resJSON = '';

          for (let i = 0; i < output.length; i += 1) {
            if (!output[i].match(rInFormat) || !output[i]) break;
            time = RegExp.$1;
            resJSON = JSON.parse(RegExp.$2);
            // 모듈로부터의 결과
            switch (resJSON.command) {
              // 단일 검색 모듈 명령어.
              case 'res':
                if (resJSON.target === 'loading') {
                  this.loading = resJSON.res;
                } else if (resJSON.target === 'paperData') {
                  this.resIndex += 1;
                  resJSON.res.index = this.resIndex;
                  this.resList.push(resJSON.res);
                } else if (resJSON.target === 'citingArticles') {
                  for (let ii = 0; ii < this.resList.length; ii += 1) {
                    console.log(resJSON.res.id);
                    if (this.resList[ii].id === resJSON.res.id) {
                      this.resList[ii].citingArticles = resJSON.res;
                      break;
                    }
                  }
                } else if (resJSON.target === 'errQuery') {
                  this.errQuery.push(resJSON.res);
                } else {
                  this.log = `${time} : ${JSON.stringify(resJSON.res)}<br>${this.log}`;
                }
                break;
              case 'mres':
                if (resJSON.target === 'loading') {
                  this.loading = resJSON.res;
                } else if (resJSON.target === 'paperData') {
                  this.mResIndex += 1;
                  resJSON.res.index = this.mResIndex;
                  this.mResList.push(resJSON.res);
                } else if (resJSON.target === 'citingArticles') {
                  for (let ii = 0; ii < this.mResList.length; ii += 1) {
                    if (this.mResList[ii].id === resJSON.res.id) {
                      this.mResList[ii].citingArticles = resJSON.res;
                      break;
                    }
                  }
                } else if (resJSON.target === 'errQuery') {
                  this.mErrQuery.unshift(resJSON.res);
                } else {
                  this.log = `${time} : ${JSON.stringify(resJSON.res)}<br>${this.log}`;
                }
                break;
              case 'cres':
                if (resJSON.target === 'result') {
                  this.cResList = resJSON.res.cResList;
                  this.cNotFoundList = resJSON.res.notFoundList;
                }
                break;
              case 'sysErr':
                this.errAlert.msg = resJSON.msg;
                this.errAlert.show = true;
                break;
              case 'err':
              case 'log':
              default:
                this.log = `${time} : ${decodeURIComponent(resJSON.msg)}<br>${this.log}`;
                break;
            }
          }
        });

        cmd.on('close', (code) => {
          console.log(`cmd child process exited with code ${code.toString()}`);
          this.executer = '';
        });
        this.executer = cmd;
        // 빌드 시 주석 필수
        this.executer.stdin.write('python src/pyscripts/dispatcher.py\n');
      }
    },
    destroyed() {
      if (this.executer !== '') {
        this.executer.stdin.pause();
        this.executer.kill();
      }
    },
  };
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons');
  /* Global CSS */
</style>
