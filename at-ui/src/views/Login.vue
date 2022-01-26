<template>
  <v-app>
    <v-main class="bg">
      <v-container class="fill-height">
        <v-row justify="center">
          <v-spacer/>
          <v-col cols="5">
            <v-form ref="form">
              <v-card class="px-8 pt-5 pb-3" elevation="5">
                <v-card-title> Automated Test</v-card-title>
                <v-card-text>
                  <v-text-field
                    v-model="loginBody.username"
                    type="text"
                    label="用户名"
                    prepend-inner-icon="mdi-account"
                    validate-on-blur
                    :rules="[rules.required,rules.username]"
                  />
                  <v-text-field
                    v-model="loginBody.password"
                    label="密码"
                    prepend-inner-icon="mdi-lock"
                    validate-on-blur
                    :rules="[rules.required,rules.password]"
                    :append-icon="showPwd ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="showPwd ? 'text' : 'password'"
                    @click:append="showPwd = !showPwd"
                  />
                </v-card-text>
                <v-card-actions>
                  <v-btn block class="blue" @click="doLogin">登录</v-btn>
                </v-card-actions>
              </v-card>
            </v-form>
          </v-col>
          <v-spacer/>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'Login',
  data: () => ({
    showPwd: false,
    loginBody: {},
    rules: {
      required: (value) => !!value || '必填项',
      username: (value) => /^\w{5,32}$/.test(value) || '5~32位字母数字下划线组合',
      password: (value) => /^([A-Za-z0-9._@]){8,32}$/.test(value) || '8~32位字母数字组合'
    }
  }),
  methods: {
    ...mapActions({
      login: 'user/login'
    }),
    doLogin () {
      if (!this.$refs.form.validate()) return
      this.login(this.loginBody).then(r => {
        this.$router.push('/')
      }).catch(e => {
        this.$toast.error(e)
      })
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
