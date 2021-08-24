<template>
  <v-app-bar
    app
    dense
  >
    <v-app-bar-nav-icon @click="drawer = !drawer">
      <v-icon>mdi-menu</v-icon>
    </v-app-bar-nav-icon>
    <v-tabs
      v-model="currTab"
      optional
    >
      <v-tab
        v-for="(t, i) in tabs"
        :key="t.name"
        :to="t.path"
      >
        {{ t.title }}
        <v-icon
          v-if="i !== 0"
          @click.stop.prevent="remove(i)"
        >
          mdi-close
        </v-icon>
      </v-tab>
    </v-tabs>
  </v-app-bar>
</template>

<script>
export default {
  name: 'AppBar',
  data: () => ({
    currTab: 0,
    tabs: []
  }),
  watch: {
    $route (to) {
      this.add(to)
    }
  },
  created () {
    this.tabs.push({ path: '/', title: 'Home' })
  },
  methods: {
    add (route) {
      const idx = this.tabs.map(i => i.path).indexOf(route.path)
      if (idx < 0) {
        const tab = { path: route.path, title: route.name }
        this.tabs.push(tab)
      }
    },
    remove (i) {
      const del = this.tabs[i]
      if (del.path === this.$route.path) {
        this.$router.push(this.tabs[i - 1].path)
      }
      this.tabs.splice(i, 1)
    }
  }
}
</script>
