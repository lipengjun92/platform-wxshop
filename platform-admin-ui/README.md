## 技术栈

- [nodejs](http://nodejs.org/)
- [ES6](http://es6.ruanyifeng.com/)
- [vue-cli](https://github.com/vuejs/vue-cli)
- [vue](https://cn.vuejs.org/index.html)
- [vue-router](https://github.com/vuejs/vue-router)
- [vuex](https://github.com/vuejs/vuex)
- [axios](https://github.com/axios/axios)
- [vue-cookie](https://github.com/alfhen/vue-cookie)
- [element-ui](https://github.com/ElemeFE/element)
- [iconfont](http://www.iconfont.cn/)

## 目录结构
本项目已经通过vue-cli脚手架为你生产完整的开发框架（有根据业务需求做调整修改），下面是整个项目的目录结构。
```bash
├── build                      // 构建相关
├── config                     // 构建配置相关
├── src                        // 源代码
│   ├── assets                 // 静态资源
│   ├── components             // 全局公用组件
│   ├── element-ui             // element-ui组件配置
│   ├── element-ui-theme       // element-ui组件主题配置
│   ├── icons                  // 所有 svg icons
│   ├── router                 // 路由
│   ├── store                  // 全局 store管理
│   ├── utils                  // 全局公用方法
│   ├── views                  // view
│   ├── App.vue                // 入口组件
│   ├── main.js                // 入口
├── static                      // 第三方不打包资源
│   ├── config                 // 全局变量配置
│   ├── img                    // favicon图标
│   ├── plugins                // 插件
├── .babelrc                   // babel-loader 配置
├── eslintrc.js                // eslint 配置项
├── .gitignore                 // git 忽略项
├── index.html                 // html模板
└── package.json               // package.json
```

## 安装
```bash
# 安装依赖
npm install

# 启动服务
npm run dev
```

安装过程中，可能会出现安装过慢、报错等情况，请尝试以下方式：
```bash
npm install -g cnpm --registry=https://registry.npm.taobao.org

cnpm install

# 启动服务
npm run dev
```
启动完成后会自动打开浏览器访问 [http://localhost:8000]()。

# 主题定制

提供12套颜色主题，进行element-ui和整站主题切换。具体切换方法如下：

1. 修改[/src/element-ui-theme/index.js](https://github.com/daxiongYang/renren-fast-vue/blob/master/src/element-ui-theme/index.js)文件中```import './element-[#17b3a3]/index.css'```[]中括号中的值，值可以在同文件中```list```属性中取即可。**（注意：这里只是修改element-ui组件主题）**
2. 修改[/src/assets/scss/_variables.scss](https://github.com/daxiongYang/renren-fast-vue/blob/master/src/assets/scss/_variables.scss)文件中```$--color-primary: [#17b3a3];```[]中括号中的值，值与第一步值同步即可。**（注意：这里只是修改站点主题，不包括element-ui组件主题）**

主题定制具体实现方法是：
1. 先通过element-ui官方提供的[在线主题生成工具](https://elementui.github.io/theme-chalk-preview/#/zh-CN)，进行切换主题色，再下载解压文件（保留```fonts目录中文件和index.css即可```）放置```/src/element-ui-theme/```目录中，使用同目录中的```index.js```进行统一配置管理。
2. 再设置修改站点主题，使整站主题色统一一致。

# 生产打包部署
```
修改
/static/config/index-prod.js文件
window.SITE_CONFIG['baseUrl'] = 'http://你的域名/platform-admin' // 后台接口请求地址
//如果使用第三方文件存储，将dist/1907180922 上传至第三方文件存储，然后填写cdn地址
window.SITE_CONFIG['domain'] = '静态资源cdn地址';

# 构建生产环境
npm run build

```
# 部署Nginx配置参考
```
  location / {
        # 指向我们打包后上传的前端文件
        root /usr/local/nginx/dist;
        index index.html;
    }
    location /platform-admin {
        # 转发请求到后端
        proxy_pass                         http://你的域名:8890/platform-admin;
        proxy_set_header  Host             $host;
        proxy_set_header  X-Real-IP        $remote_addr;
        proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;
    }
    location /platform-api {
        # 转发请求到后端
        proxy_pass                         http://你的域名:8889/platform-api;
        proxy_set_header  Host             $host;
        proxy_set_header  X-Real-IP        $remote_addr;
        proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;
    }
```

#### 常用API
- [Vue](https://cn.vuejs.org/v2/api/)
- [element-ui](http://element-cn.eleme.io/#/zh-CN/component/installation)
- [echarts](https://www.echartsjs.com/api.html#echarts)
- [iconfont](https://www.iconfont.cn/search/index)
