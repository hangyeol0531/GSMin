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
                    <v-col cols="10">설정</v-col>
                    <v-col class="mt-5 title font-weight-bold" cols="10">닉네임 변경</v-col>
                  </v-card-title>
                  <v-card-text>
                    <v-row>
                      <v-col cols="5" class="pt-0 ml-3">
                        <ValidationObserver ref="nick_ob" v-slot="{ }">
                          <ValidationProvider v-slot="{ errors }" name="닉네임 변경" rules="required">
                            <v-text-field
                              outlined
                              solo
                              flat
                              v-model="chNickname"
                              :error-messages="errors"
                              label="닉네임 변경"
                              hide-details="auto"
                              required
                            ></v-text-field>
                          </ValidationProvider>
                        </ValidationObserver>
                      </v-col>
                      <v-col cols="3 pt-0">
                        <v-btn x-large dark color="#025F94" @click="changeName">변경하기</v-btn>
                      </v-col>
                    </v-row>
                    <v-col cols="10" class="title font-weight-bold">로그아웃</v-col>
                    <v-col>
                      <v-btn dark color="#00B1EA" @click="logOut">로그아웃하기</v-btn>
                    </v-col>
                  </v-card-text>
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
import { required } from "vee-validate/dist/rules";
import { extend, ValidationObserver, ValidationProvider } from "vee-validate";
import sideBar from "./sideBar.vue";
import topBar from "./topBar.vue"
import { mapState } from "vuex";

extend("required", {
  ...required,
  message: "{_field_} 칸을 채워주세요",
});

export default {
  components: {
    sideBar,
    topBar
  },

  data() {
    return {
      text: 5,
      riple: 35,
      meal_section: `조식`,
      meal_all: meal_all,
      items: [
        {
          text: "제목",
          value: "likeCount",
        },
        {
          text: "이전",
          value: "previous",
        },
      ],
      select: {
        text: "제목",
        value: "title",
      },
      dataPerPage: 10,
      curPageNum: 1,
      search: "",
      category: "",
      resText: "게시판이 비어있어요",
      editorText: "",
      content: "",
      // 게시물 저장 로딩
      loader: null,
      loading: false,
      company: "마이다스아이티",
      chNickname: "",
      email: "",
    };
  },

  created() {},

  computed: {},

  methods: {
    changeName() {
      this.$refs.nick_ob.validate().then((valid) => {
        if (valid) {
          this.email = this.$store.state.auth.userInfo.user_email;
          let email = this.email;
          let chNickname = this.chNickname;
          this.$store.dispatch("auth/changeNick", { email, chNickname });
        }
      });
    },

    logOut() {
      this.$store.dispatch("auth/logOut");
    },
  },

  watch: {
    select: function (value) {
      this.category = value.value;
    },

    loader() {
      const l = this.loader;
      this[l] = !this[l];
      setTimeout(() => {
        this[l] = false;
        localStorage.setItem("content", this.content);
        this.$router.push({ name: "Home" });
      }, 3000);
      this.loader = null;
      this.content = this.$refs.editorText.invoke("getMarkdown");
    },
  },

  computed: {},
};
</script>

<style>
.bg {
  background-color: #ecedee !important;
}

.color {
  color: #00b1ea;
}
</style>