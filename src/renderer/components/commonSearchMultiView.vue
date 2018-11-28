<template>
  <v-layout row wrap>
    <v-flex xs12 class='mb-3'>
      <h1 class="headline">일반 엑셀 검색</h1>
      <v-divider></v-divider>
    </v-flex>
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
          :items="cResList"
          :search="listSearch"
          :loading="loading"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >{{ (props.item['Title'].length > 8)? `${props.item['Title'].slice(0, 8)}...` : props.item['Title'] }}</td>
              <td v-html="
                `${props.item['Authors']
                        .slice(0, 3)
                        .join([separator = '<br>'])}
                        ${(props.item['Authors'].length > 3)? '<br>...':''}`">
              <td >{{ props.item['Publication Date'] }}</td>
              <td >{{ props.item['Source Title'] }}</td>
              <td >{{ props.item['Total Citations'] }}</td>
              <td v-html="props.item.ivp.join([separator = '<br>'])"></td>
              <td >{{ props.item['DOI'] }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            Your search for "{{ listSearch }}" found no results.
          </v-alert>
          <template slot="expand" slot-scope="props">
            <h2 style="margin: 20px"><v-icon v-html="'assignment'"></v-icon> : {{ props.item['Title'] }}</h2>
            <table style="border:1px solid #FFFFFF; margin: 20px; width: 100%">
              <tr><td style="border:1px solid #FFFFFF;">이 논문의 저자 목록</td></tr>
              <tr><td style="border:1px solid #FFFFFF;" :key="10">
                {{ props.item['Authors'].join([separator = '; ']) }}</td></tr>
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
import FileSelect from './commonSearchMultiView/FileSelect.vue';

export default {
  props: ['cResList', 'notFoundList', 'loading', 'executer', 'log'],
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
      gubuns: ['논문제목', '저자명', '연구기관', 'DOI'],
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
          value: 'Title',
          width: '20px',
        },
        { text: '저자 목록', align: 'left', value: 'Authors', width: '30px' },
        { text: '발행년월', align: 'left', value: 'Publication Date', width: '5px' },
        { text: '발행처', align: 'left', value: 'Source Title', width: '20px' },
        { text: '피인용', align: 'left', value: 'Total Citations', width: '5px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: 'DOI', align: 'left', value: 'DOI', width: '5px' },
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
        } else if (this.file === null) {
          errObj.code = 104;
          errObj.msg = '파일 경로가 선택되지 않았습니다.';
        } else {
          this.inputFilePath = this.file.path;
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
        inputs: ['multiCommonSearch', this.startYear, this.endYear, this.gubunsId[this.gubun], this.inputFilePath, 0],
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