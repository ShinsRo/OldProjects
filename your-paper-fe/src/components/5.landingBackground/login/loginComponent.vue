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
    },
    clickForLogin () {
      if (this.user.id === '' || this.user.password === '') {
        this.wrongMessage = '로그인 정보를 입력해주세요'
      } else if (this.user.id !== '' && this.user.password !== '') {
        this.$axios({
          method: 'POST',
          url: 'http://www.siotman.com:19401/auth/login',
          data: {
            'username': this.user.id,
            'password': this.user.password
          },
          headers: { Authorization: `Basic ${btoa(`${this.user.id}:${this.user.password}`)}` }
        }).then(res => {
          console.log('then')
          this.sessionSet(res, `Basic ${btoa(`${this.user.id}:${this.user.password}`)}`)
          this.$store.dispatch('LOGIN_ACTION', res.data)
          this.$store.dispatch('ENCODING_ACTION', `Basic ${btoa(`${this.user.id}:${this.user.password}`)}`)
          if (this.$store.getters.memberInfoDtoGetter !== null) {
            this.$store.dispatch('LOAD_MY_PAPER_ACTION', 0)
            this.$router.push('./main')
          }
        }).catch(error => {
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
