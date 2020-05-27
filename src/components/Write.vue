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
              <v-col md="1"></v-col>

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
                          <v-btn text block class="subtitle-1">HOT 게시판</v-btn>
                          <v-btn text block class="subtitle-1">BEST 게시판</v-btn>
                        </v-card-text>
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          정보
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">학교 소식</v-btn>
                          <v-btn text block class="subtitle-1">채용 정보</v-btn>
                          <v-btn text block class="subtitle-1">꿀팁</v-btn>
                        </v-card-text>
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          일반
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">자유</v-btn>
                          <v-btn text block class="subtitle-1">홍보</v-btn>
                          <v-btn text block class="subtitle-1">장터</v-btn>
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
                      <v-divider></v-divider>
                        <v-card-subtitle class="pa-1 pl-3">
                          소모임
                        </v-card-subtitle>                    
                        <v-card-text>
                          <v-btn text block class="subtitle-1">코딩</v-btn>
                          <v-btn text block class="subtitle-1">공기업</v-btn>
                          <v-btn text block class="subtitle-1">공무원시험</v-btn>
                          <v-btn text block class="subtitle-2 text--grey">+ 소모임 개설</v-btn>
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
                      <v-row>
                        <v-col cols="7">
                          전체
                        </v-col>
                        <v-col cols="5" class="pa-0 d-flex">
                          <v-select
                            class="d-flex"
                            sm="6"
                            :items="items"
                            item-value="value"
                            v-model="select"
                            return-object
                            solo
                            label="제목">

                          </v-select>
                          <v-toolbar
                            flat
                            dense
                            floating
                          >
                            <v-text-field
                              solo
                              hide-details
                              v-on:keyup.enter="submit"
                              append-icon="search"
                              v-model="search"
                              single-line>
                            </v-text-field>
                              <!-- <v-btn text>
                                <v-icon>search</v-icon>
                              </v-btn>                             -->
                          </v-toolbar>
                        </v-col>
                      </v-row>
                    </v-card-title>
                      <v-card>
                      
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
const date = new Date()
const week = new Array('일', '월', '화', '수', '목', '금', '토')
const curmonth = date.getMonth()
const curdate = date.getDate()
const curday = date.getDay()
const meal_all = `백미밥 유부두부된장국 제육채소 볶음 숙주미나리무침 배추김치 에그타르트 오렌지`

import allList from '../list/list.json'

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
      listData: [],
      dataPerPage: 10,
      curPageNum: 1,
      allList : allList,
      search: '',
      category: '',
      searchData : [],
      elData : [],
      resBoard: false,
      resText : '게시판이 비어있어요'
    }
  },

  created () {
    this.$http.post('/board')
      .then((res) => {
        this.listData = res.data
        this.resBoard = true
      }). catch((e) => {
        console.log(e)
        this.resBoard = false
      }) 
  },

  methods: {
    submit() {
      let gory = this.category
      this.searchData = this.listData.filter(data => {
        return (data[gory]).includes(this.search)
      })
      
      return this.listData = this.searchData
      
    }
  },
  
  watch: {
    select: function (value) {
      this.category = value.value
    }
  },

  computed: {
    startOffset() {
      return ((this.curPageNum -1) * this.dataPerPage)
    },
    endOffset() {
      return (this.startOffset + this.dataPerPage)
    },
    numOfPages() {
      return Math.ceil(this.listData.length / this.dataPerPage)
    },
    CalData() {
      return this.listData.slice(this.startOffset, this.endOffset)
    }
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