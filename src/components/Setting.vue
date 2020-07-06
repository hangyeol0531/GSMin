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
                    <v-card-title class="headline">
                      <v-img src="../assets/one_icon.png" max-width="8%"></v-img><strong>라이언</strong></v-card-title>
                      <v-card-subtitle class="font-weight-bold">{{company}}</v-card-subtitle>
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
                            설정
                        </v-col>
                        <v-col class="mt-5 title font-weight-bold" cols="10">
                            닉네임 변경
                        </v-col>
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
                            <v-btn x-large dark color="#025F94" @click="changeName">
                                변경하기
                            </v-btn>
                        </v-col>
                    </v-row>
                    <v-col cols="10" class="title font-weight-bold" @click="logOut">
                            로그아웃
                    </v-col>
                    <v-col>
                        <v-btn dark color="#00B1EA">
                            로그아웃하기
                        </v-btn>
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
const date = new Date()
const week = new Array('일', '월', '화', '수', '목', '금', '토')
const curmonth = date.getMonth()
const curdate = date.getDate()
const curday = date.getDay()
const meal_all = `백미밥 유부두부된장국 제육채소 볶음 숙주미나리무침 배추김치 에그타르트 오렌지`

import { required } from 'vee-validate/dist/rules'
import { extend, ValidationObserver, ValidationProvider} from 'vee-validate'

  extend('required', {
    ...required,
    message: '{_field_} 칸을 채워주세요',
  })

export default {
  data () {
    return {
      text: 5,
      riple : 35,
      today : `${curmonth+1}월 ${curdate}일 ${week[curday]}요일`,
      meal_section : `조식`,
      meal_all : meal_all,
      items : 
      [
        {
          text: '제목',
          value : 'likeCount'
        },
        {
          text: '이전',
          value : 'previous'
        }
      ],
      select: {
        text: '제목',
        value: 'title'
      },
      dataPerPage: 10,
      curPageNum: 1,
      search: '',
      category: '',
      resText : '게시판이 비어있어요',
      editorText: '',
      content: '',
      // 게시물 저장 로딩
      loader: null,
      loading: false,
      //
      company: '마이다스아이티',
      chNickname: ''
    }
  },

  created () {
 
  },

  methods: {
      changeName() {
          this.$refs.nick_ob.validate().then(valid => {
              
          })
      },

      logOut() {
          
      }
  },
  
  watch: {
    select: function (value) {
      this.category = value.value
    },
    
    loader () {
      const l = this.loader
      this[l] = !this[l]
      setTimeout(() => {
        this[l] = false
        localStorage.setItem("content", this.content)
        this.$router.push({name : 'Home'})
      }, 3000)
      this.loader = null
      this.content = this.$refs.editorText.invoke("getMarkdown")
    }
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