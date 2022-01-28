<template>
  <div>
    <span id="settings">主题设置</span>
    <v-menu
      v-model="menu"
      activator="#settings"
      bottom
      right
      offset-x
      content-class="v-settings"
      nudge-left="-15"
      origin="bottom left"
      transition="scale-transition"
      :close-on-content-click="false"
    >
      <v-card
        class="text-center mb-0"
        width="300"
      >
        <v-card-text>
          <strong class="mb-3 d-inline-block">SIDEBAR FILTERS</strong>
          <v-item-group v-model="color">
            <v-item
              v-for="color in colors"
              :key="color"
              :value="color"
              class="ml-1 mr-1"
            >
              <template v-slot="{ active, toggle }">
                <v-avatar
                  :color="color"
                  :class="['v-settings__item', active && 'v-settings__item--active']"
                  size="25"
                  @click="toggle"
                />
              </template>
            </v-item>
          </v-item-group>
          <v-divider class="my-4 secondary" />
          <v-row
            align="center"
            no-gutters
          >
            <v-col cols="auto">
              Dark Mode
            </v-col>
            <v-spacer />
            <v-col cols="auto">
              <v-switch
                v-model="darkTheme"
                class="ma-0 pa-0"
                color="secondary"
                hide-details
              />
            </v-col>
          </v-row>
          <v-divider class="my-4 secondary" />
          <v-row
            align="center"
            no-gutters
          >
            <v-col cols="auto">
              Sidebar Image
            </v-col>
            <v-spacer />
            <v-col cols="auto">
              <v-switch
                v-model="showImg"
                class="ma-0 pa-0"
                color="secondary"
                hide-details
              />
            </v-col>
          </v-row>
          <v-divider class="my-4 secondary" />
          <strong class="mb-3 d-inline-block">IMAGES</strong>
          <v-item-group
            v-model="image"
            class="d-flex justify-space-between mb-3"
          >
            <v-item
              v-for="image in images"
              :key="image"
              :value="image"
              class="mx-1"
            >
              <template v-slot="{ active, toggle }">
                <v-sheet
                  :class="['d-inline-block', 'v-settings__item', active && 'v-settings__item--active']"
                  @click="toggle"
                >
                  <v-img
                    :src="image"
                    height="100"
                    width="50"
                  />
                </v-sheet>
              </template>
            </v-item>
          </v-item-group>
        </v-card-text>
      </v-card>
    </v-menu>
  </div>
</template>

<script>
// Mixins
import Proxyable from 'vuetify/lib/mixins/proxyable'
import { mapMutations, mapState } from 'vuex'

export default {
  name: 'Settings',

  mixins: [Proxyable],

  data: () => ({
    colors: [
      '#9C27b0',
      '#2196F3',
      '#4CAF50',
      '#ff9800',
      '#E91E63',
      '#FF5252'
    ],
    image: undefined,
    images: [
      'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-1.jpg',
      'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-2.jpg',
      'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-3.jpg',
      'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-4.jpg'
    ],
    menu: false,
    saveImage: undefined
  }),

  computed: {
    ...mapState(['barImage']),
    darkTheme: {
      get () {
        return this.$vuetify.theme.dark
      },
      set (val) {
        this.changeBarColor()
        this.$vuetify.theme.themes[this.darkTheme ? 'light' : 'dark'].primary = this.color
        this.$vuetify.theme.dark = val
      }
    },
    color: {
      get () {
        return this.$vuetify.theme.themes[this.darkTheme ? 'dark' : 'light'].primary
      },
      set (val) {
        this.$vuetify.theme.themes[this.darkTheme ? 'dark' : 'light'].primary = val
      }
    },
    showImg: {
      get () {
        return this.$store.state.barImage
      },
      set (val) {
        if (!val) {
          this.saveImage = this.barImage
          this.setBarImage(undefined)
        } else if (this.saveImage) {
          this.setBarImage(this.saveImage)
          this.saveImage = undefined
        } else if (val instanceof String) {
          this.setBarImage(val)
        }
      }
    }
  },
  watch: {
    image (val) {
      this.setBarImage(val)
    }
  },
  methods: {
    ...mapMutations(['setBarImage', 'changeBarColor'])
  }
}
</script>

<style lang="sass">
.v-settings
  .v-item-group > *
    cursor: pointer

  &__item
    border-width: 3px
    border-style: solid
    border-color: transparent !important

    &--active
      border-color: #00cae3 !important

  &__item:not(.v-settings__item--active)
    border-color: #CFD8DC !important
</style>
