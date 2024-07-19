// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    'browser': true,
    'commonjs': true,
    'es6': true
  },
  globals: {
    vue: true
  },
  // https://github.com/standard/standard/blob/master/docs/RULES-en.md
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  // "off"或者0    //关闭规则关闭
  // "warn"或者1    //在打开的规则作为警告（不影响退出代码）
  // "error"或者2    //把规则作为一个错误（退出代码触发时为1）
  rules: {
    // 小括号里面要不要有空格
    'space-in-parens': [2, 'never'],
    // 函数定义时括号前面要不要有空格
    'space-before-function-paren': [2, 'always'],
    // switch语句最后必须有default
    'default-case': 2,
    // 逗号风格，换行时在行首还是行尾
    'comma-style': [2, 'last'],
    // 大括号风格
    'brace-style': [2, '1tbs'],
    // 一行结束后面不要有空格
    'no-trailing-spaces': 2,
    // 禁止混用tab和空格
    'no-mixed-spaces-and-tabs': [2, false],
    // 禁止在循环中使用函数（如果没有引用外部变量不形成闭包就可以）
    'no-loop-func': 2,
    // 禁止行内备注
    'no-inline-comments': 2,
    // 禁止switch穿透
    'no-fallthrough': 2,
    // 注释风格要不要有空格什么的
    'spaced-comment': 2,
    // 禁止修改const声明的变量
    'no-const-assign': 2,
    // 块语句中的内容不能为空
    'no-empty': 2,
    // 必须使用 if(){} 中的{}
    'curly': [2, 'all'],
    // 生成器函数*的前后空格
    'generator-star-spacing': 'off',
    // 缩进风格
    'indent': [2, 2, {'SwitchCase': 1}],
    // 换行风格
    'linebreak-style': [0, 'error', 'windows'],
    'no-tabs': 'off',
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
  }
}
