<template>
  <v-row
    dense
    no-gutters
    style="width:150px"
    align="center"
    justify="center"
  >
    <v-col cols="2">
      <v-icon>mdi-clock</v-icon>
    </v-col>
    <v-col cols="5">
      <v-select
        v-model="selectHour"
        full-width
        dense
        hide-details
        :items="hourItems"
        :menu-props="{offsetY: true, maxHeight: 200}"
        @change="handleInput"
      />
    </v-col>
    <v-col cols="5">
      <v-select
        v-model="selectMinute"
        full-width
        dense
        hide-details
        :items="minuteItems"
        :menu-props="{offsetY: true, maxHeight: 200}"
        @change="handleInput"
      />
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'TimePinker',
  model: {
    prop: 'time',
    event: 'input'
  },
  props: {
    hour: {
      type: Number,
      default: 24
    },
    minute: {
      type: Number,
      default: 60
    },
    value: String
  },
  event: {
    input (val) {
      console.log(val)
    }
  },
  data: () => ({
    hourItems: [],
    minuteItems: [],
    selectHour: undefined,
    selectMinute: undefined
  }),
  created () {
    this.hourItems = this.getHour()
    this.minuteItems = this.getMinute()

    this.selectHour = this.hourItems[0]
    this.selectMinute = this.minuteItems[0]
  },
  methods: {
    createItems (length, step) {
      return Array.from({ length }, (x, i) => i * step)
    },
    getMinute () {
      return this.createItems(this.minute, this.minute / 60).map(this.fillZero)
    },
    getHour () {
      return this.createItems(this.hour, this.hour / 12).map(i => this.fillZero(i + 1))
    },
    fillZero (num) {
      let pre = ''
      if (num < 10) {
        pre = '0'
      }
      return pre + num
    },
    handleInput () {
      const val = this.selectHour + ':' + this.selectMinute
      // 通过input标签的原生事件input将值emit出去，以达到值得改变实现双向绑定
      this.$emit('input', val)
    }
  }
}
</script>
