<template>
  <div class="signUpWrapper">
    <div class="idContainer">
      <input class="inputForId" type="text" placeholder="ID" v-model="signUpData.id">
      <button class="checkForId" type="button" v-on:click="availableCheck">ID 중복확인</button>
    </div> <!-- id container-->
    <p class="idCautionMessage">{{ message.id }}</p>
    <div class="passwordContainer">
      <input class="inputForPassword" type="password" placeholder="Password" v-model="signUpData.password">
      <p class="passwordCautionMessage">{{ message.passwordLength }}</p>
      <input class="checkForPassword" type="password" placeholder="Confirm Password" v-model="signUpData.confirmPassword">
      <p class="passwordCautionMessage">{{ message.confirmPassword }}</p>
    </div> <!-- password container-->
    <div class="authorContainer">
      <input class="inputForName" type="text" placeholder="Name" v-model="signUpData.name">
      <div class="inputForAuthorName">
        <input class="AuthorName" type="text" placeholder="Author Name">
        <button class="buttonForAuthorName" type="button">+</button>
      </div>
      <div class="selectBoxForOrganization">
        <select class="organization">
          <option value="sejongUniv" selected>세종대학교</option>
          <option value="konkukUniv">건국대학교</option>
          <option value="seoulUniv">서울대학교</option>
        </select>
      </div>
    </div> <!-- author container-->
    <div class="conditionContainer">
      <textarea class="conditions" cols="40" rows="5" readonly="readonly">본 서비스를 이용하기 위한 약관</textarea>
      <p class="checkForCondition"><input type="checkbox">Agree</p>
    </div>
    <div class="buttonContainer">
      <button class="buttonForCreate" type="button" v-on:click="clickForSignUp">Create</button>
      <button class="buttonForBack" type="button" v-on:click="clickForBack">Cancel</button>
    </div> <!-- button container-->
  </div>
</template>

<script>
export default {
  name: 'signUp',
  data () {
    return {
      message: {
        id: '',
        passwordLength: '',
        confirmPassword: ''
      },
      signUpData: {
        id: '',
        duplicationCheck: false,
        password: '',
        confirmPassword: '',
        name: '',
        authorName: '',
        organization: '',
        agree: false
      }
    }
  },
  watch: {
    'signUpData.id': function () {
      this.signUpData.duplicationCheck = false
    }, // 아이디 입력이 바뀔경우 중복확인 여부를 변경한다
    'signUpData.password': function () {
      this.checkPasswordLength()
      if (this.signUpData.confirmPassword !== '') {
        this.isSamePassword()
      }
    }, // password가 입력될 때마다 확인
    'signUpData.confirmPassword': function () {
      this.isSamePassword()
    } // 두번에 걸쳐 입력한 패스워드가 동일한지 감시한다
  },
  methods: {
    // changeIdCheck () {
    //   // 중복확인 후 아이디를 바꿀경우 중복확인 여부를 false로 변경해주는 메서드
    //   this.duplicationCheck = false
    // },
    availableCheck () {
      // 아이디의 중복확인을 해주는 메서드
      // 중복확인을 할 경우 아이디의 길이를 체크
      // 길이에 문제가 없을경우 axios 통신을 통해 중복여부 확인
      if (this.signUpData.id === '') {
        this.message.id = '아이디를 입력해주세요'
      } else if (this.signUpdata.id.length < 4 || this.signUpdata.id.length > 11) {
        this.message.id = '아이디는 5자 이상 12자 미만입니다'
      } else {
        this.message.id = ''
        this.$axios({
          method: 'POST',
          url: 'http://172.16.21.6:9401/auth/availableCheck',
          data: {
            'checkParameter': {
              'username': this.signUpData.id
            }
          }
        }).then(res => {
          if (res.data.status === true) {
            this.signUpData.duplicationCheck = true
            alert('사용가능한 아이디 입니다')
          } else {
            this.signUpData.duplicationCheck = false
            alert('이미 사용중인 아이디 입니다')
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    checkPasswordLength () {
      // 패스워드의 길이를 체크하는 메소드
      if (this.signUpData.password.length < 0 || this.signUpData.password.length > 11) {
        this.message.passwordLength = '비밀번호는 1자 이상 12자 미만입니다 '
      } else {
        this.message.passwordLength = ''
      }
    },
    isSamePassword () {
      // 두번 입력한 패스워드가 같은지 확인하는 메서드
      if (this.signUpData.password === this.signUpData.confirmPassword) {
        this.message.confirmPassword = ''
      } else {
        this.message.confirmPassword = '비밀번호가 일치하지 않습니다'
      }
    },
    clickForSignUp () {
      // 버튼 클릭시 사용자가 입력한 데이터가 유효한지 검사후 가입실행 하는 메서드
      console.log(this.signUpData)
    },
    clickForBack () {
      // 로그인 페이지로 돌아가기 위한 메서드
      this.$emit('changeFlag')
    }
  }
}
</script>

<style lang="scss">
  @import './signUpComponent.scss';
</style>
