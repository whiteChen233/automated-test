import { login, logout } from '@/api/user'

export default {
  namespaced: true,
  state: {
    token: undefined,
    info: {}
  },
  mutations: {
    setToken: (state, payload) => {
      state.token = payload
      sessionStorage.setItem('token', payload)
    },
    setInfo: (state, payload) => {
      state.info = payload
    },
    resetInfo: (state) => {
      state.token = undefined
      state.info = { ...defaultInfo }
      sessionStorage.removeItem('token')
    }
  },
  actions: {
    login ({ commit }, payload) {
      return new Promise((resolve, reject) => {
        login(payload).then(r => {
          if (r.code === 0) {
            const { token } = r.data
            commit('setToken', token)
            resolve(r)
          } else {
            reject(r)
          }
        }).catch(e => reject(e))
      })
    },
    logout ({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(r => {
          if (r.code === 0) {
            commit('resetInfo')
            resolve(r)
          } else {
            reject(r)
          }
        }).catch(e => reject(e))
      })
    }
  },
  getters: {}
}

const defaultInfo = {
  username: undefined,
  nickname: undefined,
  avatar: undefined,
  roles: [],
  permissions: [],
  session: undefined
}
