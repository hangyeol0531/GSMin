import axios from "axios";
import { router } from "../../router/index.js";

const state = {
  userInfo: null,
  nickname: '',
  token: localStorage.getItem("token")
};

const getters = {};

const mutations = {
  login(state, payload) {
    localStorage.setItem("token", payload);
    state.token = payload;
  },
  getUserInfo(state, payload) {
    state.userInfo = payload;
  },
  logout(state) {
    state.token = null;
    localStorage.removeItem("token");
  }
};

const actions = {
  async login({ commit }, { email, pw }) {
    const resData = await axios.post("/login_check", {
      email,
      pw
    });
    try {
      console.log(resData);
      commit("login", resData.data.token);
      axios.defaults.headers.common["Authorization"] = resData.data.token;
      router.push({ name: "Home" });
    } catch (e) {
      alert("아이디 또는 비밀번호를 확인해주세요.");
      console.log(e);
    }
  },

  async getUserInfo({ commit }) {
    let getToken = localStorage.getItem("token");
    const resData = await axios.post("/receive_token_inforation", {
      user_token: getToken
    });
    try {
      console.log(resData)
      let userInfo = {
        user_email: resData.data.user_email,
        user_nickname: resData.data.nickname,
        user_grade: resData.data.grade
      };
      commit("getUserInfo", userInfo);
    } catch (e) {
      console.log(e);
    }
  },

  async changeNick({ commit }, { email , chNickname}) {
    const resData = await axios.post("/update_nickname", {
      email,
      change_nickname: chNickname
    });
    try {
      alert("성공적으로 변경 되었습니다.");
      commit("login", resData.data.token)
      router.push({ name: "Home" });
    } catch (e) {
      alert(e);
    }
  },

  async logOut({ commit }) {
    commit("logout");
    router.push({ name: "login" });
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
