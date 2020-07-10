<template>
  <div>
    <v-app id="inspire">
    <v-app-bar app height="100px" flat></v-app-bar>
    <v-content>
    <v-container class="fill-height" fluid>
    <v-row>
      <v-col></v-col>
      <v-col md="3" align="center" justify="center">
        <v-card flat>
          <div align="center" justify="center">
            <v-img src="../assets/full_logo.svg" max-width="243"></v-img>
          </div>          
        </v-card>

        </v-col>
        <v-col></v-col>
      </v-row>
      </v-container>
      </v-content>
    <v-footer height="100px" app flat></v-footer>
    </v-app>
  </div>
</template>

<script>
import { required, email, min, confirmed } from 'vee-validate/dist/rules'
import { extend, ValidationObserver, ValidationProvider} from 'vee-validate'
// import axios from 'axios'

  extend('required', {
    ...required,
    message: '{_field_} 칸을 채워주세요',
  })

  extend('email', {
    ...email,
    message: '올바른 이메일 형식을 입력해 주세요',
  })

    extend('min', {
    ...min,
    message: '최소 {length}자 이상 가능합니다.',
  })

    extend('confirmed', {
    ...confirmed,
    message: '비밀번호가 일치하지 않습니다',
  })

  export default {
    data () {
        return {
            password: '',
            passwordConfirm: '',
            nickname: '',
            email: '',
        }
    },
    methods: {
      submit () {
        this.$refs.reg_ob.validate().then(valid => {
            if (valid) {
                this.$http.post('/insert_user_information', {
                    pw: this.password,
                    nickname : this.nickname,
                }).then((res) => {
                    this.$router.push({name : 'endAuth'})
                }).catch(e => {
                    alert("오류", e)
                })
            }
        })
      }
    }
  }


</script>

<style>
@import url(//spoqa.github.io/spoqa-han-sans/css/SpoqaHanSans-kr.css);

* { 
    font-family: 'Spoqa Han Sans', 'Spoqa Han Sans JP', 'Sans-serif'; 
  }
</style>