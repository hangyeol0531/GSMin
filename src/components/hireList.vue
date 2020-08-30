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
                  <v-card-title class="headline font-weight-bold">
                    <v-row>
                      <v-col cols="7">채용 공고</v-col>
                      <v-col cols="5" class="pa-0 d-flex">
                        <v-select
                          class="d-flex"
                          sm="6"
                          :items="items"
                          item-value="value"
                          v-model="categorySelect"
                          return-object
                          solo
                          label="선택"
                        ></v-select>
                        <v-toolbar flat dense floating>
                          <v-text-field
                            solo
                            hide-details
                            v-on:keyup.enter="submit"
                            append-icon="search"
                            v-model="searchField"
                            single-line
                          ></v-text-field>
                        </v-toolbar>
                      </v-col>
                    </v-row>
                  </v-card-title>
                  <v-card hover v-for="(listItem, index) in CalData" :key="index" flat>
                    <v-card
                      @click="alert(listItem[1])"
                      hover
                      outlined
                      class="ml-4 mr-4 mb-3 rounded-lg"
                    >
                      <v-row>
                        <v-col cols="11" class="ma-0">
                          <v-card-title class="pb-0">
                            <div v-if="resBoard === false">{{ resText }}</div>
                            <div class="font-weight-bold">{{listItem[1]}}·</div>
                            <div class="subtitle-1 font-weight-medium">{{listItem[2]}}</div>
                          </v-card-title>
                          <v-card-title class="pb-0">
                            <div class="subtitle-2 font-weight-bold" style="color:#666666">업무내용</div>
                            <div
                              class="subtitle-2 font-weight-medium pl-2"
                              style="color:#666666"
                            >{{listItem[3]}}</div>
                          </v-card-title>
                          <v-card-title class="pt-0 pb-0">
                            <div class="subtitle-2 font-weight-bold" style="color:#666666">자격요건</div>
                            <div
                              class="subtitle-2 font-weight-medium pl-2 col-10 text-truncate"
                              style="color:#666666"
                            >{{listItem[4]}}</div>
                          </v-card-title>
                        </v-col>
                        <v-col cols="1" class="pt-13 pb-0">
                          <v-icon x-large color="#41AFE5">keyboard_arrow_right</v-icon>
                        </v-col>
                      </v-row>
                    </v-card>
                  </v-card>
                  <v-card flat>
                    <v-pagination v-model="curPageNum" :length="numOfPages" :value="selectedPage"></v-pagination>
                    <v-card-text align="right">
                      <v-btn dark color="#025F94" @click="Write">
                        <v-icon>create</v-icon>글쓰기
                      </v-btn>
                      <!-- <v-btn dark color="#025F94" @click="Viewer"><v-icon>create</v-icon>뷰어테스트</v-btn> -->
                    </v-card-text>
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

  data() {
    return {
      text: 5,
      riple: 35,
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
      resLength: "",
      selectedPage: "",
      resText: "게시판이 비어있어요",
      items: [
        {
          text: "회사 명",
          value: "1",
        },
        {
          text: "업무내용",
          value: "3",
        },
      ],
      select: {
        text: "제목",
        value: "title",
      },
      searchField: '',
    };
  },

  created() {
    this.$store.dispatch("auth/getUserInfo");
    this.$http
      .get("/gsm_hire_list", {
        //취업공고
      })
      .then((response) => {
        console.log(response);
        this.listData = response.data;
        console.log(this.listData.length);
      })
      .catch((e) => {
        console.log(e);
      });
  },

  methods: {
    searchBoard(page) {
      console.log("currP", page);
      this.$http
        .post("/get_all_board_information", {
          page_num: page,
        })
        .then((res) => {
          this.listData = res.data;
          this.resBoard = true;
          this.calData = this.listData;
        })
        .catch((e) => {
          swal("이런!", "게시판이 비어 있습니다", "error");
        });
    },

    submit() {
      let gory = this.category;
      console.log(gory);
      this.searchData = this.listData.filter((data) => {
        return data[gory].includes(this.searchField);
      });

      return (this.listData = this.searchData)
    },

    alert(company) {
      alert(company, "정보 입니다.");
    },

    eachBoard(postIdx) {
      this.$router.replace({ path: "/eachBoard", query: { postIdx } });
    },

    Write() {
      this.$router.push({ name: "Write" });
    },

    Viewer() {
      this.$router.push({ name: "Viewer" });
    },
  },

  watch: {
    categorySelect: function (value) {
      this.category = value.value;
      console.log(this.category);
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
        return Math.ceil(this.listData.length / this.dataPerPage);
      }
    },
    CalData() {
      return this.listData.slice(this.startOffset, this.endOffset);
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
