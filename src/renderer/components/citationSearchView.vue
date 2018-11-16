<template>
  <v-layout row wrap>
    <!-- 옵션 컨테이너 -->
    <v-flex xs4>
      <v-radio-group v-model="gubun" row>
        <v-radio label="논문명" value="1" checked></v-radio>
        <v-radio label="저자명" value="2"></v-radio>
      </v-radio-group>
    </v-flex>
    <v-flex xs4>
      <v-radio-group v-model="isFile" row>
        <v-radio label="검색어로" value="1" checked></v-radio>
        <v-radio label="엑셀파일로" value="2"></v-radio>
      </v-radio-group>
    </v-flex>
    <v-flex xs4>
    </v-flex>
    <v-flex xs6>
      <input type="file" @change="processFile($event)">
      <input type="folder" @change="processFile($event)">
    </v-flex>
    <v-flex xs1 sm1 md1>
      <v-text-field
        label="검색하실 내용을 입력하세요."
        placeholder=""
        outline
        v-model="keyword"
      ></v-text-field>
    </v-flex>
    <v-flex xs5 sm5 md5>
      <v-text-field
        label="검색하실 내용을 입력하세요."
        placeholder=""
        outline
        v-model="keyword"
      ></v-text-field>
    </v-flex>
    <!-- <v-flex xs12>
      <v-expansion-panel>
        <v-expansion-panel-content>
          <div slot="header">구분 옵션</div>
          <v-flex pl-3 xs12>

          </v-flex>
                
            <label for="file">인풋 파일 경로</label>
            <input type="text" id="file" value="" v-model="filePath">
            
            <label for="dir">아웃풋 폴더 경로</label>
            <input type="text" id="dir" value="" v-model="dirPath">
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-flex> -->
    <v-flex xs12 mb12>
      <!-- <blockquote class="text-xs-center">
        &#8220;First, solve the problem. Then, write the code.&#8221;
        <footer>
          <small>
            
            <em>{{ state }}</em>
          </small>
        </footer>
        <v-btn
          @click="search"
          slot="activator"
        >
        <v-flex v-if="!loading">LOAD</v-flex>
        <pulse-loader :loading="loading" :color="'#5bc0de'" :size="'20px'"></pulse-loader>
        </v-btn>
      </blockquote> -->
      
      <h3>진행 로그</h3><br>
      <v-card style="height:250px; overflow-y: scroll">
        <v-card-title>
          <span v-html="log"></span>
        </v-card-title>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';
import { spawn } from 'child_process';

export default {
  data() {
    return {
      pymsg: 'not yet',
      state: 'ready',
      loading: false,
      executer: '',
      log: '',
      gubun: 0,
      keywordGubun: ['구분을 선택하세요.', '논문명', '저자명'],
    };
  },
  components: {
    PulseLoader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    search() {
      const rInFormat = /time:(.+)@lineout:(.+)/gm;
      const cmd = spawn('cmd.exe');
      this.executer = cmd;

      cmd.stdin.setDefaultEncoding('utf-8');
      cmd.stdout.setDefaultEncoding('utf-8');
      cmd.stderr.setDefaultEncoding('utf-8');

      cmd.stderr.on('data', (data) => {
        console.log(`cmd stderr: ${data.toString()}`);
        const output = data.toString().replace(/\n/ig, '').split('&');
        let time = '';
        let resJSON = '';
        console.log(output);
        for (let i = 0; i < output.length; i += 1) {
          if (!output[i].match(rInFormat)) break;
          time = RegExp.$1;
          resJSON = JSON.parse(RegExp.$2);
          // 모듈로부터의 결과
          switch (resJSON.command) {
            case 'loading':
              this.loading = !this.loading;
              break;
            default:
              this.log = `${time} ${resJSON.msg}<br>${this.log}`;
              break;
          }
        }
      });

      cmd.on('close', (code) => {
        console.log(`cmd child process exited with code ${code.toString()}`);
      });

      if (this.loading) return;
      this.loading = true;
      this.executer.stdin.write('python src/pyscripts/citationSearch.py\n');
    },
  },
  beforeDestroy() {
    this.executer.stdin.end();
  },
};

</script>

<style scoped>
  img {
    margin-left: auto;
    margin-right: auto;
    display: block;
  }
</style>