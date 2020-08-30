import axios from "axios";

const state = {
  likeCount: 0
};

const getters = {};

const mutations = {
  getLikeCount(state, payload) {
    state.likeCount = payload
  },
  addLikeCount(state) {
    state.likeCount +=1
  },
  subtractionCount(state) {
    state.likeCount -=1
  }
};

const actions = {
  addLikeCount({ commit }, { postIdx, email }) {
    axios.post("/isgood", {
      Bulletin_idx: String(postIdx),
      email
    })
    .then((response) => {
      response.data === '추가' ? commit("addLikeCount") : commit("subtractionCount")
    })
    .catch((e) => {
      console.log(e)
    })
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
