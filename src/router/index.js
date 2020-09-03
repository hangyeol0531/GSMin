import Vue from "vue";
import VueRouter from "vue-router";
import { store } from "../store";

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push
   VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

const isAuthenticated = (to, from, next) => {
  if (store.state.auth.token === null || store.state.auth.token === undefined) {
    alert("잘못된 접근입니다 로그인을 해주세요.");
    next("/");
  } else {
    next();
  }
};

const onlyAuthenticated = (to, from, next) => {
  if (store.state.auth.token != undefined) {
    alert("잘못된 접근입니다 로그아웃을 해주세요.");
    next("/home");
  } else {
    next();
  }
};

const routes = [
  {
    path: "/home",
    name: "Home",
    component: () => import("../views/Home.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/hireList",
    name: "hireList",
    component: () => import("../views/hireList.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/board",
    name: "Board",
    component: () => import("../views/Board.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/subBoard",
    name: "subBoard",
    component: () => import("../views/subBoard.vue"),
    props: true,
    beforeEnter: isAuthenticated
  },
  {
    path: "/eachBoard",
    name: "eachBaord",
    component: () => import("../views/eachBaord.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/myBoard",
    name: "myBoard",
    component: () => import("../views/myBoard.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/write",
    name: "Write",
    component: () => import("../views/Write.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/setting",
    name: "Setting",
    component: () => import("../views/Setting.vue"),
    beforeEnter: isAuthenticated
  },
  {
    path: "/",
    name: "login",
    beforeEnter: onlyAuthenticated,
    component: () => import("../views/login.vue")
  },
  {
    path: "/confirm",
    name: "confirm",
    beforeEnter: onlyAuthenticated,
    component: () => import("../views/confirm.vue")
  },
  {
    path: "/register",
    name: "register",
    beforeEnter: onlyAuthenticated,
    component: () => import("../views/register.vue")
  },
  {
    path: "/endAuth",
    name: "endAuth",
    beforeEnter: onlyAuthenticated,
    component: () => import("../views/endAuth.vue")
  }
];

export const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});
