<template>
  <v-main>
      <keep-alive :max="10">
        <router-view />
      </keep-alive>
  </v-main>
</template>

<script>
export default {
  name: 'MainView'
}
</script>
