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
    <v-flex xs12 mb12>
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
      executer: spawn('cmd.exe'),
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
      const cmd = this.executer;

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
      this.executer.stdin.write('ASD');
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