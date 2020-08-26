import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../components/Home";
import Board from "../components/Board";
import Write from "../components/Write";
import Viewer from "../components/Viewer";
import Setting from "../components/Setting";
import { store } from "../store";

Vue.use(VueRouter);

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
    component: Home,
    beforeEnter: isAuthenticated
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    beforeEnter: isAuthenticated
  },
  {
    path: "/write",
    name: "Write",
    component: Write,
    beforeEnter: isAuthenticated
  },
  {
    path: "/viewer",
    name: "Viewer",
    component: Viewer,
    beforeEnter: isAuthenticated
  },
  {
    path: "/setting",
    name: "Setting",
    component: Setting,
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
