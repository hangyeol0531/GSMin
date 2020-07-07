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
          <v-toolbar prominent height="250px" src="../assets/school_img.jpg">
          </v-toolbar>
          <v-container
            class="bg fill-height"
            fluid
          >
            <v-row>
              <v-col md="2"></v-col>

              <v-col md="2">
                <side-bar></side-bar>
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
                          </v-toolbar>
                        </v-col>
                      </v-row>
                    </v-card-title>
                      <v-card to="/" hover v-for="(listItem, index) in CalData" :key="index" flat>
                        <v-card-text>
                          <div v-if="resBoard == false">
                            {{resText}}
                          </div>
                          <tbody>
                            <tr>
                              <td class="like"><v-icon small>thumb_up_alt</v-icon>{{listItem.likeCount}}</td>
                              <td class="section"><v-chip label>{{listItem.section}}</v-chip></td>
                              <td class="content"><strong>{{listItem.content}}</strong></td>
                              <td class="writer font-weight-bold"><div><v-img src="../assets/one_icon.png" width="20" style="float:left"></v-img>{{listItem.writer}}</div></td>
                              <td class="viewer">{{listItem.viewer}}</td>
                              <td class="previous">{{listItem.previous}}</td>
                            </tr>
                          </tbody>
                        </v-card-text>                       
                      </v-card>
                      <v-card>
                        <v-pagination
                          v-model="curPageNum"
                          :length="numOfPages">
                        </v-pagination>
                        <v-card-text align="right">
                          <v-btn dark color="#025F94" @click="Write"><v-icon>create</v-icon>글쓰기</v-btn>
                          <v-btn dark color="#025F94" @click="Viewer"><v-icon>create</v-icon>뷰어테스트</v-btn>
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
const meal_all = `백미밥 유부두부된장국 제육채소 볶음 숙주미나리무침 배추김치 에그타르트 오렌지`

import sideBar from './sideBar.vue'

export default {
  components: {
    sideBar
  },

  data () {
    return {
      text: 5,
      riple : 35,
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
      search: '',
      category: '',
      searchData : [],
      resBoard: false,
      resText : '게시판이 비어있어요'
    }
  },

  created () {
    this.$store.dispatch('auth/getUserInfo')
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
    },

    Write() {
      this.$router.push({name : 'Write'})
    },

    Viewer() {
      this.$router.push({name : 'Viewer'})
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
    },
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

.like {
  width:7%;
}
.section {
  width:10%;
}
.content {
  width:60%;
}
.writer {
  width:9%;
}

.viewer{
  width:3%;
}

.previous{
  width:5%;
}

</style>