<template>
  <v-card flat>
    <v-card-title class="text-h5 mt-n3">
      {{ title }}
      <v-spacer />
      <v-btn text tile small color="primary" @click="collapseAll">
        <v-icon>mdi-arrow-collapse-vertical</v-icon>
      </v-btn>
      <v-btn text tile small color="primary" @click="expandAll">
        <v-icon>mdi-arrow-expand-vertical</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-treeview
        ref="treeview"
        :value="selection"
        :dense="dense"
        :items="items"
        :selection-type="selectionType"
        :selectable="selectable"
        :return-object="true"
        :open-all="openAll"
        :transition="transition"
        :open-on-click="openOnClick"
        :item-key="itemKey"
        :item-text="itemText"
        :style="{ overflowY: 'scroll', height: height - 60 + 'px' }"
        @input="input"
      />
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: 'Treeview',
  props: {
    value: { type: Object, default: () => ({ node: [], selected: [] }) },
    allParentNodes: { type: Boolean, default: false },
    title: { type: String },
    height: { type: Number },

    items: { type: Array, default: () => [] },
    selectionType: { type: String, default: 'leaf' },
    selectable: { type: Boolean, default: false },
    // returnObject: { type: Boolean, default: false },
    openAll: { type: Boolean, default: false },
    dense: { type: Boolean, default: true },
    transition: { type: Boolean, default: false },
    openOnClick: { type: Boolean, default: false },
    itemKey: { type: String, default: 'id' },
    itemText: { type: String, default: 'name' }
  },
  data: () => ({
    selection: []
  }),
  computed: {
    _items () {
      const replaceChildren = (obj, parent) => {
        const clone = Object.assign({}, obj)
        delete clone.children
        if (parent) clone.parent = parent
        return clone
      }
      const addItems = (arr, parent) => {
        const items = arr.reduce((acc, x) => {
          acc.push(replaceChildren(x, parent))
          if (x.children) {
            acc.push(addItems(x.children, x.id))
          }
          return acc
        }, [])
        return items.flat()
      }
      return addItems(this.items).reduce((acc, x) => {
        acc[x.id] = x
        return acc
      }, {})
    },
    _selection () {
      const proxy = {}
      const addParents = (x, all) => {
        const parentId = this._items[x.id].parent
        if (parentId) {
          if (all) addParents(this._items[parentId])
          proxy[parentId] = this._items[parentId]
        }
      }
      this.selection.forEach((x) => {
        addParents(x, this.allParentNodes)
        proxy[x.id] = x
      })
      return Object.values(proxy)
    }
  },
  methods: {
    expandAll () {
      this.$refs.treeview.updateAll(true)
    },
    collapseAll () {
      this.$refs.treeview.updateAll(false)
    },
    input (val) {
      this.selection = val
      this.$emit('input', {
        node: val,
        selected: this._selection.map((i) => i[this.itemKey])
      })
    }
  },
  watch: {
    value: {
      handler (v) {
        if (v.node) {
          const input = v.node
          if (typeof input[0] === 'number') {
            this.selection = input.map((i) => {
              const o = {}
              o[this.itemKey] = i
              o[this.itemText] = ''
              return o
            })
          } else {
            this.selection = input
          }
        } else {
          throw new Error('Unsupported data type')
        }
      },
      deep: true
    }
  }
}
</script>
