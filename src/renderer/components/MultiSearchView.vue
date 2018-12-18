<template>
  <v-layout row wrap>
    <v-flex xs12 class='mb-3'>
      <h1 class="headline">엑셀로 검색하기</h1>
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
        검색
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
        <excel-downloader
          :resList="mResList"
          :errQuery="mErrQuery"
          :loading="loading"
        ></excel-downloader>
        <v-data-table
          :headers="resHeaders"
          :items="mResList"
          :search="listSearch"
          :loading="loading"
          :pagination.sync="pagination"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >
                <v-icon @click="deleteRow(props.item.index)">delete</v-icon>
              </td>
              <td >{{ props.item.index }}</td>
              <!-- <td >{{ props.item.subsidy }}</td> -->
              <td >{{ (props.item.title.length > 8)? `${props.item.title.slice(0, 10)}...` : props.item.title }}</td>
              <td >{{ props.item.reprint.replace(/\(.+\).+/, '').replace(' ', '') }}</td>
              <td v-html="
                `${props.item.authors.map((x => x.replace(' ', '')))
                        .slice(0, 3)
                        .join([separator = '<br>'])}
                        ${(props.item.authorsCnt>3)? '<br>...':''}`">
              </td>
              <td >{{ props.item.docType }}</td>
              <td >{{ props.item.publishedMonth }}</td>
              <td v-html="props.item.capedGrades.join([separator = '<br>'])"></td>
              <td >{{ props.item.goodRank }}</td>
              <td >{{ props.item.prevYearIF }}</td>
              <td >{{ props.item.timesCited }}</td>
              <td v-html="props.item.publisher[0]"></td>
              <td >{{ props.item.issn }}</td>
              <td v-html="props.item.ivp.join([separator = '<br>'])"></td>
              <td >{{ props.item.language }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            "{{ listSearch }}"에 관한 검색 결과가 없습니다.
          </v-alert>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >
                <v-icon @click="deleteRow(props.item.index)">delete</v-icon>
              </td>
              <td >{{ props.item.index }}</td>
              <!-- <td >{{ props.item.subsidy }}</td> -->
              <td >{{ (props.item.title.length > 8)? `${props.item.title.slice(0, 10)}...` : props.item.title }}</td>
              <td >{{ props.item.reprint.replace(/\(.+\).+/, '').replace(' ', '') }}</td>
              <td v-html="
                `${props.item.authors.map((x => x.replace(' ', '')))
                        .slice(0, 3)
                        .join([separator = '<br>'])}
                        ${(props.item.authors.length>3)? '<br>...':''}`">
              </td>
              <td >{{ props.item.docType }}</td>
              <td >{{ props.item.publishedMonth }}</td>
              <td v-html="props.item.capedGrades.join([separator = '<br>'])"></td>
              <td >{{ props.item.goodRank }}</td>
              <td >{{ props.item.prevYearIF }}</td>
              <td >{{ props.item.timesCited }}</td>
              <td v-html="props.item.publisher[0]"></td>
              <td >{{ props.item.issn }}</td>
              <td v-html="props.item.ivp.join([separator = '<br>'])"></td>
              <td >{{ props.item.language }}</td>
            </tr>
          </template>
          <v-alert slot="no-results" :value="true" color="error" icon="warning">
            "{{ listSearch }}"에 관한 검색 결과가 없습니다.
          </v-alert>
          <template slot="expand" slot-scope="props">
            <v-layout class="black">
              <v-flex xs8>
                <h1 style="margin: 20px; padding: 10px;">{{ props.item.title }}</h1>
              </v-flex>
            </v-layout>
            <!-- 인용년도 테이블 -->
            <h2 class="black" style="margin: 20px; padding: 10px;"><font>TIMES CITED PER A YEAR, IN RANGE 10 YEARS</font></h2>
            <table class="black" style="margin: 20px; border-collapse: collapse;">
              <tr style=""><td v-for="year in 10" :key="year"
              >{{intNY - 10 + year}}</td></tr>
              <tr
              ><td v-for="year in 10" :key="year"
              >{{props.item.tc_data[intNY - 10 + year]}}</td></tr>
            </table>
            <!-- IF 테이블 -->
            <h2 class="black" style="margin: 20px; padding: 10px;"><font>IMPACT FACTOR TABLE</font></h2>
            <table class="black"
            v-if="Object.keys(props.item.impact_factor).length"
            style="margin: 20px; width: 25%; border-collapse: collapse;">
              <tr v-for="(value, key, index) in props.item.impact_factor" :key="index">
                <td style="border-right:1px solid grey;">{{key}}</td>
                <td>{{value}}</td>
              </tr>
            </table>
            <h2 class="black" style="margin: 20px; padding: 10px;"><font>JCR RANKS TABLE WITH TOP-PERCENTAGE</font></h2>
            <!-- JCR 테이블 -->
            <table class="black"
            v-if="props.item.jcr.length"
            style="margin: 20px; border-collapse: collapse;">
              <tr v-for="(row, index) in props.item.jcr" :key="index"
              >
                <td v-for="col in row" :key="col">{{col}}</td>
              </tr>
            </table>
            <!-- 저자목록 테이블 -->
            <h2 class="black" style="margin: 20px; padding: 10px;"><font>ADDRESSES</font></h2>
            <table class="black" style="margin: 20px; width: 60%; border-collapse: collapse;">
              <tr>
                <td>이 논문의 저자 목록</td>
                <td>연구기관</td>
              </tr>
              <tr 
              v-if="props.item.addresses"
              v-for="(value, key, index) in props.item.addresses" :key="index">
                <td>{{ key }}</td>
                <td v-html="value.join([separator = '<br>'])"></td>
              </tr>
            </table>
            <!-- 인용 테이블 -->
            <h2 class="black" style="margin: 20px; padding: 10px;"><font>CITING ARTICLES</font></h2>
            <table class="black" style="margin: 20px; width: 97.4%; border-collapse: collapse;">
              <tr>
                <td>
                  ID : {{props.item.citingArticles.id}}
                </td>
                <td>
                  자기인용 횟수 : {{props.item.citingArticles.selfCitation}}, 
                  타인인용 횟수 : {{props.item.citingArticles.othersCitation}}
                </td>
              </tr>
              <tr>
                <td>인용 분류</td>
                <td>이 논문을 인용하는 논문</td>
                <td>논문 저자 목록</td>
              </tr>
              <tr
              v-for="(title, index) in props.item.citingArticles.titles" :key="index"
              >
                <td>{{props.item.citingArticles.isSelf[index]}}</td>
                <td>{{title}}</td>
                <td>{{props.item.citingArticles.authors[index]}}</td>
              </tr>
            </table>
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
          :items="mErrQuery"
        >
          <template slot="items" slot-scope="props">
            <tr>
              <td 
              v-html="`검색어 : ${ props.item.query[0] }
              <br> 기준저자 : ${ props.item.query[1]? props.item.query[1]:'없음' }
              <br> 연관기관 : ${ props.item.query[2]? props.item.query[2]:'없음' }`"></td>
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
import FileSelect from './MultiSearchView/FileSelect.vue';
import ExcelDownloader from './ExcelDownloader/ExcelDownloader';

export default {
  props: ['mErrQuery', 'mResList', 'loading', 'executer', 'log'],
  data() {
    return {
      intNY: parseInt((new Date()).getFullYear(), 10),
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
      pagination: {
        sortBy: 'index',
        descending: true,
      },
      resHeaders: [
        { text: '행삭제', align: 'left', value: '__icon', width: '5px', sortable: false },
        { text: '번호', align: 'left', value: 'index', width: '5px' },
        // { text: '예상 논문 게재 장려금', align: 'left', value: 'subsidy', width: '20px' },
        { text: '제목', align: 'left', value: 'title', width: '20px' },
        { text: '교신저자', align: 'left', value: 'reprint', width: '20px' },
        { text: '저자 목록', align: 'left', value: 'authors', width: '20px' },
        { text: '발행분류', align: 'left', value: 'docType', width: '10px' },
        { text: '발행년월', align: 'left', value: 'publishedMonth', width: '5px' },
        { text: '등급', align: 'left', value: 'capedGrades', width: '20px' },
        { text: '백분율', align: 'left', value: 'goodRank', width: '5px' },
        { text: 'IF', align: 'left', value: 'prevYearIF', width: '5px' },
        { text: '피인용', align: 'left', value: 'timesCited', width: '5px' },
        { text: '발행처', align: 'left', value: 'publisher', width: '20px' },
        { text: 'ISSN', align: 'left', value: 'issn', width: '5px' },
        { text: '권/호, 페이지', align: 'left', value: 'ivp', width: '5px' },
        { text: '언어', align: 'left', value: 'language', width: '10px' },
      ],
      errQueryHeaders: [
        { text: '검색어', align: 'left', value: 'query', width: '20px' },
        { text: '메세지', align: 'left', value: 'msg', width: '20px' },
      ],
    };
  },
  components: {
    FileSelect,
    PulseLoader,
    ExcelDownloader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    deleteRow(index) {
      for (let ii = 0; ii < this.mResList.length; ii += 1) {
        if (this.mResList[ii].index === index) {
          index = ii;
          break;
        }
      }
      if (confirm('정말 행을 삭제합니까?')) {
        this.mResList.splice(index, 1);
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
        inputs: ['multiSearch', this.startYear, this.endYear, this.gubunsId[this.gubun], this.inputFilePath],
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