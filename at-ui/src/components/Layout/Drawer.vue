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
    <template #append>
      <v-list-item>
        <v-list-item-avatar>
          <v-img
            v-if="userInfo.avatar"
            :src="userInfo.avatar"
          />
<!--          <v-img-->
<!--            v-else-->
<!--            src="@/assets/avatar.gif"-->
<!--          />-->
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>
            <span>{{ userInfo.nickname || userInfo.username }}</span>
          </v-list-item-title>
        </v-list-item-content>
        <v-list-item-icon>
          <v-menu offset-x>
            <template #activator="{ on, attrs }">
              <v-btn
                icon
                right
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-account-cog</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item to="/">
                <v-list-item-title>Home</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>
                  <settings />
                </v-list-item-title>
              </v-list-item>
              <v-list-item @click="logout">
                <v-list-item-title>{{ $t('user-exit') }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-list-item-icon>
      </v-list-item>
    </template>
  </v-navigation-drawer>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'

export default {
  name: 'NavDrawer',
  components: {
    Settings: () => import('./Settings')
  },
  computed: {
    ...mapState({
      barImage: state => state.barImage,
      userInfo: state => state.user.info
    }),
    ...mapGetters(['barColor']),
    drawer () {
      return this.$store.state.drawer
    }
  },
  methods: {
    ...mapActions({
      logout: 'user/logout'
    })
  }
}
</script>
