<template>
  <v-layout row wrap>
    <!-- 옵션 컨테이너 -->
    <v-flex xs8>
      <v-text-field
        label="검색하실 내용을 입력하세요."
        placeholder=""
        outline
        v-model="query"
      ></v-text-field>
    </v-flex>
    <v-flex xs2>
      <v-text-field
        label="시작년도"
        placeholder=""
        outline
        v-model="startYear"
      ></v-text-field>
    </v-flex>
    <v-flex xs2>
      <v-text-field
        label="끝 년도"
        placeholder=""
        outline
        v-model="endYear"
      ></v-text-field>
    </v-flex>
    <v-flex xs12 class="text-xs-center">
      <v-btn
        @click="search"
        slot="activator"
      >
        <v-flex v-if="!loading">LOAD</v-flex>
        <v-flex v-if="loading">
          <pulse-loader :loading="loading" :color="'#5bc0de'" :size="'20px'"></pulse-loader>
        </v-flex>
      </v-btn>
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
      query: '',
      startYear: '',
      endYear: '',
    };
  },
  components: {
    PulseLoader,
  },
  created() {
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
        if (!output[i].match(rInFormat) || !output[i]) break;
        time = RegExp.$1;
        resJSON = JSON.parse(RegExp.$2);
        // 모듈로부터의 결과
        switch (resJSON.command) {
          case 'err':
          case 'log':
            this.log = `${time} : ${decodeURIComponent(resJSON.msg)}<br>${this.log}`;
            break;
          case 'res':
            if (resJSON.target === 'loading') {
              this.loading = resJSON.res;
            }
            this.log = `${time} : ${JSON.parse(resJSON.res)}<br>${this.log}`;
            break;
          default:
            this.log += `${time} : ${JSON.parse(resJSON)}<br>${this.log}`;
            break;
        }
      }
    });

    cmd.on('close', (code) => {
      console.log(`cmd child process exited with code ${code.toString()}`);
    });

    this.loading = true;
    this.executer.stdin.write('python src/pyscripts/citationSearch.py\n');
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    search() {
      const cmd = this.executer;

      cmd.stdin.setDefaultEncoding('utf-8');
      console.log(`${this.query}`);
      this.executer.stdin.write(`${this.query}\n`);
      this.executer.stdin.write(`${this.startYear}\n`);
      this.executer.stdin.write(`${this.endYear}\n`);
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