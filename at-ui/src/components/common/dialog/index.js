import Dialog from './Dialog.vue'

export default (Vue, globalConfig = {}) => {
  let currentDialog = null
  let queue = []
  Dialog.vuetify = globalConfig.vuetify

  /**
   * 临时创建v-dialog组件，向用户显示一个对话框
   *
   * @param {Object} config
   * v-dialog的部分配置，还有一些其它的配置
   * 参见 https://vuetifyjs.com/zh-Hans/components/dialogs/
   * 这个配置会覆盖使用Vue.use安装插件时设定的全局配置
   * @param {Boolean} [config.dark = false] 为组件设置深色主题
   * @param {Boolean} [config.light = false] 为组件设置浅色主题
   * @param {Number} [config.maxWidth = 480] 设定组件的最大宽度
   * @param {Number} [config.maxHeight] 设定组件的最大高度
   * @param {Boolean} [config.noClickAnimation = false] 使用persistent属性并在对话框之外点击时，禁用弹跳效果
   * @param {String} [config.origin = "center center"] 设置动画原点，也就是CSS的transform-origin
   * @param {Boolean} [config.hideOverlay = false] 隐藏遮罩层的显示
   * @param {String} [config.overlayColor] 设置遮罩层的颜色
   * @param {Number} [config.overlayOpacity] 设置遮罩层的透明度
   * @param {Boolean} [config.persistent = false] 在对话框之外点击时不会关闭对话框
   * @param {String} [config.transition = "dialog-transition"] 设置组件动画，参见 https://vuetifyjs.com/zh-Hans/styles/transitions/
   *
   * @param {String} [config.title] 对话框的标题
   * @param {String} config.content 对话框的内容
   * @param {Boolean} [config.rawHtml = false] 内容是否使用原始HTML
   * @param {Array} config.buttons 对话框的按钮，从左往右排序
   * @param {String} config.buttons[].text 按钮的文本
   * @param {String} [config.buttons[].color = "primary darken-1"] 按钮的文本颜色
   * @param {Function} [config.buttons[].onClick] 点击按钮后执行的回调函数，参数是文本框的值，执行后（如果有设定的话）再关闭对话框并执行onClose
   * @param {Boolean} [config.stackedButtons = false] 按钮是否垂直排列
   * @param {Function} [config.onOpen] 对话框显示后执行的回调函数
   * @param {Function} [config.onClose] 对话框关闭后执行的回调函数
   * @param {Object} [config.prompt] 在对话框中使用文本框的配置
   * @param {Boolean} [config.prompt.enable = false] 是否显示文本框
   * @param {String} [config.prompt.label] 文本框上方的标签
   * @param {String} [config.prompt.hint] 文本框下方的提示
   * @param {Number} [config.prompt.rows = 1] 文本框的行数，超过1就使用v-textarea而不是v-text-field
   * @param {Boolean} [config.prompt.password = false] 是否使用type="password"，使用v-textarea时无效
   * @param {Number} [config.prompt.maxLength] 最大输入字符数量，请在rules中添加函数用于检验
   * @param {String} [config.prompt.value] 文本框的初始值
   * @param {String} [config.prompt.placeholder] 文本框的占位符
   * @param {Array} [config.prompt.rules] 文本框的验证函数数组
   * @param {Function} [config.prompt.rules[]] 文本框的验证函数，参数是文本框的值，返回true/false或错误消息
   */
  const dialog = config => {
    // 覆盖全局配置
    config = {
      ...globalConfig,
      ...config
    }

    if (currentDialog) {
      // 已有显示的组件，就加入队列
      queue.push(config)
    } else {
      // 否则直接创建组件，应用配置，$mount后显示
      currentDialog = new Vue(Dialog)
      Object.assign(currentDialog, config)
      currentDialog.$mount()

      if (currentDialog.onOpen) currentDialog.onOpen()

      // 在组件的显示状态变成false后销毁组件
      currentDialog.$on('activeChange', newval => {
        if (newval) return

        if (currentDialog.onClose) currentDialog.onClose()

        currentDialog.$nextTick(() => {
          const d = currentDialog
          currentDialog = null
          // 300ms是关闭对话框的动画时长
          setTimeout(() => {
            d.$destroy()
          }, 300)

          // 销毁后从队列获取数据创建下一个组件
          if (queue.length) dialog(queue.shift())
        })
      })
    }
  }

  const clearQueue = () => {
    queue = []
  }

  const shortcuts = {
    /**
     * 打开一个警告框
     * @param {String} content 对话框的内容
     * @param {String} [title] 对话框的标题
     * @param {Function} [onConfirm] 点击确认按钮的回调函数
     * @param {Object} [config] 其他的配置
     * @param {String} [config.confirmText = "OK"] 确认按钮的文本
     */
    alert: (content, title, onConfirm, config = {}) => {
      dialog({
        ...config,
        title,
        content,
        buttons: [{
          text: config.confirmText || 'OK',
          onClick: onConfirm
        }]
      })
    },

    /**
     * 打开一个确认框
     * @param {String} content 对话框的内容
     * @param {String} [title] 对话框的标题
     * @param {Function} [onConfirm] 点击确认按钮的回调函数
     * @param {Function} [onCancel] 点击取消按钮的回调函数
     * @param {Object} [config] 其他的配置
     * @param {String} [config.confirmText = "OK"] 确认按钮的文本
     * @param {String} [config.cancelText = "Cancel"] 取消按钮的文本
     */
    confirm: (content, title, onConfirm, onCancel, config = {}) => {
      dialog({
        ...config,
        title,
        content,
        buttons: [
          {
            text: config.cancelText || 'Cancel',
            onClick: onCancel
          },
          {
            text: config.confirmText || 'OK',
            onClick: onConfirm
          }
        ]
      })
    },

    /**
     * 打开一个输入框
     * @param {String} label 文本框的标签
     * @param {String} [title] 对话框的标题
     * @param {Function} [onConfirm] 点击确认按钮的回调函数，参数是文本框的值，但是超出了长度限制就会执行onCancel
     * @param {Function} [onCancel] 点击取消按钮的回调函数，参数是文本框的值
     * @param {Object} [config] 其他的配置，prompt部分也写在这里，而不要单独写一个prompt:{...}
     * @param {String} [config.confirmText = "OK"] 确认按钮的文本
     * @param {String} [config.cancelText = "Cancel"] 取消按钮的文本
     */
    prompt: (label, title, onConfirm, onCancel, config = {}) => {
      dialog({
        ...config,
        title,
        buttons: [
          {
            text: config.cancelText || 'Cancel',
            onClick: onCancel
          },
          {
            text: config.confirmText || 'OK',
            onClick: value => {
              const callback = (value.length > (config.maxLength || -1)) ? onCancel : onConfirm
              if (callback) return callback(value)
            }
          }
        ],
        prompt: {
          ...config,
          enable: true,
          label
        }
      })
    },

    promises: {
      /**
       * 打开一个警告框，返回Promise
       * @param {String} content 对话框的内容
       * @param {String} [title] 对话框的标题
       * @param {Function} [onConfirm] 点击确认按钮的回调函数
       * @param {Object} [config] 其他的配置
       * @param {String} [config.confirmText = "OK"] 确认按钮的文本
       * @return {Promise}
       */
      alert: (content, title, config = {}) => new Promise(resolve => {
        shortcuts.alert(content, title, resolve, config)
      }),

      /**
       * 打开一个确认框，返回Promise
       * @param {String} content 对话框的内容
       * @param {String} [title] 对话框的标题
       * @param {Object} [config] 其他的配置
       * @param {String} [config.confirmText = "OK"] 确认按钮的文本
       * @param {String} [config.cancelText = "Cancel"] 取消按钮的文本
       * @return {Promise}
       */
      confirm: (content, title, config = {}) => new Promise((resolve, reject) => {
        shortcuts.confirm(content, title, resolve, reject, config)
      }),

      /**
       * 打开一个输入框，返回Promise
       * @param {String} label 文本框的标签
       * @param {String} [title] 对话框的标题
       * @param {Object} [config] 其他的配置，prompt部分也写在这里，而不要单独写一个prompt:{...}
       * @param {String} [config.confirmText = "OK"] 确认按钮的文本
       * @param {String} [config.cancelText = "Cancel"] 取消按钮的文本
       * @return {Promise}
       */
      prompt: (label, title, config = {}) => new Promise((resolve, reject) => {
        shortcuts.prompt(label, title, resolve, reject, config)
      })
    }
  }

  Vue.prototype.$dialog = Object.assign(dialog, {
    clearQueue,
    ...shortcuts
  })
}
