<template>
  <v-main>
      <keep-alive>
        <router-view />
      </keep-alive>
  </v-main>
</template>

<script>
export default {
  name: 'MainView'
}
</script>
