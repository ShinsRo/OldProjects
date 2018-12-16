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
      path: '/FastSearch',
      name: 'FastSearch',
      component: require('@/components/FastSearchView').default,
    },
    {
      path: '/SingleSearch',
      name: 'SingleSearch',
      component: require('@/components/SingleSearchView').default,
    },
    {
      path: '/MultiSearch',
      name: 'MultiSearch',
      component: require('@/components/MultiSearchView').default,
    },
    {
      path: '/citationSearchByAuthor',
      name: 'citationSearchByAuthor',
      component: require('@/components/citationSearchByAuthorView').default,
    },
    {
      path: '/excelIntegration',
      name: 'excelIntegration',
      component: require('@/components/excelIntegrationView').default,
    },
    {
      path: '/SendMail',
      name: 'SendMail',
      component: require('@/components/SendMailView').default,
    },
    // {
    //   path: '/TEST',
    //   name: 'TEST',
    //   component: require('@/components/TEST').default,
    // },
    {
      path: '*',
      redirect: '/',
    },
  ],
});
