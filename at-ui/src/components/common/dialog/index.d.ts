import Vue from 'vue';

interface VuetifyDialogConfig {
  /** 为组件设置深色主题 */
  dark?: Boolean;
  /** 为组件设置浅色主题 */
  light?: Boolean;
  /** 设定组件的最大宽度 */
  maxWidth?: Number;
  /** 设定组件的最大高度 */
  maxHeight?: Number;
  /** 使用persistent属性并在对话框之外点击时，禁用弹跳效果 */
  noClickAnimation?: Boolean;
  /** 设置动画原点，也就是CSS的transform-origin */
  origin?: String;
  /** 隐藏遮罩层的显示 */
  hideOverlay?: Boolean;
  /** 设置遮罩层的颜色 */
  overlayColor?: String;
  /** 设置遮罩层的透明度 */
  overlayOpacity?: Number;
  /** 在对话框之外点击时不会关闭对话框 */
  persistent?: Boolean;
  /** 设置组件动画，参见 https://vuetifyjs.com/zh-Hans/styles/transitions/ */
  transition?: String;
  /** 对话框的标题 */
  title?: String;
  /** 对话框的内容 */
  content: String;
  /** 内容是否使用原始HTML */
  rawHtml?: Boolean;
  /** 对话框的按钮，从左往右排序 */
  buttons?: Array<VuetifyDialogButton>;
  /** 按钮是否垂直排列 */
  stackedButtons?: Boolean;
  /** 对话框显示后执行的回调函数 */
  onOpen?: Function;
  /** 对话框关闭后执行的回调函数 */
  onClose?: Function;
  /** 在对话框中使用文本框的配置 */
  prompt?: VuetifyDialogPromptConfig;

  /** 确认按钮的文本，仅在使用别名创建对话框时有效 */
  confirmText?: String;
  /** 取消按钮的文本，仅在使用别名创建对话框时有效 */
  cancelText?: String;
}

interface VuetifyDialogButton {
  /** 按钮的文本 */
  text: String;
  /** 按钮的文本颜色 */
  color?: String;
  /** 点击按钮后执行的回调函数，参数是文本框的值，执行后（如果有设定的话）再关闭对话框并执行onClose */
  onClick?: Function;
}

interface VuetifyDialogPromptConfig {
  /** 是否显示文本框 */
  enable?: Boolean;
  /** 文本框上方的标签 */
  label?: String;
  /** 文本框下方的提示 */
  hint?: String;
  /** 文本框的行数，超过1就使用v-textarea而不是v-text-field */
  rows?: Number;
  /** 是否使用type="password"，使用v-textarea时无效 */
  password?: Boolean;
  /** 最大输入字符数量，请在rules中添加函数用于检验 */
  maxLength?: Number;
  /** 文本框的初始值 */
  value?: String;
  /** 文本框的占位符 */
  placeholder?: String;
  /** 文本框的验证函数，参数是文本框的值，返回true/false或错误消息 */
  rules?: Array<Function>;
}

interface VuetifyDialog {
  /**
   * 打开一个对话框
   * @param config 配置，包含v-dialog的部分配置
   */
  (config: VuetifyDialogConfig): void;
  /**
   * 打开一个警告框
   * @param content 对话框的内容
   * @param title 对话框的标题
   * @param onConfirm 点击确认按钮的回调函数
   * @param config 其他的配置
   */
  alert(
    content: String,
    title?: String,
    onConfirm?: Function | null,
    config?: VuetifyDialogConfig,
  ): void;
  /**
   * 打开一个确认框
   * @param content 对话框的内容
   * @param title 对话框的标题
   * @param onConfirm 点击确认按钮的回调函数
   * @param onCancel 点击取消按钮的回调函数
   * @param config 其他的配置
   */
  confirm(
    content: String,
    title?: String,
    onConfirm?: Function | null,
    onCancel?: Function | null,
    config?: VuetifyDialogConfig,
  ): void;
  /**
   * 打开一个输入框
   * @param label 文本框的标签
   * @param title 对话框的标题
   * @param onConfirm 点击确认按钮的回调函数
   * @param onCancel 点击取消按钮的回调函数
   * @param config 其他的配置，prompt部分也写在这里，而不要单独写一个prompt:{...}
   */
  prompt(
    label: String,
    title?: String,
    onConfirm?: Function | null,
    onCancel?: Function | null,
    config?: VuetifyDialogConfig | VuetifyDialogPromptConfig,
  ): void;

  /** 对常用对话框使用Promise进行封装，resolve和reject分别对应“确定”和“取消” */
  promises: {
    /**
     * 打开一个警告框，返回Promise
     * @param content 对话框的内容
     * @param title 对话框的标题
     * @param config 其他的配置
     */
    alert(
      content: String,
      title?: String,
      config?: VuetifyDialogConfig,
    ): Promise<void>;
    /**
     * 打开一个确认框，返回Promise
     * @param content 对话框的内容
     * @param title 对话框的标题
     * @param config 其他的配置
     */
    confirm(
      content: String,
      title?: String,
      config?: VuetifyDialogConfig,
    ): Promise<void>;
    /**
     * 打开一个输入框，返回Promise
     * @param label 文本框的标签
     * @param title 对话框的标题
     * @param config 其他的配置，prompt部分也写在这里，而不要单独写一个prompt:{...}
     */
    prompt(
      label: String,
      title?: String,
      config?: VuetifyDialogConfig | VuetifyDialogPromptConfig,
    ): Promise<String>;
  };
  /**
   * 清空对话框的队列
   */
  clearQueue(): void;
}

declare module 'vue/types/vue' {
  interface Vue {
    $dialog: VuetifyDialog;
  }
}
