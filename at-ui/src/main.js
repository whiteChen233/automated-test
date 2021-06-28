import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router/router'
import store from '@/store/store'
import vuetify from '@/plugins/vuetify'
import i18n from '@/plugins/i18n'
import '@/plugins/base'
import '@/plugins/chartist'
import '@/plugins/vee-validate'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: h => h(App),
}).$mount('#app')
