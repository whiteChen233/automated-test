<template>
  <div>
    {{ $t('test') }}
    <v-btn @click="init">点我</v-btn>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    init () {
      console.log(this)
    },
    test () {
      this.$toast('哈哈哈')
      this.$toast.success('哈哈哈')
      this.$toast.info('哈哈哈')
      this.$toast.error('哈哈哈')
      this.$toast.warning('哈哈哈')

      this.$dialog.alert('这是一个弹框', '标题')
      this.$dialog.confirm('这是一个弹框', '标题')
      this.$dialog.prompt('这是一个弹框', '标题')
    }
  }

}
</script>
