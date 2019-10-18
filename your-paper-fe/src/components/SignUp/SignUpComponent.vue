<template>
  <div class="wrapper">
    <div class="FormContainer">
      <div class="idFormContainer">
        <input class="basicInputBox idInputBox" type="text" placeholder="ID">
        <button class="idCheckBtn" type="button">ID 중복확인</button>
      </div>
      <input class="basicInputBox pwInputBox" type="password" placeholder="Password">
      <input class="basicInputBox pwCheckBox" type="password" placeholder="Confirm Password">
      <input class="basicInputBox nameInputBox" type="text" placeholder="Name">
    </div>
    <div class="ConditionForm">
      <textarea class="conditionBox" readonly="readonly" cols="30" rows="10" style="resize:none;">약관</textarea>
      <div class="agreeCheckbox">
        Agree<input type="checkbox">
      </div>
    </div>
    <div class="buttonContainer">
      <button class="basicBtn createBtn" type="button">Create</button>
      <button class="basicBtn cancelBtn" type="button">Cancel</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'signUp'
}
</script>

<style lang="scss">
  @import './SignUpComponent.scss';
</style>
