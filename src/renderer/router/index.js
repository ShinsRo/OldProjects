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
      path: '/commonSearch',
      name: 'commonSearch',
      component: require('@/components/commonSearchView').default,
    },
    {
      path: '/citationSearch',
      name: 'citationSearch',
      component: require('@/components/citationSearchView').default,
    },
    {
      path: '*',
      redirect: '/',
    },
  ],
});
