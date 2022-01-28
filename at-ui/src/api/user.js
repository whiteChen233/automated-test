import axios from '@/plugins/axios'

export function login (params) {
  return axios.post('/login', params)
}

export function logout () {
  return axios.post('/logout')
}
