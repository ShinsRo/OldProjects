<template>
    <div>
      <div id="headerLogoLayout">
        <p class="logoName">
          Your Paper
        </p>

      </div> <!--헤더 (왼쪽) 로고 레이아웃-->
      <div id="headerSettingLayout">
        <div id="headerSettingTabLayout">
          <div class="headerSettingTab">
            <p class="text">
              My Paper
            </p>
          </div>
          <div class="headerSettingTab">
            <p class="text">
              Paper Statics
            </p>
          </div>
        </div> <!--헤더 설정 화면탭 레이아웃-->
        <div id="headerSettingUserLayout">
          <div class="headerSettingUser">
            <img class="userImage" src="../../../assets/image/image-03.png"/>
          </div>
          <div class="headerSettingUser">
            <p class="text">
              SuHyoun
            </p>
          </div>
          <div class="headerSettingUser">
            <p class="text">
              menu
            </p>
          </div>

        </div> <!--헤더 설정 사용자 정보 레이아웃-->

      </div> <!--헤더 (오른쪽) 설정 레이아웃-->
    </div> <!--헤더 전체 레이아웃-->
</template>

<script>
    export default {
        name: "HeaderLayout"
    }
</script>

<style lang="scss">
  @import './HeaderLayout.scss';
</style>
