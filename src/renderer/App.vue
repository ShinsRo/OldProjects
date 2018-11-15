<template>
  <div id="app">
    <v-app dark>
      <!-- 드로워 정의 -->
      <v-navigation-drawer
        fixed
        :mini-variant="miniVariant"
        :clipped="clipped"
        v-model="drawer"
        app
        permanent
      >
        <v-list>
          <v-list-tile>
            <v-list-tile-action>
              <v-btn 
                icon
                @click.native.stop="miniVariant = !miniVariant"
              >
                <v-icon v-html="miniVariant ? 'chevron_right' : 'chevron_left'"></v-icon>
              </v-btn>
            </v-list-tile-action>
          </v-list-tile>
        </v-list>
        <v-list>
          <v-list-tile 
            router
            :to="item.to"
            :key="i"
            v-for="(item, i) in items"
            exact
          >
            <v-list-tile-action>
              <v-icon v-html="item.icon"></v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title v-text="item.title"></v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list>
      </v-navigation-drawer>
      <!-- END 드로워 정의 -->

      <!-- 헤더 라인 정의 -->
      <v-toolbar fixed app :clipped-left="clipped" dense style="-webkit-app-region: drag">
        <v-toolbar-title v-text="title"></v-toolbar-title>
        <v-spacer></v-spacer>
        <v-tooltip bottom style="-webkit-app-region: no-drag">
            <v-btn
            icon
            slot="activator"
            @click="winMinimize"
            >
              <v-icon v-html="'minimize'"></v-icon>
            </v-btn>
            <v-btn
            icon
            slot="activator"
            @click="winClose"
            >
              <v-icon v-html="'close'"></v-icon>
            </v-btn>

        </v-tooltip>
        
      </v-toolbar>
      <!-- END 헤더 라인 정의 -->

      <!-- content -->
      <v-content>
        <v-container fluid fill-height>
          <v-slide-y-transition mode="out-in">
            <router-view></router-view>
          </v-slide-y-transition>
        </v-container>
      </v-content>
      <!-- END content -->
      
      <v-footer :fixed="fixed" app>
        <v-spacer></v-spacer>
        <span>&copy; 2018 Sejong Univ.</span>
        <v-spacer></v-spacer>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
  import { remote } from 'electron';

  export default {
    name: 'sejong-wos',
    data: () => ({
      clipped: true,
      drawer: true,
      fixed: false,
      items: [
        { icon: 'info', title: '가이드', to: '/' },
        { icon: 'search', title: '논문 검색', to: '/commonSearch' },
        { icon: 'format_quote', title: '피인용 횟수/논문 검색', to: '/citationSearch' },
      ],
      miniVariant: true,
      // right: true,
      // rightDrawer: false,
      title: '세종대학교 논문 정보 검색 시스템',
    }),
    methods: {
      winClose: () => {
        window.close();
      },
      winMinimize: () => {
        remote.BrowserWindow.getFocusedWindow().minimize();
      },
    },
  };
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons');
  /* Global CSS */
</style>
