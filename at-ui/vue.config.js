module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy: {
      // 配置跨域
      "/api": {
        // 真实的后台接口
        target: "http://localhost:8089/at/",
        ws: true,
        changOrigin: true, // 允许跨域
        pathRewrite: {
          "^/api": "" // 请求的时候使用这个api就可以
        }
      }
    }
  }
}
