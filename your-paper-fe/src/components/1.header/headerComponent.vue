<template>
  <div id="headerLayout">

    <div id="headerLogoLayout">
      <p class="logoName">
        Your Paper
      </p>
      <p class="subLogoName">
        Paper Management System
      </p>
    </div> <!--헤더 (왼쪽) 로고 레이아웃-->

    <div id="headerSettingLayout">
      <div id="headerSettingTabLayout">
        <div class="headerSettingTab">
          <p class="text" v-on:click="clickForMyPaper">
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

        <div class="headerSettingUser img">
          <img class="userImage"
               src="../../assets/images/avatar.png"/>
        </div><!--사용자 아이콘-->

        <div class="headerSettingUser">
          <p class="text">
            SuHyoun
          </p>
        </div><!--사용자 이름-->

        <div class="headerSettingUser"
             @mouseover="isDropBoxShow=setTrue()"
             @mouseleave="isDropBoxShow=setFalse()"
             style="cursor: pointer;">

          <p class="text">
            menu
          </p>

          <div id="dropBox"
               v-show="isDropBoxShow">
            <div class="textWrapper">
              <p class="text" v-on:click="clickForPaperEdit">
                Paper Edit
              </p>
            </div>
            <hr class="divideLine"/>
            <div class="textWrapper" v-on:click="clickForLogout">
              <p class="text">
                Log Out
              </p>
            </div>
          </div><!--메뉴 드롭박스-->

        </div><!--메뉴 탭-->

      </div> <!--헤더 설정 사용자 정보 레이아웃-->

    </div> <!--헤더 (오른쪽) 설정 레이아웃-->

  </div> <!--헤더 전체 레이아웃-->
</template>

<script>
export default {
  name: 'HeaderLayout',
  data () {
    return {
      isDropBoxShow: false
    }
  },
  methods: {
    setTrue () {
      return true
    },
    setFalse () {
      return false
    },
    clickForMyPaper () {
      this.$emit('changeFlag', 1)
    },
    clickForPaperEdit () {
      this.$emit('changeFlag', 2)
    },
    clickForLogout () {
      this.$store.dispatch('logoutAction')
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss">
  @import './HeaderComponent.scss';
</style>
