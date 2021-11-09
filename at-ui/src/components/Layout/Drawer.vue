<template>
  <v-navigation-drawer
    :value="drawer"
    app
  >
    <v-list>
      <v-list-item to="/">
        <v-list-item-title>首页</v-list-item-title>
      </v-list-item>
      <v-list-item to="/about">
        <v-list-item-title>关于</v-list-item-title>
      </v-list-item>
      <v-list-item to="/input/1">
        <v-list-item-title>输入1</v-list-item-title>
      </v-list-item>
      <v-list-item to="/input/2">
        <v-list-item-title>输入2</v-list-item-title>
      </v-list-item>
      <v-list-item to="/input/3">
        <v-list-item-title>输入3</v-list-item-title>
      </v-list-item>
      <v-list-item to="/input/4">
        <v-list-item-title>输入4</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'NavDrawer',
  computed: {
    drawer () {
      return this.$store.state.drawer
    }
  }
}
</script>

<style>

</style>
