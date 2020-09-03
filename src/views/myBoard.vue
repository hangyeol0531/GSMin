<template>
  <div>
    <v-app>
      <top-bar ref="topBar"></top-bar>
      <v-content>
        <v-toolbar prominent height="250px" src="../assets/school_img.jpg"></v-toolbar>
        <v-container class="bg fill-height" fluid>
          <v-row>
            <v-col md="2"></v-col>

            <v-col md="2">
              <side-bar></side-bar>
            </v-col>

            <v-col md="6">
              <v-card class="mx-auto fixed" style="margin-top: -300px;">
                <v-card flat>
                  <v-card-title class="headline font-weight-bold">
                    <v-row>
                      <v-col cols="7">
                        <div v-if="$route.query.name === 'board'">내가 쓴 게시물</div>
                        <div v-else>내가 쓴 댓글</div>
                      </v-col>
                    </v-row>
                  </v-card-title>
                  <v-card v-for="(listItem, index) in calData" :key="index" flat>
                    <v-card @click="eachBoard(listItem.idx)" hover>
                      <v-card-text>
                        <div v-if="resBoard === false">{{ resText }}</div>
                        <tbody v-else>
                          <tr>
                            <td class="like">
                              <v-icon small v-if="listItem.good_count">thumb_up_alt</v-icon>{{listItem.good_count}}
                              <div v-if="listItem.good_count === null"></div>
                            </td>
                            <td class="section">
                              <v-chip label v-if="listItem.type">{{listItem.type}}</v-chip>
                              <div v-if="listItem.type === undefined"></div>
                            </td>
                            <td class="content">
                              <strong v-if="listItem.title">{{ listItem.title }}</strong>
                              <strong v-if="listItem.comment">{{ listItem.comment }}</strong>
                            </td>
                            <td class="writer font-weight-bold">
                              <div>
                                <v-img
                                  v-if="listItem.grade === 1"
                                  src="../assets/one_icon.png"
                                  width="20"
                                  style="float:left"
                                ></v-img>
                                <v-img
                                  v-if="listItem.grade === 2"
                                  src="../assets/two_icon.png"
                                  width="20"
                                  style="float:left"
                                ></v-img>
                                <v-img
                                  v-if="listItem.grade === 3"
                                  src="../assets/three_icon.png"
                                  width="20"
                                  style="float:left"
                                ></v-img>
                                {{listItem.nickname}}
                              </div>
                            </td>
                            <td class="viewer">{{listItem.view_count}}</td>
                            <td class="previous">{{ listItem.date.split("T")[0] }}</td>
                          </tr>
                        </tbody>
                      </v-card-text>
                    </v-card>
                  </v-card>
                  <v-card>
                    <v-pagination v-model="curPageNum" :length="numOfPages" :value="selectedPage"></v-pagination>
                    <v-card-text align="right"></v-card-text>
                  </v-card>
                </v-card>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-content>
    </v-app>
  </div>
</template>

<script>
import sideBar from "./sideBar.vue";
import topBar from "./topBar.vue";
import swal from "sweetalert";

export default {
  components: {
    sideBar,
    topBar,
  },

  props: {
    name: {},
  },

  data() {
    return {
      categoryItems: [
        { text: "자유", value: "자유" },
        { text: "질문", value: "질문" },
        { text: "꿀팁", value: "꿀팁" },
        { text: "1학년", value: "1학년" },
        { text: "2학년", value: "2학년" },
        { text: "3학년", value: "3학년" },
        { text: "졸업생", value: "졸업생" },
      ],
      categorySelect: { text: "자유", value: "자유" },
      listData: [],
      dataPerPage: 10,
      curPageNum: 1,
      search: "",
      category: "",
      searchData: [],
      resBoard: {},
      resText: "게시판이 비어있어요",
      resLength: "",
      subCategory: "",
      selectedPage: "",
      calData: [],
    };
  },

  async created() {
    await this.$store.dispatch("auth/getUserInfo");

    let option = this.$route.query.name === "board" ? "b" : "c";
    let isHover = option === 'b' ? true : false

    await this.$http
      .post("/get_my_list", {
        page_num: String(this.curPageNum),
        email: this.$store.state.auth.userInfo.user_email,
        b_c: option,
      })
      .then((res) => {
        this.resBoard = true;
        this.calData = res.data;
      })
      .catch((e) => {
        this.resBoard = false;
      });

    await this.$http
      .post("/board_num", {
        email: this.$store.state.auth.userInfo.user_email,
      })
      .then((res) => {
        option === "b"
          ? (this.resLength = res.data.Bulletin_count)
          : (this.resLength = res.data.Comment_count);
      });
  },

  methods: {
    searchBoard(page) {
      let option = this.$route.query.name === "board" ? "b" : "c";
      this.$http
        .post("/get_my_list", {
          page_num: page,
          email: this.$store.state.auth.userInfo.user_email,
          b_c: option,
        })
        .then((res) => {
          this.resBoard = true;
          this.calData = res.data;
        })
        .catch((e) => {
          swal("이런!", "게시판이 비어 있습니다", "error");
        });
    },
    eachBoard(postIdx) {
      this.$router.replace({ path: "/eachBoard", query: { postIdx } });
    },
  },

  watch: {
    categorySelect: function (value) {
      this.category = value.value;
    },
    curPageNum: function (page) {
      this.searchBoard(page);
    },
  },

  computed: {
    startOffset() {
      return (this.curPageNum - 1) * this.dataPerPage;
    },
    endOffset() {
      return this.startOffset + this.dataPerPage;
    },
    numOfPages() {
      if (this.listData === null) {
        swal("이런!", "게시판이 비어 있습니다", "error");
      } else {
        return Math.ceil(this.resLength / this.dataPerPage);
      }
    },
  },
};
</script>

<style>
.bg {
  background-color: #ecedee !important;
}

.color {
  color: #00b1ea;
}

.like {
  width: 7%;
}
.section {
  width: 10%;
}
.content {
  width: 57%;
}
.writer {
  width: 11%;
}

.viewer {
  width: 7%;
}

.previous {
  width: 20%;
}
</style>