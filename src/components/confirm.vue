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
              <v-card-text justify="left">
                  <v-img src="../assets/student_confirm.svg" max-width="329" class="mr-10"></v-img>
              </v-card-text>
            </v-card>
              <v-card-text>
              <ValidationObserver ref="reg_ob" v-slot="{ }">
                <form>
                  <ValidationProvider v-slot="{ errors }" name="email" rules="required">
                    <v-text-field
                    outlined
                    solo
                    flat
                    v-model="email"
                    :error-messages="errors"
                    label="이메일"
                    suffix="@gsm.hs.kr"
                    required
                    ></v-text-field>
                  </ValidationProvider>
                  <div v-if="confirmValue == false">
                    <v-btn color="#41AFE5" rounded block dark x-large @click="submit" >
                        <strong class="title">로그인</strong>
                    </v-btn>
                  </div>
                  <div v-if="confirmValue == true">
                    <v-card flat align="left">
                      <p class = "message">인증 메일이 발송되었습니다!</p>
                      <p class = "message"><strong class="message_strong">메일함</strong>을 확인해 주세요.</p>
                    </v-card>
                  </div>
                </form>
              </ValidationObserver>
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

.message {
  color:black;
  font-size: 1.3rem;
  line-height:140%
}

.message_strong {
  color:#41AFE5;
}
</style>