<template>
    <div>
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
            <v-card-title v-if="user">
                <v-img v-if="user['user_grade'] === 1" src="../assets/one_icon.png" max-width="8%"></v-img>
                <v-img v-if="user['user_grade'] === 2" src="../assets/two_icon.png" max-width="8%"></v-img>
                <v-img v-if="user['user_grade'] === 3" src="../assets/three_icon.png" max-width="8%"></v-img><strong>{{user['user_nickname']}}</strong></v-card-title>
                <v-card-subtitle class="font-weight-bold">{{company}}</v-card-subtitle>
                <v-divider></v-divider>
                <v-card-subtitle class="pa-1 pl-3">
                    모아보기
                </v-card-subtitle>                    
                <v-card-text>
                    <v-btn text block class="subtitle-1" @click="routerPush('Board')">전체</v-btn>
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
                    <div v-if="viewer == true">학년</div>
                </v-card-subtitle>                    
                <v-card-text>
                    <v-btn text block class="subtitle-1">1학년</v-btn>
                    <v-btn text block class="subtitle-1">2학년</v-btn>
                    <v-btn text block class="subtitle-1">3학년</v-btn>
                    <v-btn text block class="subtitle-1">졸업생</v-btn>
                </v-card-text>
            </v-card>
            </v-card>        
        <slot></slot>
    </div>
</template>

<script>
const date = new Date()
const week = new Array('일', '월', '화', '수', '목', '금', '토')
const curmonth = date.getMonth()
const curdate = date.getDate()
const curday = date.getDay()
import { mapState } from 'vuex'

export default {
    data() {
        return{
            today : `${curmonth+1}월 ${curdate}일 ${week[curday]}요일`,
            company : '마이다스 아이티',
            viewer : true
        }
    },

    created() {
        this.$store.dispatch('auth/getUserInfo')
    },

    computed: {
        ...mapState({
        user: state => state.auth.userInfo
        })
    },

    methods: {
        test() {
            console.log("child!")
            // this.$store.dispatch('auth/getUserInfo')
        }
    }
}
</script>