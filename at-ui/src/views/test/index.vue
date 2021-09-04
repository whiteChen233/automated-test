<template>
  <div>
    <time-pinker v-model="time"/>
    <v-btn @click="time='13:03'">按钮</v-btn>
  </div>
</template>

<script>
export default {
  name: 'Test',
  components: {
    TimePinker: () => import('@/components/common/time-pinker/TimePinker')
  },
  data: () => ({
    time: ''
  }),
  watch: {
    time (val) {
      console.log(val)
    }
  }
}
</script>
