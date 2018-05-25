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
        <v-btn @click.stop="showSignUpDialog=true">
          test SignUp modal
        </v-btn>
        <v-btn @click.stop="showSignInDialog=true">
          test SignIn modal
        </v-btn>
        <SignUpDialog :visible="showSignUpDialog" @close="showSignUpDialog=false"></SignUpDialog>
        <SignInDialog :visible="showSignInDialog" @close="showSignInDialog=false"></SignInDialog>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- 메뉴 -->
      <v-toolbar-items class="hidden-xs-only">
        <v-menu offset-y
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          <v-btn slot="activator" flat>
            <v-icon left dark>{{ item.icon }}</v-icon>
            <v-flex>{{ item.title }}</v-flex>
          </v-btn>
          <v-list
            v-if="item.dropDown"
            v-for="dropDownItem in item.dropDownMenu"
            :key="dropDownItem.title"
            :to="dropDownItem.path">
            <v-list-tile>{{ dropDownItem.title }}</v-list-tile>
          </v-list>
        </v-menu>
        <!-- 로그아웃 메뉴 -->
          <v-btn flat v-if="isAuthenticated" @click="userSignOut">
            <v-icon left>exit_to_app</v-icon>
            Sign Out
          </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-content>
      <router-view></router-view>
    </v-content>

  </v-app>
</template>

<script>
  import SignUpDialog from './components/SignUpDialog'
  import SignInDialog from './components/SignInDialog'
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
        appTitle: 'Midas App',
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
        if (this.isAuthenticated) {
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
        } else {
          return [
            {
              title: 'Dropdown4',
              icon: 'home',
              dropDown: true,
              dropDownMenu: [
                {title: 'item1', path: '/home', icon: 'home'},
                {title: 'item2', path: '/home', icon: 'home'}
              ]
            },
            {title: 'Sign Up', path: '/signup', icon: 'face', dropDown: false},
            {title: 'Sign In', path: '/signin', icon: 'lock_open', dropDown: false}
          ]
        }
      }
    }
  }
</script>
