import { login } from '@/api/user'

export default {
  namespaced: true,
  state: {
    token: null
  },
  mutations: {
    setToken: (state, payload) => {
      state.token = payload
      sessionStorage.setItem('token', payload)
    },
    delToken: (state) => {
      state.token = null
    }
  },
  actions: {
    login ({ commit }, payload) {
      return new Promise((resolve, reject) => {
        login(payload).then(r => {
          if (r.code === 0) {
            const { token } = r.data
            commit('setToken', token)
          }
          resolve(r)
        }).catch(e => reject(e))
      })
    }
  },
  getters: {

  }
}
