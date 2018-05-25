import Vue from 'vue'
import Router from 'vue-router'

const routerOptions = [
  { path: '/', component: 'Landing' },
  { path: '/signin', component: 'Signin' },
  { path: '/signup', component: 'Signup' },
  { path: '/home', component: 'Home' },
  { path: '/demo', component: 'demo/Demo' },
  { path: '/board', component: 'Board' },
  { path: '/boardwrite', component: 'BoardWrite' },
  { path: '/boardread', name: 'boardRead', component: 'BoardRead', props: true }

]

const routes = routerOptions.map(route => {
  return {
    ...route,
    component: () => import(`@/components/${route.component}.vue`)
  }
})

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes
})
