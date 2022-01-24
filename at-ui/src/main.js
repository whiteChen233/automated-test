import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import vuetify from '@/plugins/vuetify'
import i18n from '@/plugins/i18n'
import api from '@/api'
import SnackbarComponent from '@/components/common/snackbar'
import DialogComponent from '@/components/common/dialog'

Vue.use(SnackbarComponent, { vuetify, timeout: 3000 })
Vue.use(DialogComponent, { vuetify })

Vue.config.productionTip = false

Vue.prototype.$api = api

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app')
