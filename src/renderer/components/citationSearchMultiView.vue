<template>
  <v-layout row wrap>
    <!-- 옵션 컨테이너 -->
    <v-flex xs4>
      <v-select
          :items="gubuns"
          v-model="gubun"
          label="검색구분"
          outline
        ></v-select>
    </v-flex>
    <v-flex xs6>
      <div v-if="file" class="text-xs-right" style="padding: 15px;">
        {{file.path.slice(0,40)}} {{(file.path.length > 39)? "...":""}}
      </div>
      <div v-else class="text-xs-right" style="padding: 15px;">
        파일 경로
      </div>
      
    </v-flex>
    <v-flex xs2 class="text-xs-right">
      <file-select v-model="file"></file-select>
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
    <v-spacer></v-spacer>
    <v-flex xs2 class="text-xs-right">
      <v-btn
        @click="stdin"
        slot="activator"
        v-if="!loading"
      >
        불러오기
      </v-btn>
      <pulse-loader :loading="loading" :color="'#5bc0de'" :size="'20px'"></pulse-loader>
    </v-flex>
    <!-- END 옵션 컨테이너 -->
    <!-- 인풋 에러 얼럿 -->
    <v-flex xs12>
      <v-alert
      :value="errObj.show"
      type="error"
      >
        {{ errObj.msg }}
      </v-alert>
    </v-flex>
    <!-- END 인풋 에러 얼럿 -->
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
          :items="mResList"
          :search="listSearch"
          :loading="loading"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >{{ (props.item.title.length > 8)? `${props.item.title.slice(0, 10)}...` : props.item.title }}</td>
              <td >{{ props.item.reprint.replace(/\(.+\)/, '') }}</td>
              <td v-html="
                `${props.item.authors
                        .slice(0, 3)
                        .join([separator = '<br>'])}
                        ${(props.item.authorsCnt>3)? '<br>...':''}`">
              </td>
              <td >{{ props.item.docType }}</td>
              <td >{{ props.item.publishedMonth }}</td>
              <td v-html="props.item.publisher[0]"></td>
              <td >{{ props.item.timesCited }}</td>
              <td v-html="props.item.capedGrades.join([separator = '<br>'])"></td>
              <td v-html="props.item.ivp.join([separator = '<br>'])"></td>
              <td >{{ props.item.language }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            Your search for "{{ listSearch }}" found no results.
          </v-alert>
          <template slot="expand" slot-scope="props">
            <h2 style="margin: 20px"><v-icon v-html="'assignment'"></v-icon> : {{ props.item.title }}</h2>
            <!-- IF 테이블 -->
            <table 
            v-if="Object.keys(props.item.impact_factor).length"
            style="border:1px solid #FFFFFF; margin: 20px; width: 25%">
              <tr v-for="(value, key, index) in props.item.impact_factor" :key="index">
                <td style="border:1px solid #FFFFFF;">{{key}}</td>
                <td style="border:1px solid #FFFFFF;">{{value}}</td>
              </tr>
            </table>
            <!-- JCR 테이블 -->
            <table 
            v-if="props.item.jcr.length"
            style="border:1px solid #FFFFFF; margin: 20px;">
              <tr v-for="(row, index) in props.item.jcr" :key="index">
                <td v-for="col in row" :key="col" style="border:1px solid #FFFFFF;">{{col}}</td>
              </tr>
            </table>
            <table style="border:1px solid #FFFFFF; margin: 20px; width: 100%">
              <tr>
                <td style="border:1px solid #FFFFFF;">이 논문의 저자 목록</td>
                <td style="border:1px solid #FFFFFF;">연구기관</td>
              </tr>
              <tr 
              v-if="props.item.addresses"
              v-for="(value, key, index) in props.item.addresses" :key="index">
                <td style="border:1px solid #FFFFFF;">{{ key }}</td>
                <td v-html="value.join([separator = '<br>'])" style="border:1px solid #FFFFFF;"></td>
              </tr>
            </table>
            <!-- 인용 테이블 -->
            <table style="border:1px solid #FFFFFF; margin: 20px; width: 100%">
              <tr>
                <td style="border:1px solid #FFFFFF;">파일 ID : {{props.item.citingArticles.id}}</td>
              </tr>
              <tr>
                <td style="border:1px solid #FFFFFF;">이 논문을 인용하는 논문</td>
                <td style="border:1px solid #FFFFFF;">논문 저자 목록</td>
              </tr>
              <tr v-if="props.item.citingArticles.titles.length > 10">
                <td>인용한 논문이 너무 많습니다. 엑셀파일을 확인해 주세요.</td><td></td>
              </tr>
              <tr
              v-if="props.item.citingArticles.titles.length <= 10"
              v-for="(title, index) in props.item.citingArticles.titles" :key="index"
              >
                <td style="border:1px solid #FFFFFF;">{{title}}</td>
                <td style="border:1px solid #FFFFFF;">{{props.item.citingArticles.authors[index]}}</td>
              </tr>
            </table>
          </template>
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 결과표 -->
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';
import FileSelect from './citationSearchMultiView/FileSelect.vue';

export default {
  props: ['mResList', 'loading', 'executer', 'log'],
  data() {
    return {
      errObj: {
        show: false,
        code: 200,
        msg: '입력 값이 잘못되었습니다',
      },
      startYear: '2010',
      endYear: '2018',
      gubun: '논문제목',
      gubuns: ['논문제목', 'DOI'],
      gubunsId: {
        논문제목: 'TI',
        저자명: 'AU',
        연구기관: 'AD',
        DOI: 'DO',
      },
      file: null,
      inputFilePath: 'C:\\input.csv',
      listSearch: '',
      resHeaders: [
        {
          text: '제목',
          align: 'left',
          sortable: true,
          value: 'title',
          width: '20px',
        },
        { text: '교신저자', align: 'left', value: 'reprint', width: '30px' },
        { text: '저자 목록', align: 'left', value: 'authors', width: '30px' },
        { text: '발행분류', align: 'left', value: 'docType', width: '10px' },
        { text: '발행년월', align: 'left', value: 'publishedMonth', width: '5px' },
        { text: '발행처', align: 'left', value: 'publisher', width: '20px' },
        { text: '피인용', align: 'left', value: 'timesCited', width: '5px' },
        { text: '등급', align: 'left', value: 'capedGrades', width: '20px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: '언어', align: 'left', value: 'language', width: '10px' },
      ],
    };
  },
  components: {
    FileSelect,
    PulseLoader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    stdin() {
      // 인풋값 체크
      const errObj = this.errObj;
      try {
        this.startYear.trim();
        const intSY = parseInt(this.startYear, 10);
        const intEY = parseInt(this.endYear, 10);
        const intNY = parseInt((new Date()).getFullYear(), 10);
        if (!this.startYear || this.startYear.length !== 4) {
          errObj.code = 100;
          errObj.msg = '시작년도를 확인해주세요.';
        } else if (!this.endYear || this.endYear.length !== 4) {
          errObj.code = 101;
          errObj.msg = '끝 년도를 확인해주세요.';
        } else if (
          !(intSY >= 1900 && intSY <= intNY) ||
          !(intEY >= 1900 && intEY <= intNY)
        ) {
          errObj.code = 102;
          errObj.msg = '년도는 1900년에서 금년 + 1 사이여야 합니다.';
        } else if (intSY > intEY) {
          errObj.code = 103;
          errObj.msg = '시작년도가 끝 년도보다 최근일 수 없습니다.';
        } else {
          errObj.code = false;
        }
        if (errObj.code) { throw errObj; }
      } catch (e) {
        errObj.show = true;
        return;
      }
      errObj.show = false;
      const payload = {
        scope: this,
        inputs: ['multiCitationSearch', this.startYear, this.endYear, this.gubunsId[this.gubun], this.inputFilePath],
      };
      this.$emit('stdin', payload);
    },
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