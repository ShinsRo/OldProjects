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
        @click="stdin"
        slot="activator"
        v-if="!loading"
      >
        불러오기
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
          :items="cResList"
          :search="listSearch"
          :loading="loading"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <tr @click="props.expanded = !props.expanded">
              <td >{{ props.item['Title'] }}</td>
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
        </v-data-table>
      </v-card>
    </v-flex>
    <!-- END 결과표 -->
  </v-layout>
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader.vue';

export default {
  props: ['cResList', 'loading', 'executer', 'log'],
  data() {
    return {
      query: '',
      startYear: '',
      endYear: '',
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
    PulseLoader,
  },
  methods: {
    logFlush() {
      this.log = '';
    },
    stdin() {
      console.log('stdin func');
      const payload = {
        scope: this,
        inputs: ['singleCitationSearch', this.query, this.startYear, this.endYear],
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