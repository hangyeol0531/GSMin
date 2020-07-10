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
                <v-btn color="#41AFE5" rounded block dark x-large @click="goHome('login')" >
                    <strong class="title">로그인으로 이동</strong>
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
  export default {
    methods: {
      goHome(name){
        this.$router.push({name})
      }
    }
  }


</script>

<style>
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