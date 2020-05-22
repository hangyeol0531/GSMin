import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/login'
import confirm from '../components/confirm'
import register from '../components/register'
import endAuth from '../components/endAuth'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/confirm',
    name: 'confirm',
    component: confirm
  },
  {
    path: '/register',
    name: 'register',
    component: register
  },
  {
    path: '/endAuth',
    name: 'endAuth',
    component: endAuth
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
