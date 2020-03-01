<template>
  <div id="viewMyPaperComponentLayout">
    <div>
      <div class="mainOptionFilter optionCheck">
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.authorStatus" type="checkbox"/>
          <p class="text">
            저자 상태
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.loadStatus" type="checkbox"/>
          <p class="text">
            논문 상태
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.quotation" type="checkbox"/>
          <p class="text">
            피 인용수
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.pages" type="checkbox"/>
          <p class="text">
            권, 호, 페이지
          </p>
        </div>

        <div class="mainOptionFilterContent">
          <input class="check" v-model="viewToggle.url" type="checkbox"/>
          <p class="text">
            URL
          </p>
        </div>
      </div>

      <div class="mainOptionFilter" style="border-bottom: none; padding-left: 40px;">
        <div class="mainOptionOrderName">
          <p class="text">
            최신 순
          </p>
        </div>
        <div class="mainOptionOrderName">
          <p class="text">
            인용 순
          </p>
        </div>
      </div>
    </div>
    <paperDataComponent class="paperComponentLayout"
    :view-toggle="viewToggle"
    v-for="(paper, index) in paperData" :key="index" :paper="paper"></paperDataComponent>
  </div>
</template>

<script>
// import { PaperRecordContainer, SORT_MP_ENUM } from '../../../../public/apis/api/paper-api.js'
import paperDataComponent from './paperData/paperDataComponent.vue'
export default {
  name: 'MainList',
  components: {
    'paperDataComponent': paperDataComponent
  },
  props:['paperData'],
  data () {
    return {
      viewToggle: {
        authorStatus: true,
        loadStatus: true,
        quotation: true,
        pages: true,
        url: true
      },
    }
  },
}

</script>

<style lang="scss">
  @import './viewMyPaperComponent.scss';

</style>
