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
              <side-bar ref="sidebar"></side-bar>
            </v-col>

            <v-col md="6">
              <v-card flat class="mx-auto fixed" style="margin-top: -300px;">
                <v-card flat>
                  <v-card-title class="headline font-weight-bold">
                    <v-col cols="10">통계</v-col>
                  </v-card-title>
                  <v-row class="ma-3" align="center" justify="center">
                    <v-col align="center">
                      <v-card width="250">
                        <v-card-title class="font-weight-black title">
                          <div class="font">졸업생 취업률</div>
                          <v-spacer></v-spacer>
                          <v-icon color="#025F94">school</v-icon>
                        </v-card-title>
                        <v-card-title
                          class="display-3 font-weight-bold light-blue--text text--darken-4 pt-0"
                        >{{graduatePercent}}%</v-card-title>
                        <v-card-text class="grey--text caption" align="left">{{getJobText}}</v-card-text>
                      </v-card>
                    </v-col>
                    <v-col align="center">
                      <v-card width="250">
                        <v-card-title class="font-weight-black title">
                          <div class="font">재학생 취업률</div>
                          <v-spacer></v-spacer>
                          <v-icon color="#41AFE5">edit</v-icon>
                        </v-card-title>
                        <v-card-title
                          class="display-3 font-weight-bold blue--text text--lighten-1 pt-0"
                        >{{enrolledPercent}}%</v-card-title>
                        <v-card-text class="grey--text caption" align="left">{{getJobNum}}</v-card-text>
                      </v-card>
                    </v-col>
                    <v-col align="center">
                      <v-card width="250">
                        <v-card-title class="font-weight-black title">
                          <div class="font">평균 연봉</div>
                          <v-spacer></v-spacer>
                          <v-icon color="#DDE546">monetization_on</v-icon>
                        </v-card-title>
                        <v-card-title
                          class="display-3 font-weight-bold lime--text text--lighten-1 pt-0"
                        >
                          2900
                          <div class="font-weight-bold headline mt-5">만원</div>
                        </v-card-title>
                        <v-card-text class="grey--text caption" align="left">1기 72명 중 59명</v-card-text>
                      </v-card>
                    </v-col>
                  </v-row>
                  <v-row class="ma-2" align="center" justify="center">
                    <v-col class="ml-4">
                      <v-card>
                        <v-card-title class="font-weight-black title">
                          <div class="font">취업처 기업 규모</div>
                        </v-card-title>
                        <v-card-text>
                          <div class="chart">
                            <line-chart
                              :chart-data="datacollection"
                              style="width:27vw; height:15vw;"
                            ></line-chart>
                          </div>
                        </v-card-text>
                      </v-card>
                    </v-col>
                    <v-col class="ml-6 mr-4">
                    </v-col>
                  </v-row>
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
import LineChart from "../LineChart.js";
import { router } from "../router/index.js";
import sideBar from "./sideBar.vue";
import topBar from "./topBar.vue"

export default {
  data() {
    return {
      meal_section: `조식`,
      graduatePercent: "81.94",
      enrolledPercent: "",
      getJobNum: "",
      getJobText: "1기 72명 중 59명",
      datacollection: null,
    };
  },

  components: {
    LineChart,
    sideBar,
    topBar
  },

  created() {
    this.$http
      .get("/gsm_hire_list", {
        //취업공고
      })
      .then((response) => {
        console.log(response);
      })
      .catch((e) => {
        console.log(e);
      });

    this.$http
      .get("/gsm_employment_rate", {
        //취업률
      })
      .then((response) => {
        console.log(response);
        this.enrolledPercent = response.data.rate;
        this.getJobNum = `2기 80명 중 ${response.data.emp_num}명`;
      });

    this.fillData();
  },

  methods: {
    routerPush(name) {
      router.push({ name });
    },
    fillData() {
      this.datacollection = {
        labels: [
          "공무원",
          "공기업",
          "그룹 및 200인 이상",
          "20인 이상 200인 이하",
          "20인 이하",
        ],
        datasets: [
          {
            barThickness: 20,
            label: "인원 수",
            hoverBackgroundColor: "#2980b9",
            backgroundColor: "#41AFE5",
            data: [
              2,
              2,
              3,
              8,
              44,
            ],
          },
        ],
      };
    },
    getRandomInt() {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5;
    },
  },

  watch: {},
};
</script>

<style>
.bg {
  background-color: #ecedee !important;
}

.color {
  color: #00b1ea;
}

.chart {
  max-width: 100%;
  height: 50%;
}
</style>