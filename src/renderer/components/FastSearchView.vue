<template>
  <v-layout row wrap>
    <v-flex xs12 class='mb-3'>
      <h1 class="headline">빠른 검색</h1>
      <v-divider></v-divider>
    </v-flex>
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
        @click="stdin"
        slot="activator"
        v-if="!loading"
      >
        검색
      </v-btn>
      <v-flex v-if="loading">
        <pulse-loader :loading="loading" :color="'#5bc0de'" :size="'20px'"></pulse-loader>
      </v-flex>
    </v-flex>
    <v-flex xs6>
      <v-text-field
        label="같이 검색할 소속 기관명"
        placeholder="입력값이 없으면 해당 필드는 조건에서 제외됩니다."
        outline
        v-model="organizaion"
        disabled
      ></v-text-field>
    </v-flex>
    <v-flex xs4>
      <v-select
          :items="gubuns"
          v-model="gubun"
          label="검색구분"
          outline
        ></v-select>
    </v-flex>
    <!-- END 옵션 컨테이너 -->
    <!-- 옵션 컨테이너 -->

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
          빠른 검색 결과
          <v-spacer></v-spacer>
          <v-text-field
            v-model="listSearch"
            append-icon="search"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-btn @click="resFlush">결과 비우기</v-btn>
        <v-data-table
          :headers="resHeaders"
          :items="fResList"
          :search="listSearch"
          :loading="loading"
          :pagination.sync="pagination"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >
                <v-icon @click="deleteRow(props.item)">delete</v-icon>
              </td>
              <td>{{ props.item.TI }}</td>
              <td>{{ props.item.DI }}</td>
              <td>{{ props.item.TC }}</td>
              <!-- <td>{{ props.item.AU }}</td>
              <td>{{ props.item.PD }}</td>
              <td>{{ props.item.SO }}</td>
              <td>{{ props.item.IS }}</td> -->
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            "{{ listSearch }}"에 관한 검색 결과가 없습니다.
          </v-alert>
          <template slot="expand" slot-scope="props">
            저자 목록 : {{ props.item.AU }} <br>
            출판일 : {{ props.item.PD }} <br>
            발행처 : {{ props.item.SO }} <br>
            권, 호 : {{ props.item.IS }} / {{ props.item.VL }} <br>
            페이지 : {{ props.item.BP }} - {{ props.item.EP }} <br>
          </template>
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 결과표 -->
    <!-- 검색 실패표 -->
    <v-flex>
      <v-card class='mt-3'>
        <v-card-title>
          검색어 에러 메세지
          <v-spacer></v-spacer>
        </v-card-title>
        <v-data-table
          :headers="errQueryHeaders"
          :items="fErrQuery"
        >
          <template slot="items" slot-scope="props">
            <tr>
              <td v-html="`검색어 : ${ props.item.query[0] }<br> 기준저자 : ${ props.item.query[1] }`"></td>
              <td >{{ props.item.msg }}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 검색 실패표 -->
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';
import ExcelDownloader from './ExcelDownloader/ExcelDownloader';

export default {
  props: ['fResList', 'fErrQuery', 'loading', 'executer', 'log'],
  data() {
    return {
      errObj: {
        show: false,
        code: 200,
        msg: '입력 값이 잘못되었습니다',
      },
      query: '',
      pAuthors: '',
      startYear: '',
      endYear: '',
      gubun: '논문제목',
      gubuns: ['논문제목', 'DOI', '저자명'],
      gubunsId: {
        논문제목: 'TI',
        저자명: 'AU',
        연구기관: 'AD',
        DOI: 'DO',
      },
      organizaion: 'Sejong Univ',
      listSearch: '',
      pagination: {
        sortBy: 'index',
        descending: true,
      },
      resHeaders: [
        { text: '행삭제', align: 'left', value: '__icon', width: '1px', sortable: false },
        { text: '제목', align: 'left', value: 'TI', width: '20px' },
        { text: 'DOI', align: 'left', value: 'DI', width: '20px' },
        { text: '피인용', align: 'left', value: 'TC', width: '5px' },
      ],
      errQueryHeaders: [
        { text: '검색어', align: 'left', value: 'query', width: '20px' },
        { text: '메세지', align: 'left', value: 'msg', width: '20px' },
      ],
    };
  },
  components: {
    PulseLoader,
    ExcelDownloader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    resFlush() {
      this.fResList.splice(0, this.fResList.length);
    },
    deleteRow(item) {
      if (confirm('정말 행을 삭제합니까?')) {
        const index = this.fResList.indexOf(item);
        this.fResList.splice(index, 1);
      }
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
        inputs: ['fastSearch', this.query, this.startYear, this.endYear, this.pAuthors, this.organizaion, this.gubunsId[this.gubun]],
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