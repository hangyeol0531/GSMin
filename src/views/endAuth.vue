<template>
  <div>
    <v-app>
    <v-app-bar app height="100px" flat></v-app-bar>
    <v-content>
    <v-container class="fill-height" fluid>
    <v-row>
      <v-col></v-col>
      <v-col md="3" align="center" justify="center">
        <v-card flat>
          <div align="center" justify="center">
            <v-img src="../assets/full_logo.svg" max-width="243"></v-img>
            <!-- <v-img src="../assets/GSMin.svg" class="mx-auto" max-width="158"></v-img> -->
          </div>          
        </v-card>
            <v-card flat>
            <v-card flat align="left">
              <v-card-text justify="left" >
                  <v-img src="../assets/finish.svg" max-width="182" class="mr-10"></v-img>
                    <p class="finish_message">가입이 완료되었습니다!</p>
                    <p class="finish_message">이제 <strong>GSMin</strong>에서 함께 이야기를 나누어 보세요.</p>
              </v-card-text>
            </v-card>
              <v-card-text>
                <v-btn color="#41AFE5" rounded block dark x-large @click="submit" >
                    <strong class="title">홈으로 이동</strong>
                </v-btn>
              </v-card-text>
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
import { required, email } from 'vee-validate/dist/rules'
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

  export default {
    data () {
        return {
            email: '',
            confirmValue: false
        }
    },
    methods: {
      submit () {
        this.$refs.reg_ob.validate().then(valid => {
            if (valid) {
                this.$http.post('/emailCheck', {
                    email: this.email+'@gsm.hs.kr'
                }).then((res) => {
                    this.confirmValue = true
                }).catch(e => {
                    console.log(e)
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
.href {
  color:#41AFE5;
  text-decoration-color: red;
}
.href:link, .href:visited{
  color:#41AFE5;
  text-decoration: none;
}
.finish_message{
    font-size:1.2rem;
    color:black;
}

</style>