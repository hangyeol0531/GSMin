<template>
  <div>
    <v-app-bar app height="70px" flat color="white" hide-on-scroll>
      <v-spacer></v-spacer>
      <v-toolbar-title>
        <router-link :to="{name: 'Home'}">
          <v-img src="../assets/fixlogo.svg" max-width="100%"></v-img>
        </router-link>
      </v-toolbar-title>
      <v-spacer />
      <v-spacer />
      <v-spacer />
      <v-badge color="#00B1EA" content="6" bordered offset-x="22" offset-y="20" overlap>
        <v-btn icon>
          <v-icon large>notifications</v-icon>
        </v-btn>
      </v-badge>
      <v-btn @click="routerPush('Setting')" icon>
        <v-icon>settings</v-icon>
      </v-btn>
      <v-spacer></v-spacer>
    </v-app-bar>
    <slot></slot>
  </div>
</template>

<script>
export default {
  methods: {
    routerPush(name) {
      this.$router.push({ name });
    },
  },
};
</script>