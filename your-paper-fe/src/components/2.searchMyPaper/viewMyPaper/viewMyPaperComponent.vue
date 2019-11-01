<template>
  <div id="viewMyPaperComponentLayout">
    <div>
      <div class="mainOptionFilter optionCheck">
        <div class="mainOptionFilterContent">
          <input class="check" type="checkbox"/>
          <p class="text">
            주 저자 여부
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="checkbox"/>
          <p class="text">
            피 인용수
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="checkbox"/>
          <p class="text">
            권, 호, 페이지
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="checkbox"/>
          <p class="text">
            상태 표시
          </p>
        </div>
        <div class="mainOptionFilterContent">
          <input class="check" type="checkbox"/>
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

    <paperDataComponent class="paperComponentLayout"></paperDataComponent>
    <paperDataComponent class="paperComponentLayout"></paperDataComponent>
    <paperDataComponent class="paperComponentLayout"></paperDataComponent>
  </div>
</template>

<script>
import paperDataComponent from './paperData/paperDataComponent.vue'
export default {
  name: 'MainList',
  components: {
    'paperDataComponent': paperDataComponent
  }
}
</script>

<style lang="scss">
  @import './viewMyPaperComponent.scss';

</style>
