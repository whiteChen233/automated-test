import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    drawer: null,
    barColor: {
      all: ['rgba(250, 250, 250, 1), rgba(250, 250, 250, .7)', 'rgba(0, 0, 0, 1), rgba(0, 0, 0, .7)'],
      idx: 0
    },
    barImage: undefined
  },
  mutations: {
    setDrawer (state, payload) {
      state.drawer = payload
    },
    setBarImage (state, payload) {
      state.barImage = payload
    },
    changeBarColor (state) {
      state.barColor.idx = (state.barColor.idx + 1) % 2
    }
  },
  actions: {
  },
  getters: {
    barColor: state => state.barColor.all[state.barColor.idx]
  },
  modules: {
    user
  }
})
