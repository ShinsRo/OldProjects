import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'welcome-view',
      component: require('@/components/WelcomeView').default,
    },
    {
      path: '/citationSearch',
      name: 'citationSearch',
      component: require('@/components/citationSearchView').default,
    },
    {
      path: '/citationSearchMulti',
      name: 'citationSearchMulti',
      component: require('@/components/citationSearchMultiView').default,
    },
    {
      path: '/commonSearchMulti',
      name: 'commonSearchMulti',
      component: require('@/components/commonSearchMultiView').default,
    },
    {
      path: '*',
      redirect: '/',
    },
  ],
});
