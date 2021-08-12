import Vue from 'vue'
import VueI18n from 'vue-i18n'

import zhHans from 'vuetify/lib/locale/zh-Hans'
import en from 'vuetify/lib/locale/en'

Vue.use(VueI18n)

export default new VueI18n({
  locale: process.env.VUE_APP_I18N_LOCALE || 'zh',
  fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE || 'zh',
  messages: {
    zh: {
      ...require('@/lang/zh'),
      $vuetify: zhHans
    },
    en: {
      ...require('@/lang/en'),
      $vuetify: en
    }
  }
})
