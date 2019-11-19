<template>
  <div class="loginWrapper">
    <input class="formForId formElement" type="text" placeholder="ID" v-model="user.id" v-on:keyup.enter="clickForLogin">
    <input class="formForPassword formElement" type="password" placeholder="Password" v-model="user.password" v-on:keyup.enter="clickForLogin">
    <p class="loginErrorMessage">{{ wrongMessage }}</p>
    <button class="buttonForLogin formElement" type="button" v-on:click="clickForLogin">Login</button>
    <button class="buttonForSignUp formElement" type="button" v-on:click="clickForSignUp">SignUp</button>
  </div>
</template>

<script>

export default {
  name: 'login',
  data () {
    return {
      user: {
        id: '',
        password: ''
      },
      wrongMessage: ''
    }
  },
  methods: {
    clickForSignUp () {
      this.wrongMessage = ''
      this.$emit('changeFlag')
      // 회원가입을 위한 컴포넌트로 변경
      // 부모에 이벤트를 호출하여 다른 다른 컴포넌트를 보여주는 방식
    },
    clickForLogin () {
      if (this.user.id === '' || this.user.password === '') {
        this.wrongMessage = '로그인 정보를 입력해주세요'
        // 아이디나 비밀번호가 비어있을 경우
        // 메세지 출력
      } else if (this.user.id !== '' && this.user.password !== '') {
        // 아이디나 비밀번호가 비어있지 않을 경우
        this.$axios({
          method: 'POST',
          url: 'http://www.siotman.com:19401/auth/login',
          data: {
            'username': this.user.id,
            'password': this.user.password
          },
          headers: { Authorization: `Basic ${btoa(`${this.user.id}:${this.user.password}`)}` }
          // 암호화를 이용하여 base64로 인코딩한 뒤 요청
        }).then(res => {
          console.log('then')
          this.sessionSet(res, `Basic ${btoa(`${this.user.id}:${this.user.password}`)}`)
          // 정상적으로 처리될 경우
          if (sessionStorage.getItem('token') !== '') {
            // 세션스토리지에 토큰이 있을 경우 메인 페이지로 이동
            this.$router.push('./main')
          }
          // -------------------------------------------------
          // this.$store.dispatch('loginAction', res.data)
          // this.$store.dispatch('encodingAction', `Basic ${btoa(`${this.user.id}:${this.user.password}`)}`)
          // if (this.$store.getters.memberInfoDtoGetter !== null) {
          //   this.$store.dispatch('loadMyPaperAction', 0)
          //   this.$router.push('./main')
          // }
          // ----------------------------------------------
        }).catch(error => {
          // error catch code
          console.log(error)
          this.wrongMessage = '아이디와 비밀번호를 확인해주세요'
        })
      }
    },
    sessionSet (res, encodingData) {
      sessionStorage.setItem('data', JSON.stringify(res.data))
      sessionStorage.setItem('token', encodingData)
    }
  }
}
</script>

<style lang="scss">
  @import './loginComponent.scss';
</style>
