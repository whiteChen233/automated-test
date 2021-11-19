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
    }
  }

}
</script>
