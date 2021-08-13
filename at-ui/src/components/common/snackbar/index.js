import Snackbar from './Snackbar.vue'

export default (Vue, globalConfig = {}) => {
  let currentSnackbar = null
  let queue = []
  Snackbar.vuetify = globalConfig.vuetify

  /**
   * 临时创建v-snackbar组件，使用Snackbar向用户显示一个通知
   *
   * @param {String} text Snackbar的文本
   * @param {Object} [config]
   * v-snackbar的部分配置，还有一些其它的配置
   * 参见 https://vuetifyjs.com/zh-Hans/components/snackbars/
   * 这个配置会覆盖使用Vue.use安装插件时设定的全局配置
   * @param {Boolean} [config.absolute = false] 给组件应用position:absolute
   * @param {Boolean} [config.bottom = false] 将组件向底部对齐
   * @param {String} [config.color] 将指定的颜色应用于控件
   * @param {Boolean} [config.left = false] 将组件向左边对齐
   * @param {Boolean} [config.multiLine = false] 使消息条具有更大的最低高度
   * @param {Boolean} [config.right = false] 将组件向右边对齐
   * @param {Number} [config.timeout = 6000] 等待Snackbars自动隐藏的时间，如果为0表示永久开启
   * @param {Boolean} [config.top = false] 将组件向顶部对齐
   * @param {Boolean} [config.vertical = false] 将消息条内容堆叠在按钮之上
   *
   * @param {String} [config.buttonText] 按钮的文本，如果为空就不显示按钮
   * @param {String} [config.buttonColor = "primary lighten-1"] 按钮的文本颜色
   * @param {Boolean} [config.closeOnClick = false] 点击Snackbar执行完回调函数后是否关闭Snackbar
   * @param {Boolean} [config.closeOnButtonClick = true] 点击按钮执行完回调函数后是否关闭Snackbar
   * @param {Function} [config.onClick] 点击Snackbar执行的回调函数
   * @param {Function} [config.onButtonClick] 点击按钮执行的回调函数
   * @param {Function} [config.onOpen] Snackbar显示后执行的回调函数
   * @param {Function} [config.onClose] Snackbar关闭后执行的回调函数
   */
  const toast = (text, config = {}) => {
    // 覆盖全局配置
    config = {
      ...globalConfig,
      text,
      ...config
    }

    if (currentSnackbar) {
      // 已有显示的组件，就加入队列
      queue.push(config)
    } else {
      // 否则直接创建组件，应用配置，$mount后显示
      currentSnackbar = new Vue(Snackbar)
      Object.assign(currentSnackbar, config)
      document.body.appendChild(currentSnackbar.$mount().$el)

      if (currentSnackbar.onOpen) currentSnackbar.onOpen()

      // 在组件的显示状态变成false后销毁组件
      currentSnackbar.$on('activeChange', newval => {
        if (newval) return

        if (currentSnackbar.onClose) currentSnackbar.onClose()

        currentSnackbar.$nextTick(() => {
          currentSnackbar.$destroy()
          // $destroy以后，组件的DOM会变成空节点<!---->，也可以删掉
          document.body.removeChild(currentSnackbar._vnode.elm)
          currentSnackbar = null

          // 销毁后从队列获取数据创建下一个组件
          if (queue.length) toast(null, queue.shift())
        })
      })
    }
  }

  const clearQueue = () => {
    queue = []
  }

  // 一些封装
  const shortcuts = {};
  ['success', 'info', 'error', 'warning'].forEach(color => {
    shortcuts[color] = (text, config = {}) => {
      toast(text, {
        ...config,
        color,
        buttonColor: 'white'
      })
    }
  })

  Vue.prototype.$toast = Object.assign(toast, {
    clearQueue,
    ...shortcuts
  })
}
