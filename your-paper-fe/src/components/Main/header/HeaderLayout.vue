<template>
    <div>
      <div id="headerLogoLayout">
        <p>
          Your Paper
        </p>

      </div> <!--헤더 (왼쪽) 로고 레이아웃-->
      <div id="headerSettingLayout">
        <div id="headerSettingTabLayout">
          <div class="headerSettingTab">
            <p>
              My Paper
            </p>
          </div>
          <div class="headerSettingTab">
            <p>
              Paper Statics
            </p>
          </div>
        </div> <!--헤더 설정 화면탭 레이아웃-->
        <div id="headerSettingUserLayout">
          <div class="headerSettingUser">
            <img src="../../../assets/image/image-03.png"/>
          </div>
          <div class="headerSettingUser">
            <p>
              SuHyoun
            </p>
          </div>
          <div class="headerSettingUser">
            <p>
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

<style scoped>
  #headerLogoLayout{
    display: flex;
    align-items : center;
    justify-content: center;
    width: 10%;
    height: 100%;
  }
  #headerLogoLayout > p {
    color: white;
    font-size: 20px;
  }

  #headerSettingLayout{
    display: flex;
    float: right;
    width: 40%;
    height: 100%;
    justify-self: flex-end;
  }

  #headerSettingTabLayout{
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 60%;
  }

  .headerSettingTab > p{
    color: white;
    font-size: 20px;
  }

  #headerSettingUserLayout{
    display: flex;
    width: 40%;
    justify-content: space-around;
    align-items: center;
  }

  .headerSettingUser {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 80%;
    width: 30%;
  }

  .headerSettingUser > img{
    height: 100%;

  }

  .headerSettingUser > p{
    color: white;
    font-size: 15px;
  }



</style>
