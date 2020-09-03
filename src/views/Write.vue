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
                    <v-col cols="10">글 쓰기</v-col>
                  </v-card-title>
                  <ValidationObserver ref="section_ob" v-slot="{}">
                    <form>
                      <v-card-title>
                        <v-col cols="2" class="pb-0">
                          <ValidationProvider v-slot="{ errors }" name="카테고리" rules="required">
                            <v-select
                              class="d-flex"
                              sm="4"
                              :items="mainItems"
                              v-model="mainSelect"
                              :error-messages="errors"
                              return-object
                              hide-details
                              solo
                              label="일반"
                            ></v-select>
                          </ValidationProvider>
                        </v-col>
                        <v-col cols="3" class="pb-0">
                          <ValidationProvider v-slot="{ errors }" name="서브카테고리" rules="required">
                            <v-select
                              class="d-flex"
                              sm="4"
                              :items="subItems"
                              item-value="value"
                              v-model="subSelect"
                              :error-messages="errors"
                              return-object
                              hide-details
                              solo
                              label="카테고리"
                            ></v-select>
                          </ValidationProvider>
                        </v-col>
                      </v-card-title>
                      <v-card-title>
                        <v-col cols="12" class="pt-0">
                          <ValidationProvider v-slot="{ errors }" name="제목" rules="required">
                            <v-text-field
                              label="제목"
                              v-model="title"
                              outlined
                              solo
                              flat
                              :error-messages="errors"
                            ></v-text-field>
                          </ValidationProvider>
                        </v-col>
                      </v-card-title>
                    </form>
                  </ValidationObserver>
                  <v-card>
                    <v-card-text>
                      <viewer :initialValue="editorText" height="500px" />
                      <editor ref="editorText" height="500px" mode="wysiwyg" />
                    </v-card-text>
                    <v-card-text>
                      <v-row>
                        <v-col>
                          <v-btn>
                            <v-icon left>delete_forever</v-icon>취소
                          </v-btn>
                        </v-col>
                        <v-col class="text-right">
                          <v-btn dark color="#025F94" @click="post">
                            게시
                            <v-icon right>send</v-icon>
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                  <v-card></v-card>
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
import { required } from "vee-validate/dist/rules";
import { extend, ValidationObserver, ValidationProvider } from "vee-validate";
import sideBar from "./sideBar.vue";
import topBar from "./topBar.vue";

extend("required", {
  ...required,
  message: "{_field_} 칸을 채워주세요",
});

export default {
  data() {
    return {
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
      editorText: "",
    };
  },

  components: {
    editor: Editor,
    viewer: Viewer,
    sideBar,
    topBar,
  },

  created() {},

  methods: {
    post() {
      this.$refs.section_ob.validate().then((valid) => {
        if (valid) {
          this.$http
            .post("/write_Bulletin", {
              title: this.title,
              type: this.category,
              email: this.$store.state.auth.userInfo.user_email,
              content: this.$refs.editorText.invoke("getMarkdown"),
            })
            .then((response) => {
              alert("성공적으로 등록 되었습니다");
              router.push({ name: "Board" });
            })
            .catch((e) => {
              alert(e)
            });
        }
      });
    },
  },

  watch: {
    mainSelect: function (value) {
      value.value === "일반"
        ? (this.subItems = this.subDefaultItems)
        : (this.subItems = this.subGradeItems);
    },
    subSelect: function (value) {
      this.category = value.value;
    },
    loader() {
      this.content = this.$refs.editorText.invoke("getMarkdown");
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
</style>
