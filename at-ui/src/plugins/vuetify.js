import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import i18n from '@/plugins/i18n'

Vue.use(Vuetify)

const options = {
  lang: {
    t: (key, ...params) => i18n.t(key, params)
  }
}

export default new Vuetify(options)
