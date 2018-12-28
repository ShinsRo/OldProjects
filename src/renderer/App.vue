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
            @click="winMaximize"
            >
              <v-icon v-html="'crop_square'"></v-icon>
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
            :version="version"
            :loading="loading"
            :errQuery="DV['res'].errQuery"
            :resList="DV['res'].resList"
            :mErrQuery="DV['mres'].errQuery"
            :mResList="DV['mres'].resList"
            :fErrQuery="fErrQuery"
            :fResList="fResList"
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
        <span>&copy; 2018 Sejong IACF, Software-centered Univ.</span>
        <v-spacer></v-spacer>
      </v-footer>

      </v-content>
    </v-app>
  </div>
</template>

<script>
  import { remote } from 'electron';
  import { spawn } from 'child_process';
  import path from 'path';
  import { readFileSync } from 'fs';

  export default {
    name: 'sejong-wos',
    data: () => ({
      title: '세종대학교 논문 정보 검색 시스템',
      version: 'x.x.x',
      executer: '',
      errAlert: { show: false, msg: '' },
      loading: true,
      log: '',
      logLineCnt: 1,
      logLineLimit: 500,
      maximize: false,
      clipped: true,
      drawer: true,
      fixed: false,
      items: [
        { icon: 'info', title: '가이드', to: '/' },
        { icon: 'search', title: '빠른 검색', to: '/FastSearch' },
        { icon: 'zoom_in', title: '상세 검색하기', to: '/SingleSearch' },
        { icon: 'format_quote', title: '엑셀로 검색하기', to: '/MultiSearch' },
        // { icon: 'person', title: '중복을 허용해 검색하기', to: '/citationSearchByAuthor' },
        { icon: 'library_books', title: '엑셀 취합하기', to: '/excelIntegration' },
        { icon: 'email', title: '메일 보내기', to: '/SendMail' },
      ],
      miniVariant: true,
      DV: {
        res: {
          errQuery: [],
          resList: [],
          resIndex: 0,
        },
        mres: {
          errQuery: [],
          resList: [],
          resIndex: 0,
        },
        fres: {
          errQuery: [],
          resList: [],
          resIndex: 0,
        },
      },
      fErrQuery: [],
      fResList: [],
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
      winMaximize: () => {
        this.maximize = !this.maximize;
        remote.BrowserWindow.getFocusedWindow().setFullScreen(this.maximize);
      },
    },
    created() {
      let version = 'x.x.x';
      if (remote.getGlobal('IS_DEV')) {
        version = readFileSync('version.json');
      } else {
        version = readFileSync(`${path.dirname(process.execPath)}/version.json`);
      }
      this.version = JSON.parse(version);

      if (this.executer === '') {
        const rInFormat = /time:(.+)#@lineout:(.+)/gm;
        this.loading = true;
        let cmd = '';
        if (remote.getGlobal('IS_DEV')) {
          cmd = spawn('cmd.exe');
        } else {
          cmd = spawn(`${path.dirname(process.execPath)}/dispatcher/dispatcher.exe`);
        }
        cmd.stdin.setDefaultEncoding('utf-8');
        cmd.stdout.setDefaultEncoding('utf-8');
        cmd.stderr.setDefaultEncoding('utf-8');

        // cmd.stdin.on('data', (data) => {
        //   console.log(`cmd stdin: ${data.toString()}`);
        // });
        // cmd.stderr.on('data', (data) => {
        //   console.log(`cmd stderr: ${data.toString()}`);
        // });
        cmd.stdout.on('data', (data) => {
          const output = data.toString().replace(/\n/ig, '').split('#&');

          let time = '';
          let resJSON = '';

          for (let i = 0; i < output.length; i += 1) {
            if (!output[i].match(rInFormat) || !output[i]) break;
            time = RegExp.$1;
            resJSON = JSON.parse(RegExp.$2);

            if ('msg' in resJSON) {
              console.log(resJSON.msg);
            }
            if ('target' in resJSON) {
              console.log(resJSON.target);
            }
            const tval = resJSON.command;
            // 모듈로부터의 결과
            switch (tval) {
              // 검색 모듈 명령어.
              case 'mres':
              case 'res':
                switch (resJSON.target) {
                  case 'loading':
                    this.loading = resJSON.res;

                    break;
                  case 'paperData':
                    this.DV[tval].resIndex += 1;
                    resJSON.res.index = this.DV[tval].resIndex;
                    this.DV[tval].resList.push(resJSON.res);

                    break;
                  case 'citingArticles':
                    for (let ii = 0; ii < this.DV[tval].resList.length; ii += 1) {
                      if (this.DV[tval].resList[ii].id === resJSON.res.id) {
                        this.DV[tval].resList[ii].citingArticles = resJSON.res;
                        break;
                      }
                    }

                    break;
                  case 'tc_data':
                    for (let ii = 0; ii < this.DV[tval].resList.length; ii += 1) {
                      if (this.DV[tval].resList[ii].id === resJSON.res.id) {
                        this.DV[tval].resList[ii].tc_data = {};
                        const temp = Object.keys(resJSON.res.tc_data);
                        for (let jj = 0; jj < temp.length; jj += 1) {
                          this.DV[tval].resList[ii]
                            .tc_data[parseInt(temp[jj])] = resJSON.res.tc_data[temp[jj]];
                        }
                        break;
                      }
                    }

                    break;
                  case 'errQuery':
                    this.DV[tval].errQuery.unshift(resJSON.res);

                    break;
                  default:
                    if (this.logLineCnt % this.logLineLimit === 0) {
                      this.log = '';
                    }
                    this.log = `${time} : ${JSON.stringify(resJSON.res)}<br>${this.log}`;
                    this.logLineCnt += 1;

                    break;
                }

                break;
              case 'fres':
                if (resJSON.target === 'fast_5000') {
                  this.fResList = resJSON.res;
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

        // cmd.on('close', (code) => {
        //   console.log(`cmd child process exited with code ${code.toString()}`);
        //   this.executer = '';
        // });
        cmd.on('close', () => {
          this.executer = '';
        });
        if (remote.getGlobal('IS_DEV')) {
          this.executer = cmd;
          this.executer.stdin.write('python pyscripts/NEWPY/dispatcher.py\n');
        }
        this.executer = cmd;
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
