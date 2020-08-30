import axios from "axios";

const state = {
  likeCount: 0
};

const getters = {};

const mutations = {};

const actions = {
  addLikeCount({ commit }, { postIdx, email }) {
    const resData = axios.post("/isgood", {
      Bulletin_idx: parseInt(postIdx),
      email
    });
    try {
      console.log(resData);
    } catch (e) {
      console.log(e);
    }
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
