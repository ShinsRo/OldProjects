import Vue from 'vue'
import Router from 'vue-router'

const routerOptions = [
  // { path: '/demo', component: 'demo/Demo' },
  // { path: '/board', component: 'Board' },
  // { path: '/boardwrite', component: 'BoardWrite' },
  // { path: '/boardread', name: 'boardRead', component: 'BoardRead', props: true },
   { path: '/menu', name: 'menuInsert', component: 'menu', props: true },
  // { path: '/boardread', name: 'boardRead', component: 'BoardRead', props: true },
  /**************/
  /* public path */
  { path: '/', component: 'public/Landing' },
  { path: '/signin', component: 'public/Signin' },
  { path: '/signup', component: 'public/Signup' },
  { path: '/menuList', component: 'public/MenuList' },
  /* admin path */
  { path: '/admin/home', component: 'admin/Home' },
  { path: '/admin/menuList', component: 'admin/MenuList' },
  { path: '/admin/userManagement', component: 'admin/UserManagement' },
  /* user path */
  { path: '/user/orderList', component: 'user/OrderList' },
  { path: '/home', component: 'user/Home' }
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
