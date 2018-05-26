<template>
  <v-app>
    <!-- 왼쪽 사이드 바 -->
    <v-navigation-drawer v-model="sidebar" app>
      <v-list>
        <v-list-tile
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>{{ item.title }}</v-list-tile-content>
        </v-list-tile>
      </v-list-tile>
        <!-- 로그아웃 메뉴 -->
        <v-list-tile v-if="isAuthenticated" @click= "userSignOut">
          <v-list-tile-action>
            <v-icon>exit_to_app</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>Sign Out</v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>

    <!-- 상단 툴 바 -->
    <v-toolbar app>
      <span class="hidden-sm-and-up">
        <v-toolbar-side-icon @click="sidebar = !sidebar">
        </v-toolbar-side-icon>
      </span>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
          {{ appTitle }}
        </router-link>
        <!-- 모달 주석 -->
        <!--
        <v-btn @click.stop="showSignUpDialog=true">
                  test SignUp modal
                </v-btn>
        <v-btn @click.stop="showSignInDialog=true">
          test SignIn modal
        </v-btn>
        <SignUpDialog :visible="showSignUpDialog" @close="showSignUpDialog=false"></SignUpDialog>
        <SignInDialog :visible="showSignInDialog" @close="showSignInDialog=false"></SignInDialog>
      -->
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn
          flat
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          <v-icon left dark>{{ item.icon }}</v-icon>
          {{ item.title }}
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-content>
      <router-view></router-view>
    </v-content>

  </v-app>
</template>

<script>
/* eslint-disable */
  import SignUpDialog from './components/public/SignUpDialog'
  import SignInDialog from './components/public/SignInDialog'
  /*
  App.vue computed 속성 명세
  computed 기능은 다음과 같습니다.
  1. getSessionAttribute :User
    세션 정보를 {email: "xxx@xxx.xxx", password: "", name: "xxx"} 형식으로 불러옵니다.

  2. isAuthenticated :Bool
    현재 세션 정보가 유효한 지 검사합니다.

  3. menuItems : Object[]
    nav의 메뉴바 정보를 불러옵니다.
    dropDown 항목이 트루면 하위 항목이 존재합니다.
    폴트면 하위 항목은 없습니다.
  ****************/
  export default {
    data () {
      return {
        appTitle: 'Midas Cafe',
        sidebar: false,
        showSignUpDialog: false,
        showSignInDialog: false
      }
    },
    components: {
      SignUpDialog,
      SignInDialog
    },
    methods: {
      userSignOut () {
        this.$store.dispatch('userSignOut')
      }
    },
    computed: {
      getSessionAttribute () {
        return this.$store.getters.getSessionAttribute
      },
      isAuthenticated () {
        return this.$store.getters.isAuthenticated
      },
      menuItems () {
        if (this.isAuthenticated === '0') {
          return [
            {
              title: 'Dropdown',
              icon: 'home',
              dropDown: true,
              dropDownMenu: [
                {title: 'item1', path: '/home', icon: 'home'},
                {title: 'item2', path: '/home', icon: 'home'}
              ]
            },
            {title: 'Home', path: '/home', icon: 'home', dropDown: false}
          ]
        } else  if (this.isAuthenticated === '1' || this.isAuthenticated === '9') {
          return [
            {title: 'Menu List', path: '/MenuList', icon: 'list', dropDown: false},
            {title: 'User Management', path: '/Management', icon: 'list', dropDown: false}
            // {
            //   title: '회원관리',
            //   icon: 'home',
            //   path: '/home',
            //   dropDown: true,
            //   dropDownMenu: [
            //     {title: 'item1', path: '/home', icon: 'home'},
            //     {title: 'item2', path: '/home', icon: 'home'}
            //   ]
            // },
          ]
        }
        else {
          return [
            // {
            //   title: 'Dropdown4',
            //   icon: 'home',
            //   dropDown: true,
            //   dropDownMenu: [
            //     {title: 'item1', path: '/home', icon: 'home'},
            //     {title: 'item2', path: '/home', icon: 'home'}
            //   ]
            // },
            {title: 'Menu List', path: '/MenuList', icon: 'list', dropDown: false},
            {title: 'Sign Up', path: '/signup', icon: 'face', dropDown: false},
            {title: 'Sign In', path: '/signin', icon: 'lock_open', dropDown: false}
          ]
        }
      }
    }
  }
</script>
