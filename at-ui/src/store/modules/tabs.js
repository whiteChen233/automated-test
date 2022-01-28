export default {
  namespaced: true,
  state: {
    data: [{
      title: 'home',
      path: '/'
    }],
    reopen: []
  },
  mutations: {
    openTab (state, payload) {
      state.data.push(payload)
    },
    closeTab (state, payload) {
      state.data.splice(payload, 1)
    },
    addReopen (state, payload) {
      if (state.reopen.every(i => i !== payload.title)) {
        state.reopen.push(payload.title)
      }
    },
    delReopen (state, payload) {
      state.reopen.splice(state.reopen.indexOf(payload), 1)
    }
  },
  actions: {
    add ({ commit, state }, payload) {
      const item = {
        path: payload.path,
        title: payload.meta.title
      }
      if (state.data.every(i => i.path !== item.path)) {
        commit('openTab', item)
        commit('addReopen', item)
      }
    },
    del ({ commit, state }, payload) {
      commit('ADD_REOPEN', state.data[payload])
      commit('closeTab', payload)
    },
    reload ({ commit, state, rootGetters }, payload) {
      const title = rootGetters.currentRoute.meta.title
      if (state.reopen.some(i => i === title)) {
        if (typeof payload === 'function') {
          payload()
        }
        commit('delReopen', title)
      }
    }
  },
  getters: {}
}
