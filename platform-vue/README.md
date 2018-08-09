# platform-vue

> wechat-end h5 client powered by vuejs and vux

>部署时， 在当前根目录执行npm run build，将dist文件夹中的所有文件放置web服务器即可

>注意1：由于该应用采用history路由模式，有些服务器会出现无法访问的想象（可在/src/router/index.js中的model设置为hash即可，这样的URL并不优雅）

>注意2：应用采用history路由模式，对于常用的apache和nginx需要做额外的web服务器配置，详情请查看[vue-router](https://router.vuejs.org/zh-cn/essentials/history-mode.html)


## Build Setup

``` bash
# install yarn
platform-vue> npm install -g yarn

# install dependencies
platform-vue> yarn install

# serve with hot reload at localhost:8080
platform-vue> yarn run dev

# build for production with minification
platform-vue> yarn run build

# build for production and view the bundle analyzer report
platform-vue> yarn run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
