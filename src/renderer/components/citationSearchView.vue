<template>
  <v-layout row wrap>
    <!-- 옵션 컨테이너 -->
    <v-flex xs6>
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
    <v-flex xs2 class="text-xs-center">
      <v-btn
        @click="search"
        slot="activator"
        v-if="true"
      >
        LOAD
      </v-btn>
      <v-flex v-if="loading">
        <pulse-loader :loading="loading" :color="'#5bc0de'" :size="'20px'"></pulse-loader>
      </v-flex>
    </v-flex>
    <!-- END 옵션 컨테이너 -->
    <!-- 결과표 -->
    <v-flex xs12>
      <v-card>
        <v-card-title>
          검색 대상 논문 정보
          <v-spacer></v-spacer>
          <v-text-field
            v-model="listSearch"
            append-icon="search"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="resHeaders"
          :items="resList"
          :search="listSearch"
          :loading="loading"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >{{ (props.item.title.length > 16)? `${props.item.title.slice(0, 15)}...` : props.item.title }}</td>
              <td v-html="props.item.authors.join([separator = '<br>'])"></td>
              <td >{{ props.item.reprint }}</td>
              <td >{{ props.item.docType }}</td>
              <td v-html="props.item.ivp.join([separator = '<br>'])"></td>
              <td >{{ props.item.publishedMonth }}</td>
              <td v-html="props.item.publisher[0]"></td>
              <td >{{ props.item.timesCited }}</td>
              <td v-html="props.item.capedGrades.join([separator = '<br>'])"></td>
              <td >{{ props.item.language }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            Your search for "{{ listSearch }}" found no results.
          </v-alert>
          <template slot="expand" slot-scope="props">
            <table style="border:1px solid #FFFFFF; margin: 20px; width: 25%">
              <tr v-for="(value, key) in props.item.impact_factor" :key="value+key">
                <td style="border:1px solid #FFFFFF;">{{key}}</td>
                <td style="border:1px solid #FFFFFF;">{{value}}</td>
              </tr>
            </table>
            <table style="border:1px solid #FFFFFF; margin: 20px;">
              <tr v-for="row in props.item.jcr" :key="row">
                <td v-for="col in row" :key="col" style="border:1px solid #FFFFFF;">{{col}}</td>
              </tr>
            </table>
            <table style="border:1px solid #FFFFFF; margin: 20px; width: 100%">
              <tr>
                <td style="border:1px solid #FFFFFF;">이 논문을 인용하는 논문</td>
                <td style="border:1px solid #FFFFFF;">논문 저자 목록</td>
              </tr>
              <tr v-for="(title, index) in props.item.citingArticles.titles" :key="title">
                <td style="border:1px solid #FFFFFF;">{{title}}</td>
                <td style="border:1px solid #FFFFFF;">{{props.item.citingArticles.authors[index]}}</td>
              </tr>
            </table>
          </template>
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 결과표 -->
    <v-flex xs12 mb12>
      <br>
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
// import { fs } from 'fs';

export default {
  data() {
    return {
      pymsg: 'not yet',
      state: 'ready',
      loading: false,
      // executer: spawn('cmd.exe'),
      executer: spawn('cmd.exe'),
      log: '',
      gubun: 0,
      query: '',
      startYear: '',
      endYear: '',
      listSearch: '',
      resHeaders: [
        {
          text: '제목',
          align: 'left',
          sortable: true,
          value: 'title',
          width: '20px',
        },
        { text: '저자 목록', align: 'left', value: 'authors', width: '30px' },
        { text: '교신저자', align: 'left', value: 'reprint', width: '30px' },
        { text: '발행분류', align: 'left', value: 'docType', width: '10px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        // { text: '호', align: 'left', value: 'volume', width: '1px' },
        { text: '발행년월', align: 'left', value: 'publishedMonth', width: '5px' },
        { text: '발행처', align: 'left', value: 'publisher', width: '20px' },
        { text: '피인용', align: 'left', value: 'timesCited', width: '5px' },
        // { text: '등급', align: 'left', value: 'grades', width: '20px' },
        { text: '등급', align: 'left', value: 'capedGrades', width: '20px' },
        { text: '언어', align: 'left', value: 'language', width: '10px' },
      ],
      resList: [
        {
          id: '123123',
          title: 'sadkjdsfalkdsajflksadkjdsfalkdsajflksadkjdsfalkdsajflksadkjdsfalkdsajflk',
          authors: ['sadkjdsfalkdsajflk', 'Kasd, DD', 'Asdfasfasdfaf, D, RQWE'],
          authorsCnt: 'sadkjdsfalkdsajflksdfajl;aksdf',
          doi: '3',
          capedGrades: ['SICE', 'SCI'],
          volume: '4',
          issue: '5',
          pages: '333-1414',
          ivp: ['4', '5', '333-1414'],
          published: '',
          publishedMonth: 'nov 2020',
          publisher: ['asdasd', 'asdasdasdasdasdasd'],
          impact_factor: { 2017: '2.111', '5 year': '1.442' },
          timesCited: '2',
          grades: ['asdasd', 'asdasdasdasdasdasd'],
          docType: 'Article',
          researchAreas: '',
          language: '',
          reprint: '',
          jcr: [
            ['JCR® Category', 'Rank in Category', 'Quartile in Category'],
            ['BIOCHEMISTRY & MOLECULAR BIOLOGY', '1 of 293', 'Q1'],
            ['CELL BIOLOGY', '2 of 190', 'Q1'],
            ['MEDICINE, RESEARCH & EXPERIMENTAL', '1 of 133', 'Q1'],
          ],
          citingArticles: {
            id: 102020,
            titles: ['asdasdasdasdaas', 'asdasdasdasdasd'],
            authors: ['ASDSAD DD; asdasdasd; asdasd;', 'ADADA asdasd; asdasd'],
          },
        },
      ],
    };
  },
  components: {
    PulseLoader,
  },
  created() {
    const rInFormat = /time:(.+)#@lineout:(.+)/gm;
    const cmd = this.executer;

    cmd.stdin.setDefaultEncoding('utf-8');
    cmd.stdout.setDefaultEncoding('utf-8');
    cmd.stderr.setDefaultEncoding('utf-8');

    cmd.stderr.on('data', (data) => {
      console.log(`cmd stderr: ${data.toString()}`);
      const output = data.toString().replace(/\n/ig, '').split('#&');
      console.log(output);
      let time = '';
      let resJSON = '';
      // const rawFile = new XMLHttpRequest();
      // rawFile.onreadystatechange = () => {
      //   if (rawFile.readyState === 4) {
      //     if (rawFile.status === 200 || rawFile.status === 0) {
      //       const allText = rawFile.responseText;
      //       resJSON = JSON.parse(allText).res;
      //     }
      //   }
      // };
      for (let i = 0; i < output.length; i += 1) {
        if (!output[i].match(rInFormat) || !output[i]) break;
        time = RegExp.$1;
        console.log(resJSON);
        resJSON = JSON.parse(RegExp.$2);

        // if (resJSON.command === 'tooLong') {
        //   // rawFile.open('GET', resJSON.path, false);
        //   // rawFile.send(null);
        //   const resFileUrl = new URL(`file://${resJSON.path}`);
        //   const temp = fs.readFileSync(resFileUrl, 'r');
        //   resJSON = JSON.parse(temp);
        //   fs.unlinkSync(resFileUrl);
        // }
        // 모듈로부터의 결과
        switch (resJSON.command) {
          case 'err':
          case 'log':
          case 'sysErr':
            this.log = `${time} : ${decodeURIComponent(resJSON.msg)}<br>${this.log}`;
            break;
          case 'res':
            if (resJSON.target === 'loading') {
              this.loading = resJSON.res;
            } else if (resJSON.target === 'paperData') {
              this.resList.push(resJSON.res);
            } else if (resJSON.target === 'citingArticles') {
              for (let ii = 0; ii < this.resList.length; ii += 1) {
                console.log(resJSON.res.id);
                if (this.resList[ii].id === resJSON.res.id) {
                  this.resList[ii].citingArticles = resJSON.res;
                  break;
                }
              }
            } else {
              this.log = `${time} : ${JSON.stringify(resJSON.res)}<br>${this.log}`;
            }
            break;
          default:
            this.log = `${time} : ${decodeURIComponent(resJSON.msg)}<br>${this.log}`;
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