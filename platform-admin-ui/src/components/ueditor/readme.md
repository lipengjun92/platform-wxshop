# ueditor

<img src="https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/demo.gif" width="400" />

## Advanced

1. 如何获取 `ueditor` 实例？

    ```html
    <ueditor @ready="ready"></ueditor>

    ...

    methods: {
      ready (editorInstance) {
        console.log(`编辑器实例${editorInstance.key}: `, editorInstance)
      }
    }
    ```

2. 设置是否在组件的 `beforeDestroy` 钩子里销毁 `UEditor` 实例

    ```html
    <ueditor :destroy="true"></ueditor>
    ```
3. 选取 `v-model` 的实现方式。双向绑定的实现依赖对编辑器内容变化的监听，由于监听方式的不同，会带来监听效果的差异性，你可以自行选择，但建议使用开箱即用的默认值。

    ```html
    <ueditor mode="listener"></ueditor>
    ```
   可选值：`observer`，`listener`

   默认值：`observer`

   参数说明：
1. `observer`
   模式借助 [MutationObserver API](https://developer.mozilla.org/zh-CN/docs/Web/API/MutationObserver)。优点在于监听的准确性，缺点在于它会带来一点额外的性能开销。
   你可以通过 `observerDebounceTime` 属性设置触发间隔，还可以通过 `observerOptions`
   属性有选择的设置 [MutationObserver](https://developer.mozilla.org/en-US/docs/Web/API/MutationObserverInit)
   的监听行为。该 API 只兼容到 IE11+，但 `ueditor` 会在不支持的浏览器中自动启用 `listener` 模式。

    ```html
    <ueditor
      mode="observer"
      :observerDebounceTime="100"
      :observerOptions="{ attributes: true, characterData: true, childList: true, subtree: true }"
      >
    </ueditor>
    ```

2. `listener` 模式借助 UEditor 的 [contentChange 事件](https://ueditor.baidu.com/doc/#UE.Editor:contentChange)，优点在于依赖官方提供的事件
   API，无需额外的性能消耗，兼容性更好，但缺点在于监听的准确性并不高。

4. 如何进行二次开发（添加自定义按钮、弹窗等）？

   本组件提供了 `beforeInit` 钩子，它会在 `UEditor` 的 scripts 加载完毕之后、编辑器初始化之前触发，你可以在此时机，通过操作 window.UE 对象，来进行诸如添加自定义按钮、弹窗等的二次开发。
   `beforeInit` 的触发函数以 编辑器 id 和 配置参数 作为入参。如果有二次开发的需求，你可以参考[官方 API](https://ueditor.baidu.com/doc/)。

   <details>
     <summary>自定义按钮 Demo</summary>

   ```html
   <ueditor v-model="msg" @beforeInit="addCustomButtom"></ueditor>

     ...

   addCustomButtom (editorId) {
     window.UE.registerUI('test-button', function (editor, uiName) {
       // 注册按钮执行时的 command 命令，使用命令默认就会带有回退操作
       editor.registerCommand(uiName, {
         execCommand: function () {
           editor.execCommand('inserthtml', `<span>这是一段由自定义按钮添加的文字</span>`)
         }
       })

       // 创建一个 button
       var btn = new window.UE.ui.Button({
         // 按钮的名字
         name: uiName,
         // 提示
         title: '鼠标悬停时的提示文字',
         // 需要添加的额外样式，可指定 icon 图标，图标路径参考常见问题 2
         cssRules: "background-image: url('/test-button.png') !important;background-size: cover;",
         // 点击时执行的命令
         onclick: function () {
           // 这里可以不用执行命令，做你自己的操作也可
           editor.execCommand(uiName)
         }
       })

       // 当点到编辑内容上时，按钮要做的状态反射
       editor.addListener('selectionchange', function () {
         var state = editor.queryCommandState(uiName)
         if (state === -1) {
           btn.setDisabled(true)
           btn.setChecked(false)
         } else {
           btn.setDisabled(false)
           btn.setChecked(state)
         }
       })

       // 因为你是添加 button，所以需要返回这个 button
       return btn
     }, 0 /* 指定添加到工具栏上的哪个位置，默认时追加到最后 */, editorId /* 指定这个 UI 是哪个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮 */)
   }
   ```
   </details>

   <details>
     <summary>自定义弹窗 Demo</summary>

   ```html
   <ueditor v-model="msg" @beforeInit="addCustomDialog"></ueditor>
   ```

   ```js
   addCustomDialog (editorId) {
     window.UE.registerUI('test-dialog', function (editor, uiName) {
       // 创建 dialog
       var dialog = new window.UE.ui.Dialog({
         // 指定弹出层中页面的路径，这里只能支持页面，路径参考常见问题 2
         iframeUrl: '/customizeDialogPage.html',
         // 需要指定当前的编辑器实例
         editor: editor,
         // 指定 dialog 的名字
         name: uiName,
         // dialog 的标题
         title: '这是一个自定义的 Dialog 浮层',
         // 指定 dialog 的外围样式
         cssRules: 'width:600px;height:300px;',
         // 如果给出了 buttons 就代表 dialog 有确定和取消
         buttons: [
           {
             className: 'edui-okbutton',
             label: '确定',
             onclick: function () {
               dialog.close(true)
             }
           },
           {
             className: 'edui-cancelbutton',
             label: '取消',
             onclick: function () {
               dialog.close(false)
             }
           }
         ]
       })

       // 参考上面的自定义按钮
       var btn = new window.UE.ui.Button({
         name: 'dialog-button',
         title: '鼠标悬停时的提示文字',
         cssRules: `background-image: url('/test-dialog.png') !important;background-size: cover;`,
         onclick: function () {
           // 渲染dialog
           dialog.render()
           dialog.open()
         }
       })

       return btn
     }, 0 /* 指定添加到工具栏上的那个位置，默认时追加到最后 */, editorId /* 指定这个UI是哪个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮 */)
   }
   ```

   弹出层中的 HTML 页面 `customizeDialogPage.html`

   ```html
   <!DOCTYPE html>
   <html>

   <head>
     <meta charset="UTF-8">
     <title>Title</title>
     <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
     <meta name="renderer" content="webkit">
     <!--页面中一定要引入internal.js为了能直接使用当前打开dialog的实例变量-->
     <!--internal.js默认是放到 UEditor/dialogs 目录下的-->
     <script type="text/javascript" src="./ueditor/dialogs/internal.js"></script>
   </head>

   <body>
     <h1>hello ueditor</h1>
     <script>
       //可以直接使用以下全局变量
       //当前打开dialog的实例变量
       console.log('editor: ' + editor);
       //一些常用工具
       console.log('domUtils: ' + domUtils);
       console.log('utils: ' + utils);
       console.log('browser: ' + browser);
       dialog.onok = function() {
         editor.execCommand('inserthtml', '<span>我点击了确定</span>');
       };
       dialog.oncancel = function() {
         editor.execCommand('inserthtml', '<span>我点击了取消</span>');
       };
     </script>
   </body>

   </html>
   ```

   </details>

## Features

1. `v-model` 双向数据绑定！你不需要考虑实例化，也不需要考虑何时 `getContent`，何时`setContent`，简单到像使用 `input` 框一样！

2. 完全遵从官方 `API`，所有的配置参数和实例方法与官方完全一致。通过给 `ueditor` 组件的 `config` 属性传递一个对象，你就可以得到一个完全独立配置的 `UEditor` 编辑器。 通过监听 `ready`
   事件你就可以得到初始化后的 `UEditor` 实例并执行实例上的各种方法。

3. 自动添加依赖文件。你不需要自己在 `index.html` 或 `main.js` 里引入 `UEditor` 的 JS 文件。更重要的是即使你在一个页面里同时使用多个 `ueditor` 组件，它所依赖的 JS 文件也只会加载一次。
   这么做的原因在于你不需要当用户一打开项目就先加载大量 `UEditor` 相关的资源，所有的资源文件只会在 `ueditor` 组件第一次被激活时才加载。当然，如果你在 `index.html` 或 `main.js`
   里引入了相关资源，
   `ueditor` 也会准确判断，你不用担心它会重复加载。

4. 每个 `ueditor` 组件是完全独立的。你甚至可以在上面使用 `v-for` 指令一次渲染 99个 兔斯基（不要忘记添加 `key` 值）。
