<template>
  <v-app>
    <v-main class="bg">
      <v-container class="fill-height">
        <v-row justify="center">
          <v-spacer />
          <v-col cols="5">
            <v-form ref="form">
              <v-card class="px-8 pt-5 pb-3" elevation="5">
                <v-card-title> 登录 </v-card-title>
                <v-card-text>
                  <v-text-field
                    v-model="login.username"
                    type="text"
                    label="用户名"
                    prepend-inner-icon="mdi-account"
                    validate-on-blur
                    :rules="[rules.required,rules.username]"
                  />
                  <v-text-field
                    v-model="login.password"
                    label="密码"
                    prepend-inner-icon="mdi-lock"
                    validate-on-blur
                    :rules="[rules.required,rules.password]"
                    :append-icon="isshow ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="isshow ? 'text' : 'password'"
                    @click:append="isshow = !isshow"
                  />
                </v-card-text>
                <v-card-actions>
                  <v-spacer />
                  <v-btn class="blue" @click="doLogin"> 登录 </v-btn>
                  <v-btn class="ml-3" @click="$refs.form.reset()"> 重置 </v-btn>
                </v-card-actions>
              </v-card>
            </v-form>
          </v-col>
          <v-spacer />
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'Login',
  data: () => ({
    isshow: false,
    login: {},
    rules: {
      required: (value) => !!value || '必填项',
      username: (value) => {
        if (!(value && value.length >= 6 && value.length <= 18)) return '账号必须为5-18位英文+数字'
        const pattern = /^[\da-zA-z]{5,16}$/
        return pattern.test(value) || '请填写正确格式的账号'
      },
      password: (value) => {
        if (value && (value.length < 8 || value.length > 18)) return '密码必须为6-18位英文+数字'
        const pattern = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{8,16}$/
        return pattern.test(value) || '请填写正确格式的密码'
      }
    }
  }),
  methods: {
    doLogin () {
      if (this.$refs.form.validate()) {
        this.$router.push({ name: 'Home' })
      }
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
<style scoped>
.bg {
  background: url("~@/assets/login.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
</style>
