<template>
  <div>
    <hello-world />
    {{ $t('default.test') }}
    <v-btn @click="init" >点我</v-btn>
  </div>
</template>

<script>
import HelloWorld from '../components/HelloWorld'

export default {
  name: 'Home',

  components: {
    HelloWorld
  },
  methods: {
    init () {
      console.log(this)
    }
  }

}
</script>
