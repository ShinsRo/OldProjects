<template>
  <v-layout column justify-center>
    <v-flex>
      <blockquote class="text-xs-center">
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
      </blockquote>
      <div class="text-xs-left">
        <pre>{{ content }}</pre>
      </div>
    </v-flex>
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';
import { spawn } from 'child_process';
// import { Tail } from 'tail';
// import { fs } from 'fs';

export default {
  data() {
    return {
      pymsg: 'not yet',
      state: 'ready',
      loading: false,
      executer: '',
      content: '',
    };
  },
  created() {
    const cmd = spawn('cmd.exe');
    const rInFormat = /lineout:\{(.+)\}/;
    this.executer = cmd;

    cmd.stdin.setDefaultEncoding('utf-8');
    cmd.stdout.setDefaultEncoding('utf-8');
    cmd.stderr.setDefaultEncoding('utf-8');

    // cmd.stdout.on('data', (data) => {
    //   // console.log(`cmd stdout: ${data.toString()}`);
    //   // this.content += data.toString();
    // });

    cmd.stderr.on('data', (data) => {
      console.log(`cmd stderr: ${data.toString()}`);
      let output = rInFormat.exec(data.toString());
      if (output) {
        output = `${output[0].replace(rInFormat, '$1')}`;
        if (output[0] === '@') {
          console.log(output);
          this.content += `${output}\n`;
        } else {
          this.content += `${output}\n`;
        }
      } else {
        this.content = '알 수 없는 오류가 발생했습니다.';
      }
    });

    cmd.on('close', (code) => {
      console.log(`cmd child process exited with code ${code.toString()}`);
    });
  },
  components: {
    PulseLoader,
  },
  methods: {
    search() {
      if (this.loading) return;
      this.loading = true;
      // this.executer.stdin.write(`echo hello${this.counter}\n`);
      this.executer.stdin.write('python src/pyscripts/script.py\n');
      // shell.send('pleeeeeese');
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