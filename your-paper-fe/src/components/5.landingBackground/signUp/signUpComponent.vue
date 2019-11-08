<template>
  <div class="signUpComponentWrapper">
    <div class="idContainer">
      <div class="idInputContainer">
        <input class="idInputBox" type="text" placeholder="ID" v-model="registerInput.id">
        <button class="idAvailableButton" type="button" v-on:click="avaliableCheck">ID 중복확인</button>
      </div>
      <p class="cautionMessage idMessage">{{ message.id }}</p>
    </div>
    <!-- 아이디 입력을 위한 컨테이너 -->
    <div class="passwordContainer">
      <input class="passwordInputBox" type="password" placeholder="Passsword" v-model="registerInput.password">
      <p class="cautionMessage passwordMessage">{{ message.passwordLength }}</p>
      <input class="confirmPasswordInputBox" type="password" placeholder="Confirm Password" v-model="registerInput.confirmPassword">
      <p class="cautionMessage confirmPasswordMessage">{{ message.confirmPassword}}</p>
    </div>
    <!-- 비밀번호 입력을 위한 컨테이너 -->
    <div class="nameContainer">
      <input class="nameInputBox" type="text" placeholder="Name" v-model="registerInput.name">
      <p class="cautionMessage nameMessage">{{ message.name }}</p>
    </div>
    <!-- 이름 입력을 위한 컨테이너 -->
    <div class="authorNameContainer">
      <div class="authorNameInputContainer">
        <input class="authorNameInputBox" type="text" placeholder="Author Name" v-model="authorName" v-on:keyup.enter="addAuthorName">
        <button class="authorNameAddButton" type="button" v-on:click="addAuthorName">+</button>
      </div>
      <!-- 저자 명을 입력받기 위한 컨테이너 -->
      <div class="authorNameShowContainer">
        <div class="authorNameList" v-for="(authorName, index) in registerInput.authorNameList" :key="index">
          <p class="authorNameVal">{{ authorName }}</p>
          <button class="authorNameRemoveButton" type="button" v-on:click="removeAuthorName(index)">X</button>
        </div>
      </div>
      <!-- 입력한 저자 명을 보여주기 위한 컨테이너 -->
    </div>
    <!-- 저자 이름 입력과 출력을 위한 컨테이너 -->
    <div class="organizationContainer">
      <select class="organizationSelectBox" v-model="registerInput.organization">
        <option disabled value="" style="display: none;">Organization</option>
        <option value="sejong Univ">Sejong Univ</option>
        <option value="konkuk Univ">Konkuk Univ</option>
        <option value="seoul Univ">Seoul Univ</option>
      </select>
    </div>
    <!-- 연구 기관 설정을 위한 컨테이너 -->
    <div class="conditionContainer">
      <textarea class="conditions" cols="40" rows="2" readonly> 본 서비스를 이용하기 위한 약관</textarea>
      <p class="checkForCondition"><input type="checkbox" v-model="registerInput.agree">Agree</p>
    </div>
    <!-- 약관을 위한 컨테이너 -->
    <div class="buttonContainer">
      <button class="createButton" type="button" v-on:click="clickForSignUp">Create</button>
      <button class="backButton" type="button" v-on:click="clickForBack">Cancel</button>
    </div>
    <!-- 버튼을 위한 컨테이너 -->
  </div>
</template>

<script>
export default {
  name: 'signUp',
  data () {
    return {
      authorName: '',
      message: {
        id: '',
        passwordLength: '',
        confirmPassword: '',
        name: ''
      },
      registerInput: {
        id: '',
        duplicationCheck: false,
        password: '',
        confirmPassword: '',
        name: '',
        authorNameList: [],
        organization: '',
        agree: false
      }
    }
  },
  watch: {
    'registerInput.id': function () {
      this.registerInput.duplicationCheck = false
      if (this.registerInput.id === '') { // 아이디가 공백일시 메세지
        this.message.id = '아이디를 입력해주세요'
      } else if (this.registerInput.id.length < 5 || this.registerInput.id.length > 11) { // 아이디의 길이 제한에 대한 메세지
        this.message.id = '아이디는 5자이상 12자 미만입니다'
      } else { // 공백도 아니고 길이 제한도 맞추었을 경우
        this.message.id = ''
      }
    }, // 아이디 입력이 바뀔 경우 유효성 검사 후 중복확인 여부를 false로 변경
    'registerInput.password': function () {
      this.checkPasswordLength()
      if (this.registerInput.confirmPassword !== '') {
        this.isSamePassword()
      }
    }, // password가 입력댈 때 마다 길이 및 일치 여부 확인
    'registerInput.confirmPassword': function () {
      this.isSamePassword()
    } // 두번에 걸쳐 입력한 패스워드가 동일한지 감시한다
  },
  methods: {
    avaliableCheck () {
      if (this.registerInput.id === '') {
        this.message.id = '아이디를 입력해주세요'
      } else {
        this.message.id = ''
        this.$axios({
          method: 'POST',
          url: 'http://www.siotman.com:19401/auth/availableCheck',
          data: {
            'username': this.registerInput.id
          }
        }).then(res => {
          if (res.data === true) {
            this.registerInput.duplicationCheck = true
            alert('사용가능한 아이디 입니다')
          } else {
            this.registerInput.duplicationCheck = false
            alert('이미 사용중인 아이디 입니다')
          }
        }).catch(error => {
          console.log(error)
        })
      }
    }, // 아이디 중복확인을 해주는 메서드
    checkPasswordLength () {
      if (this.registerInput.password.length < 1 || this.registerInput.password.length > 11) {
        this.message.passwordLength = '비밀번호는 1자 이상 12자 미만입니다 '
      } else {
        this.message.passwordLength = ''
      }
    },
    isSamePassword () {
      // 두번 입력한 패스워드가 같은지 확인하는 메서드
      if (this.registerInput.password === this.registerInput.confirmPassword) {
        this.message.confirmPassword = ''
      } else {
        this.message.confirmPassword = '비밀번호가 일치하지 않습니다'
      }
    },
    addAuthorName () {
      // 저자명을 넣어주는 기능
      if (this.authorName === '') {
        alert('저자명을 입력후 추가해주세요')
      } else {
        this.registerInput.authorNameList.push(this.authorName)
        this.authorName = ''
      }
    },
    removeAuthorName (val) {
      // 등록한 저자명을 삭제해주는 기능
      this.registerInput.authorNameList.splice(val, 1)
      this.addAuthorCounter -= 1
    },
    clickForSignUp () {
      // 버튼 클릭시 사용자가 입력한 데이터가 유효한지 검사후 가입실행 하는 메서드
      if (this.registerInput.id === '') {
        // 아디 공백 여부 체크
        alert('아이디를 입력해주세요')
      } else if (!this.registerInput.duplicationCheck) {
        // 중복 체크 여부 확인
        alert('아이디 중복체크를 진행해주세요')
      } else if (this.registerInput.password === '' || (this.registerInput.password !== this.registerInput.confirmPassword)) {
        // 비밀번호 공백여부 및 일치 여부 확인
        alert('비밀번호를 확인해주세요')
      } else if (this.registerInput.name === '') {
        // 이름의 공백여부 확인
        alert('이름을 입력해주세요')
      } else if (this.registerInput.organization === '') {
        // 연구기관 선택여부 확인
        alert('연구기관을 골라주세요')
      } else if (!this.registerInput.agree) {
        // 약관 동의 여부 확인
        alert('약관에 동의해주세요')
      } else {
        this.$axios({
          method: 'POST',
          url: 'http://www.siotman.com:19401/auth/register',
          data: {
            'username': this.registerInput.id,
            'password': this.registerInput.password,
            'memberInfoDto': {
              'name': this.registerInput.name,
              'authorNameList': this.registerInput.authorNameList,
              'organizationList': [
                this.registerInput.organization
              ]
            }
          }
        }).then(res => {
          // 세션추가 자리
          if (res.data.username === this.registerInput.id) {
            alert('회원가입 되었습니다')
            this.$emit('changeFlag')
          } else {
            alert('다시 시도해주세요')
          }
        }).catch(error => {
          console.log(error)
        })
      }
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
