import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/Home'
import Write from '../components/Write'


Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/write',
    name: 'Write',
    component: Write
  },
  {
    path: '/',
    name: 'login',
    component: () => import('../views/login.vue')
  },
  {
    path: '/confirm',
    name: 'confirm',
    component: () => import('../views/confirm.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/register.vue')
  },
  {
    path: '/endAuth',
    name: 'endAuth',
    component: () => import('../views/endAuth.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
