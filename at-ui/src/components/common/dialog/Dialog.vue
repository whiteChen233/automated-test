<template>
  <v-dialog
    v-model="active"
    :dark="dark"
    :light="light"
    :hide-overlay="hideOverlay"
    :max-width="maxWidth"
    :no-click-animation="noClickAnimation"
    :origin="origin"
    :overlay-color="overlayColor"
    :overlay-opacity="overlayOpacity"
    :persistent="persistent"
    :transition="transition"
    scrollable
  >
    <v-card :style="{ maxHeight: `${maxHeight}px` }">
      <v-card-title v-if="title" class="headline">{{ title }}</v-card-title>
      <v-card-text>
        <div v-if="rawHtml" v-html="content"></div>
        <div v-else>{{ content }}</div>
        <template v-if="prompt.enable">
          <v-textarea
            v-if="prompt.rows > 1"
            v-model="prompt.value"
            no-resize
            :label="prompt.label"
            :hint="prompt.hint"
            :rows="prompt.rows"
            :counter="prompt.maxLength || false"
            :placeholder="prompt.placeholder"
            :dark="dark"
            :light="light"
          ></v-textarea>
          <v-text-field
            v-else
            v-model="prompt.value"
            :label="prompt.label"
            :hint="prompt.hint"
            :type="prompt.password ? 'password' : 'text'"
            :counter="prompt.maxLength || false"
            :placeholder="prompt.placeholder"
            :dark="dark"
            :light="light"
          ></v-text-field>
        </template>
      </v-card-text>
      <v-card-actions v-if="buttons.length">
        <v-spacer></v-spacer>
        <div
          :class="[
            'd-flex',
            `flex-${stackedButtons ? 'column' : 'row'}`,
            'align-end',
          ]"
        >
          <v-btn
            v-for="(item, index) in buttons"
            :key="index"
            :color="
              item.color ||
              `primary ${dark ? 'lighten-1' : light ? 'darken-1' : ''}`
            "
            text
            class="d-block"
            @click="
              if (item.onClick) item.onClick(prompt.value);
              active = false;
            "
            >{{ item.text }}</v-btn
          >
        </div>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    title: { type: String },
    content: { type: String },
    rawHtml: { type: Boolean, default: false },
    buttons: { type: Array },
    stackedButtons: { type: Boolean, default: false },
    onOpen: { type: Function },
    onClose: { type: Function },
    maxHeight: { type: Number },
    prompt: {
      type: Object,
      default () {
        return {
          enable: false,
          label: '',
          hint: '',
          rows: 1,
          maxLength: null,
          value: '',
          placeholder: ''
        }
      }
    },

    // 以下是v-dialog自带的配置
    // https://vuetifyjs.com/zh-Hans/components/dialogs/
    dark: { type: Boolean, default: false },
    light: { type: Boolean, default: false },
    hideOverlay: { type: Boolean, default: false },
    maxWidth: { type: Number, default: 480 },
    noClickAnimation: { type: Boolean, default: false },
    origin: { type: String, default: 'center center' },
    overlayColor: { type: String },
    overlayOpacity: { type: Number },
    persistent: { type: Boolean, default: false },
    transition: { type: String, default: 'dialog-transition' }
  },
  data () {
    return {
      active: false
    }
  },
  watch: {
    active (newval) {
      this.$emit('activeChange', newval)
    }
  },
  mounted () {
    this.active = true
    if (this.dark === this.light) {
      this.dark = this.$vuetify.theme.isDark
      this.light = !this.dark
    }
    this.prompt.value = this.prompt.value || ''
  }
}
</script>
