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
                <side-bar></side-bar>
              </v-col>

              <v-col md="6">               
                <v-card
                  class="mx-auto fixed"
                  style="margin-top: -300px;">
                  <v-card flat>
                    <v-card-title class="headline font-weight-bold">
                        <v-col cols="10">
                            취업 후기
                        </v-col>
                    </v-card-title>
                    <v-card-title>
                        <v-col cols="2" class="pb-0">
                          <v-select
                            class="d-flex"
                            sm="4"
                            return-object
                            hide-details
                            solo
                            label="일반">
                          </v-select>
                        </v-col>
                        <v-col cols="3" class="pb-0">
                          <v-select
                            class="d-flex"
                            sm="4"
                            :items="items"
                            item-value="value"
                            v-model="select"
                            return-object
                            hide-details
                            solo
                            label="제목">
                          </v-select>
                        </v-col>                        
                    </v-card-title>
                    <v-card-title>
                        <v-col cols="12" class="pt-0">
                            <v-text-field
                                label="제목"
                                outlined
                                solo
                                flat>
                            </v-text-field>
                        </v-col>
                    </v-card-title>
                      <v-card>
                          <v-card-text>
                            <viewer :initialValue="editorText" height="500px" />
                            <editor
                            ref="editorText"
                            height="500px"
                            mode="wysiwyg"/>
                          </v-card-text>
                          <v-card-text>
                              <v-row>
                                  <v-col>
                                      <v-btn><v-icon left>delete_forever</v-icon>취소</v-btn>
                                  </v-col>
                                  <v-col class="text-right">
                                      <v-btn 
                                        :loading="loading"
                                        dark
                                        color="#025F94"
                                        @click="loader = 'loading'">게시<v-icon right>send</v-icon></v-btn>
                                  </v-col>
                              </v-row>
                          </v-card-text>
                      </v-card>
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
const meal_all = `백미밥 유부두부된장국 제육채소 볶음 숙주미나리무침 배추김치 에그타르트 오렌지`
import { Editor, Viewer } from '@toast-ui/vue-editor'
import sideBar from './sideBar.vue'
export default {
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
      company: '마이다스아이티'
    }
  },

  components : {
    'editor' : Editor,
    'viewer' : Viewer,
    sideBar
  },

  created () {
 
  },

  methods: {

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