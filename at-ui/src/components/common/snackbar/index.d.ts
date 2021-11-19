import Vue from 'vue';

interface VuetifyToastConfig {
    /** 给组件应用position:absolute */
    absolute?: Boolean;
    /** 将组件向底部对齐 */
    bottom?: Boolean;
    /** 将组件向顶部对齐 */
    top?: Boolean;
    /** 将组件向左边对齐 */
    left?: Boolean;
    /** 将组件向右边对齐 */
    right?: Boolean;
    /** 将指定的颜色应用于控件 */
    color?: String;
    /** 使消息条具有更大的最低高度 */
    multiLine?: Boolean;
    /** 等待Snackbars自动隐藏的时间，如果为0表示永久开启 */
    timeout?: Number;
    /** 将消息条内容堆叠在按钮之上 */
    vertical?: Boolean;
    /** 按钮的文本，如果为空就不显示按钮 */
    buttonText?: String;
    /** 按钮的文本颜色 */
    buttonColor?: String;
    /** 点击Snackbar执行完回调函数后是否关闭Snackbar */
    closeOnClick?: Boolean;
    /** 点击按钮执行完回调函数后是否关闭Snackbar */
    closeOnButtonClick?: Boolean;
    /** 点击Snackbar执行的回调函数 */
    onClick?: Function;
    /** 点击按钮执行的回调函数 */
    onButtonClick?: Function;
    /** Snackbar显示后执行的回调函数 */
    onOpen?: Function;
    /** Snackbar关闭后执行的回调函数 */
    onClose?: Function;
}

interface VuetifyToast {
    /**
     * 使用Snackbar显示一条通知消息
     * @param text Snackbar的文本
     * @param config 配置，包含v-snackbar的部分配置
     */
    (text: String, config?: VuetifyToastConfig): void;
    /**
     * 使用Snackbar显示操作成功信息
     * @param text Snackbar的文本
     * @param config 配置，包含v-snackbar的部分配置
     */
    success(text: String, config?: VuetifyToastConfig): void;
    /**
     * 使用Snackbar显示一般信息
     * @param text Snackbar的文本
     * @param config 配置，包含v-snackbar的部分配置
     */
    info(text: String, config?: VuetifyToastConfig): void;
    /**
     * 使用Snackbar显示错误信息
     * @param text Snackbar的文本
     * @param config 配置，包含v-snackbar的部分配置
     */
    error(text: String, config?: VuetifyToastConfig): void;
    /**
     * 使用Snackbar显示警告信息
     * @param text Snackbar的文本
     * @param config 配置，包含v-snackbar的部分配置
     */
    warning(text: String, config?: VuetifyToastConfig): void;
    /**
     * 清空Snackbar的队列
     */
    clearQueue(): void;
}

declare module 'vue/types/vue' {
    interface Vue {
        $toast: VuetifyToast;
    }
}