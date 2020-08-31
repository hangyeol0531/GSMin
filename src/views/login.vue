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
                </div>
              </v-card>
              <v-card flat>
                <v-card-text>
                  <ValidationObserver ref="login_ob" v-slot="{}">
                    <form>
                      <ValidationProvider v-slot="{ errors }" name="이메일" rules="required|email">
                        <v-text-field
                          outlined
                          v-model="email"
                          :error-messages="errors"
                          solo
                          flat
                          label="이메일"
                          required
                        ></v-text-field>
                      </ValidationProvider>
                      <ValidationProvider v-slot="{ errors }" name="비밀번호" rules="required">
                        <v-text-field
                          outlined
                          solo
                          flat
                          type="password"
                          v-model="password"
                          :error-messages="errors"
                          v-on:keyup.enter="submit"
                          label="비밀번호"
                          hide-details="auto"
                          required
                        ></v-text-field>
                      </ValidationProvider>
                      <v-card-title>
                        <v-btn color="#41AFE5" rounded block dark x-large @click="submit">
                          <strong class="title">로그인</strong>
                        </v-btn>
                      </v-card-title>
                    </form>
                  </ValidationObserver>
                </v-card-text>
                <v-container>
                  <v-card flat align="right">
                    <p>
                      <strong>GSMin</strong>이 처음이신가요?
                      <router-link :to="{name : 'confirm'}" class="href">
                        <strong>회원가입</strong>
                      </router-link>
                    </p>
                  </v-card>
                  <v-card flat align="right">
                    <p>
                      <router-link :to="{name : 'confirm'}" class="href">
                        <strong>아이디/비밀번호 찾기</strong>
                      </router-link>
                    </p>
                  </v-card>
                </v-container>
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
import { required, email } from "vee-validate/dist/rules";
import { extend, ValidationObserver, ValidationProvider } from "vee-validate";

extend("required", {
  ...required,
  message: "{_field_} 칸을 채워주세요",
});

extend("email", {
  ...email,
  message: "올바른 이메일 형식을 입력해 주세요",
});

export default {
  data() {
    return {
      email: "",
      password: "",
      chpassword: "",
    };
  },

  created() {
    localStorage.removeItem("token");
  },

  methods: {
    submit() {
      this.$refs.login_ob.validate().then((valid) => {
        if (valid) {
          let email = this.email;
          let pw = this.password;

          this.$store.dispatch("auth/login", { email, pw });
        }
      });
    },
  },
};
</script>

<style>
@import url(//spoqa.github.io/spoqa-han-sans/css/SpoqaHanSans-kr.css);

* {
  font-family: "Spoqa Han Sans", "Spoqa Han Sans JP", "Sans-serif";
}
.href {
  color: #41afe5;
  text-decoration-color: red;
}
.href:link,
.href:visited {
  color: #41afe5;
  text-decoration: none;
}
</style>