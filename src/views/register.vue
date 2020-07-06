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
            <v-card flat>
            <v-card flat align="left">
            <v-card-text justify="left">
                <v-img src="../assets/input_info.svg" max-width="182" class="mr-10"></v-img>
            </v-card-text>
            </v-card>
              <v-card-text>
              <ValidationObserver ref="reg_ob" v-slot="{ }">
                <form>
                  <ValidationProvider v-slot="{ errors }" name="비밀번호" rules="required|min:8" vid="pw">
                    <v-text-field
                    type="password"
                    outlined
                    solo
                    flat
                    v-model="password"
                    :error-messages="errors"
                    counter
                    maxlength="16"
                    label="비밀번호(8~16자)"
                    required
                    ></v-text-field>
                  </ValidationProvider>                    
                  <ValidationProvider v-slot="{ errors }" name="비밀번호 확인" rules="confirmed:pw|required">
                    <v-text-field
                    type="password"
                    outlined
                    solo
                    flat
                    v-model="passwordConfirm"
                    :error-messages="errors"
                    label="비밀번호 확인"
                    required
                    ></v-text-field>
                  </ValidationProvider>                    
                  <ValidationProvider v-slot="{ errors }" name="닉네임" rules="required">
                    <v-text-field
                    outlined
                    solo
                    flat
                    v-model="nickname"
                    :error-messages="errors"
                    label="닉네임"
                    required
                    ></v-text-field>
                  </ValidationProvider>
                    <v-btn color="#41AFE5" rounded block dark x-large @click="submit" >
                        <strong class="title">회원가입</strong>
                    </v-btn>
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
                    email: `${this.email}@gsm.hs.kr`,
                    pw: this.password,
                    nickname : this.nickname,
                }).then((res) => {
                    this.$router.push({name : 'login'})
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