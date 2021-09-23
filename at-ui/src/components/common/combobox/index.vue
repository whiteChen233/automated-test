<template>
  <v-combobox
    :value="selected"
    :label="label"
    :items="items"
    :item-text="itemText"
    :item-value="itemValue"
    :multiple="multiple"
    :dense="dense"
    :outlined="outlined"
    :menu-props="menuProps"
    :validate-on-blur="validateOnBlur"
    :rules="rules"
    @change="input"
  >
    <template
      v-if="selectAll"
      #prepend-item
    >
      <v-list-item
        ripple
        @click="toggle"
      >
        <v-list-item-action>
          <v-icon :color="selected.length > 0 ? 'indigo darken-4' : ''">
            {{ icon }}
          </v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title> Select All </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-divider class="mt-2" />
    </template>
    <template
      v-if="multiple"
      #selection="{ item, index }"
    >
      <span v-if="index === 0">
        {{ item[itemText] }}
      </span>
      <span
        v-if="index === 1"
        class="grey--text text-caption"
      >
        (+{{ selected.length - 1 }})
      </span>
    </template>
  </v-combobox>
</template>

<script>
export default {
  name: 'Combobox',
  props: {
    // 自定义的属性
    value: { type: [Array, String] },
    selectAll: { type: Boolean, default: false },
    // 控件原本就有的属性
    items: { type: Array, default: () => [] },
    label: { type: String },
    multiple: { type: Boolean, default: false },
    itemText: { type: String, default: 'text' },
    itemValue: { type: String, default: 'value' },
    dense: { type: Boolean, default: true },
    outlined: { type: Boolean, default: true },
    menuProps: { type: [Object, String] },
    validateOnBlur: { type: Boolean, default: false },
    rules: { type: Array, default: () => [] }
  },
  computed: {
    allSelected () {
      return this.selected.length === this.items.length
    },
    someSelected () {
      return !this.allSelected && this.selected.length > 0
    },
    icon () {
      if (this.allSelected) return 'mdi-close-box'
      if (this.someSelected) return 'mdi-minus-box'
      return 'mdi-checkbox-blank-outline'
    },
    selected () {
      if (this.multiple) {
        const indexOf = c => this.value && this.value.indexOf(c) > -1
        if (indexOf('*')) {
          return this.items.slice()
        } else {
          return this.items.filter(i => indexOf(i[this.itemValue]))
        }
      } else {
        return this.items.find(i => i[this.itemValue] === this.value)
      }
    }
  },
  methods: {
    toggle () {
      this.$nextTick(() => {
        if (this.allSelected) {
          this.selected = []
        } else {
          this.selected = this.items.slice()
        }
      })
    },
    input (val) {
      let result
      if (this.multiple) {
        if (this.value === ['*']) {
          result = this.value
        } else {
          result = val ? val.map(i => i[this.itemValue]) : []
        }
      } else {
        result = this.findValueInObject(this.items[this.items.indexOf(val)])
      }
      this.$emit('input', result)
    },
    findValueInObject (val) {
      return val && Object.entries(val).find(([k, v]) => k === this.itemValue)[1]
    }
  }
}
</script>
