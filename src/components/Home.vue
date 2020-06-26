<template>
  <div>
    <v-app>
      <v-app-bar app height="70px" flat color="white" hide-on-scroll>
        <v-toolbar-title>
          <v-img src="../assets/full_logo.svg" max-width="80%"></v-img>
        </v-toolbar-title>
        <v-spacer></v-spacer>
          <v-badge
            color="#00B1EA"
            content="6"
            bordered
            offset-x=22
            offset-y=20
            overlap>
            <v-btn icon><v-icon large>notifications</v-icon></v-btn>
          </v-badge>
            <v-btn icon><v-icon large>mdi-account</v-icon></v-btn>
      </v-app-bar>
        <v-content>
          <v-toolbar prominent height="374px" src="../assets/school_img.jpg">
          </v-toolbar>
          <v-container
            class="bg fill-height"
            fluid
          >
            <v-row>
              <v-col md="2"></v-col>

              <v-col md="2">
                <v-card
                dark
                color="rgb(255, 0, 0, 0)"
                flat
                app
                class="mx-auto"
                width="700"
                style="margin-top: -372px;"
                >
                <v-card-title class="display-1">
                  {{today}}
                </v-card-title>
                </v-card>
                <v-card
                  app
                  class="mx-auto">
                  <v-card app fixed flat>
                    <v-card-title class="headline"><strong>라이언</strong></v-card-title>
                      <v-card-subtitle>글<a class="color">{{text}}</a> 댓글<a class="color">{{riple}}</a></v-card-subtitle>
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          모아보기
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">전체</v-btn>
                        </v-card-text>
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          정보
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">채용 공고</v-btn>
                          <v-btn text block class="subtitle-1">취업 현황</v-btn>
                        </v-card-text>
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          일반
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">자유</v-btn>
                          <v-btn text block class="subtitle-1">질문</v-btn>
                          <v-btn text block class="subtitle-1">꿀팁</v-btn>
                        </v-card-text> 
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          학년
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">1학년</v-btn>
                          <v-btn text block class="subtitle-1">2학년</v-btn>
                          <v-btn text block class="subtitle-1">3학년</v-btn>
                          <v-btn text block class="subtitle-1">졸업생</v-btn>
                        </v-card-text>
                    </v-card>
                    </v-card>
                </v-col>

              <v-col md="6">               
                <v-card
                  class="mx-auto fixed"
                  style="margin-top: -300px;">
                  <v-card flat>
                    <v-card-title class="headline font-weight-bold">
                        <v-col cols="10">
                            홈
                        </v-col>
                    </v-card-title>
                    <v-row class="ma-3" align="center" justify="center">
                        <v-col align="center">
                            <v-card width="250" outlined>
                                <v-card-title class="font-weight-black title">
                                    졸업생 취업률
                                    <v-spacer></v-spacer>
                                    <v-icon color="#025F94">school</v-icon>
                                </v-card-title>
                                <v-card-title class="display-3 font-weight-bold light-blue--text text--darken-4 pt-0">
                                    {{getJobPercent}}
                                </v-card-title>
                                <v-card-text class="grey--text caption" align="left">
                                    {{getJobText}}
                                </v-card-text>
                            </v-card>
                        </v-col>
                        <v-col align="center">
                            <v-card width="250" outlined>
                                <v-card-title class="font-weight-black title">
                                    재학생 취업률
                                    <v-spacer></v-spacer>
                                    <v-icon color="#41AFE5">edit</v-icon>
                                    </v-card-title>
                                    <v-card-title class="display-3 font-weight-bold blue--text text--lighten-1 pt-0">
                                        {{getJobPercent}}
                                    </v-card-title>
                                    <v-card-text class="grey--text caption" align="left">
                                        {{getJobText}}
                                    </v-card-text>                                    
                            </v-card>
                        </v-col>
                        <v-col align="center">
                            <v-card width="250" outlined>
                                <v-card-title class="font-weight-black title">
                                    평균 연봉
                                    <v-spacer></v-spacer>
                                    <v-icon color="#DDE546">monetization_on</v-icon>
                                    </v-card-title>
                                    <v-card-title class="display-3 font-weight-bold lime--text text--lighten-1 pt-0">
                                        2900<div class=" font-weight-bold headline mt-5">만원</div>
                                    </v-card-title>
                                    <v-card-text class="grey--text caption" align="left">
                                        {{getJobText}}
                                    </v-card-text>                                    
                            </v-card>
                        </v-col>                        
                    </v-row>
                    <v-row class="ma-2" align="center" justify="center">
                        <v-col class="ml-4">
                            <v-card width="554" outlined>
                                <v-card-title class="font-weight-black title">
                                    취업처 기업 규모
                                </v-card-title>
                            </v-card>
                        </v-col>
                        <v-col class="ml-6 mr-4">
                            <v-card outlined>
                                <v-card-title class="font-weight-black title">
                                    근무지
                                </v-card-title>
                            </v-card>
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
const date = new Date()
const week = new Array('일', '월', '화', '수', '목', '금', '토')
const curmonth = date.getMonth()
const curdate = date.getDate()
const curday = date.getDay()
const meal_all = `백미밥 유부두부된장국 제육채소 볶음 숙주미나리무침 배추김치 에그타르트 오렌지`
import { Editor, Viewer } from '@toast-ui/vue-editor'

export default {
  data () {
    return {
      text: 5,
      riple : 35,
      today : `${curmonth+1}월 ${curdate}일 ${week[curday]}요일`,
      meal_section : `조식`,
      meal_all : meal_all,
      getJobPercent: '75%',
      getJobText:'1기 80명 중 60명'
    }
  },

  components : {

  },

  created () {
 
  },

  methods: {

  },
  
  watch: {

  },

  computed: {

  }
}
</script>

<style>
.bg{
  background-color:#ECEDEE !important; 
}

.color {
  color: #00B1EA;
}


</style>