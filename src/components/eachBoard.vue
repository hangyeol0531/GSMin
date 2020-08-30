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
              <v-card class="mx-auto fixed" style="margin-top: -300px;" flat>
                <v-card flat>
                  <v-card-title class="headline font-weight-bold pb-0">
                    <v-col cols="10">{{title}}</v-col>
                  </v-card-title>
                  <v-card-title class="subtitle-1 font-weight-bold ml-3">
                    <v-img
                      v-if="boardInfo.grade === 1"
                      src="../assets/one_icon.png"
                      max-width="2%"
                      class="mr-1"
                    ></v-img>
                    <v-img
                      v-if="boardInfo.grade === 2"
                      src="../assets/two_icon.png"
                      max-width="2%"
                      class="mr-1"
                    ></v-img>
                    <v-img
                      v-if="boardInfo.grade === 3"
                      src="../assets/three_icon.png"
                      max-width="2%"
                      class="mr-1"
                    ></v-img>
                    {{boardInfo.name}}
                    <div class="font-weight-light ml-2">{{date}}</div>
                    <v-col>
                      <div
                        class="font-weight-light"
                        style="text-align:right"
                      >조회수: {{viewCount}} 추천: {{likeCount}} 댓글: {{rippleCount}}</div>
                    </v-col>
                  </v-card-title>
                  <v-divider></v-divider>
                  <v-card flat>
                    <v-card-title>
                      <v-col>
                        <viewer v-if="content != null" :initialValue="content" />
                      </v-col>
                    </v-card-title>
                  </v-card>
                  <v-divider></v-divider>
                  <v-card flat>
                    <v-card-title>
                      <v-col cols="7" class="pr-9">
                        <div style="text-align:right">
                          <v-btn
                            :outlined="!isLikeBtn"
                            @click="likeBtn"
                            :color="isLikeBtn ? '#5C6BC0' : '#E0E0E0'"
                            :style="{
                            backgroundColor : isLikeBtn ? '#5C6BC0 !important' : '',
                            color: isLikeBtn ? 'white' : '#E0E0E0'
                          }"
                          >
                            <v-icon>thumb_up</v-icon>
                            <div
                              class="likeCount pl-2 subtitle-1 font-weight-bold"
                              style="text-align:center"
                            >{{likeCount}}</div>
                          </v-btn>
                        </div>
                      </v-col>
                      <v-col cols="5" class>
                        <div style="text-align:right">
                          <v-btn text>
                            <v-icon small color="#BDBDBD">share</v-icon>공유
                          </v-btn>
                          <v-btn text>신고</v-btn>
                        </div>
                      </v-col>
                    </v-card-title>
                  </v-card>
                </v-card>
              </v-card>
              <v-card class="mt-5" flat>
                <v-card-title>
                  <div class="title font-weight-bold">댓글</div>
                  <div class="subtitle-1 font-weight-regular ml-2">{{rippleCount}}</div>
                </v-card-title>
                <v-divider></v-divider>
                <v-card flat>
                  <v-card-title>
                    <v-col
                      cols="10"
                      class="subtitle-2 font-weight-bold pb-0"
                    >{{user['user_nickname']}}</v-col>
                    <v-row>
                      <v-col cols="10" class="pt-0 pb-0">
                        <v-text-field
                          v-model="rippleContent"
                          label="댓글을 입력하세요"
                          autocomplete="off"
                          height="10%"
                          solo
                          flat
                          v-on:keyup.enter="postRipple()"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" class="pt-0 pl-5 pr-5">
                        <v-btn color="indigo" dark block large @click="postRipple()">댓글쓰기</v-btn>
                      </v-col>
                    </v-row>
                  </v-card-title>
                </v-card>
                <v-card v-for="(rippleItem, index) in rippleInfo" :key="index" flat>
                  <v-card flat>
                    <v-divider></v-divider>
                    <v-card-title class="pl-5">
                      <div class="subtitle-1 font-weight-bold">
                        {{rippleItem.nickname}}
                        <v-icon class="pl-2" small color="grey">thumb_up</v-icon>
                      </div>
                      <v-col>
                        <div style="text-align:right">
                          <v-btn text>추천</v-btn>
                          <v-divider vertical inset></v-divider>
                          <v-btn text>신고</v-btn>
                        </div>
                      </v-col>
                    </v-card-title>
                    <v-card-text class="subtitle-1 font-weight-bold pl-5">{{rippleItem.comment}}</v-card-text>
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
import { Editor, Viewer } from "@toast-ui/vue-editor";
import { router } from "../router/index.js";
import { mapState } from "vuex";
import sideBar from "./sideBar.vue";
import topBar from "./topBar.vue";

export default {
  data() {
    return {
      text: 5,
      riple: 35,
      mainItems: [
        { text: "일반", value: "일반" },
        { text: "학년", value: "학년" },
      ],
      mainSelect: [{ text: "일반", value: "일반" }],
      subItems: [
        { text: "자유", value: "자유" },
        { text: "질문", value: "질문" },
        { text: "꿀팁", value: "꿀팁" },
      ],
      subDefaultItems: [
        { text: "자유", value: "자유" },
        { text: "질문", value: "질문" },
        { text: "꿀팁", value: "꿀팁" },
      ],
      subGradeItems: [
        { text: "1학년", value: "1학년" },
        { text: "2학년", value: "2학년" },
        { text: "3학년", value: "3학년" },
        { text: "졸업생", value: "졸업생" },
      ],
      subSelect: [
        {
          text: "자유",
          value: "자유",
        },
      ],
      title: "",
      dataPerPage: 10,
      curPageNum: 1,
      search: "",
      category: "",
      resText: "게시판이 비어있어요",
      editorText: "",
      content: null,
      company: "마이다스아이티",
      email: "",
      date: "",
      boardInfo: [],
      viewCount: 12,
      likeCount: 5,
      rippleInfo: [],
      rippleCount: "",
      rippleContent: "",
      currentUser: "",
      isLikeBtn: false,
    };
  },

  components: {
    editor: Editor,
    viewer: Viewer,
    sideBar,
    topBar,
  },

  created() {
    this.likeCount = this.$store.state.board.likeCount;
    this.$store.dispatch("auth/getUserInfo");
    this.$http
      .post("/get_one_board", {
        idx: this.$route.query.postIdx,
      })
      .then((response) => {
        console.log(response.data);
        this.title = response.data.title;
        this.content = response.data.content;
        this.email = response.data.user_email;
        this.viewCount = response.data.view_count;
        this.date = response.data.date.split("T")[0];
        this.getBoardInfo(this.email);
      })
      .catch((e) => {
        console.log(e);
      });

    this.$http
      .post("/get_comment_information", {
        idx: this.$route.query.postIdx,
      })
      .then((response) => {
        console.log(response.data);
        this.rippleCount = response.data.length;
        this.rippleInfo = response.data;
      });

    this.$http
      .post("/isgood_num", {
        Bulletin_idx: this.$route.query.postIdx,
      })
      .then((response) => {
        console.log(response);
      })
      .catch((e) => {
        console.log(e);
      });
  },

  methods: {
    getBoardInfo(email) {
      this.$http
        .post("/get_user_data", {
          email,
        })
        .then((response) => {
          console.log(response.data);
          this.boardInfo = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    postRipple() {
      this.$http
        .post("/write_comment", {
          idx: this.$route.query.postIdx,
          email: this.$store.state.auth.userInfo.user_email,
          comment: this.rippleContent,
        })
        .then((response) => {
          console.log(response.data);
          this.rippleContent = "";
          window.history.go();
        })
        .catch((e) => {
          console.log(e);
        });
    },

    likeBtn() {
      this.likeCount = this.$store.state.board.likeCount;
      this.isLikeBtn = !this.isLikeBtn;
      let postIdx = this.$route.query.postIdx;
      let email = this.$store.state.auth.userInfo.user_email;
      this.$store.dispatch("board/addLikeCount", { postIdx, email });
    },
  },

  watch: {},

  computed: {
    ...mapState({
      user: (state) => state.auth.userInfo,
    }),
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

.likeCount {
  color: black !important;
}
</style>
