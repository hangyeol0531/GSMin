<template>
  <router-view :key="$route.fullPath"></router-view>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  })
}
</script>
