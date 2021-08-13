<template>
  <v-app>
    <app-bar />
    <nav-drawer />
    <main-view />
  </v-app>
</template>

<script>
export default {
  name: 'Layout',
  components: {
    AppBar: () => import('./AppBar'),
    NavDrawer: () => import('./Drawer'),
    MainView: () => import('./View')
  }
}
</script>
