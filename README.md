#### [腾讯云双十一秒杀](https://cloud.tencent.com/redirect.php?redirect=10172&cps_key=30280f92fc381dfc9e1d9e0e23d25a18&from=activity)：
活动时间（10.29-11.30）：每日5场秒杀，分别于 9:00 / 11:00 / 14:00 / 16:00 / 19:00 开抢。热门云产品1折起，送13000元续费/升级大礼包
[![腾讯云双十一](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/tx11.jpg)](https://cloud.tencent.com/redirect.php?redirect=10172&cps_key=30280f92fc381dfc9e1d9e0e23d25a18&from=activity)

# 微信小程序商城（Java版）

## 官方首页
* [演示地址](http://fly2you.cn)
* [最新开发文档](http://fly2you.cn/guide/index)

* 官方QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=2d02d83d8be4c2cb6848bbae1df1037ba2acddecd2a1aa8cef7b3e4ab4ff75aa"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="platform-wechat-mall ①群" title="platform-wechat-mall ①群"></a><a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=990a15d445ef791dba99d22d9772c06ac7894ffa6ac639b1eec530554c432583"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="platform-wechat-mall ②群" title="platform-wechat-mall ②群"></a>
* git：[https://gitee.com/fuyang_lipengjun/platform](https://gitee.com/fuyang_lipengjun/platform)
* 基础架构版
    * git：[https://gitee.com/fuyang_lipengjun/platform-framework](https://gitee.com/fuyang_lipengjun/platform-framework)
* 代码生成工具IDEA插件
    * git：[https://gitee.com/fuyang_lipengjun/platform-gen](https://gitee.com/fuyang_lipengjun/platform-gen)
     
## 技术选型
* 1 后端使用技术
    * 1.1 springframework4.3.7.RELEASE
    * 1.2 mybatis3.4.1
    * 1.3 shiro1.3.2
    * 1.4 servlet3.1.0
    * 1.5 druid1.0.28
    * 1.6 slf4j1.7.19
    * 1.7 fastjson1.2.30
    * 1.8 poi3.15
    * 1.9 velocity1.7
    * 1.10 quartz2.2.3
    * 1.11 mysql5.1.39
    * 1.12 swagger2.4
    * 1.13 j2cache2.3.22-release
        
* 2 前端使用技术
    * 2.1 Vue2.5.1
    * 2.2 iview
    * 2.3 layer3.0.3
    * 2.4 jquery2.2.4
    * 2.5 bootstrap3.3.7
    * 2.6 jqgrid5.1.1
    * 2.7 ztree3.5.26
    * 2.8 froala_editor1.2.2

## 项目结构
~~~
platform-wechat-mall
|--platform-admin 后台管理
|--platform-api 微信小程序商城api接口
|--platform-common 公共模块
|--platform-framework 系统WEB合并
|--platform-gen 代码生成
|--platform-mp 微信公众号模块
|--platform-schedule 定时任务
|--platform-shop 商城后台管理
|--wx-mall 微信小程序商城
|--platform-vue 微信公众号商城（待开发）
~~~

## 实现功能

* 一：会员管理
    * a 会员管理
    * b 会员等级
    * c 收货地址管理
    * d 会员优惠劵
    * e 会员收藏
    * f 会员足迹
    * g 搜索历史
    * h 购物车

* 二：商城配置
    * a 区域配置
    * b 商品属性种类
    * c 品牌制造商
    * d 商品规格
    * e 订单管理
    * f 商品类型
    * g 渠道管理
    * h 商品问答
    * i 反馈
    * j 关键词

* 三：商品编辑
    * a 所有商品
    * b 用户评论
    * c 产品设置
    * d 商品规格
    * e 商品回收站

* 四：推广管理
    * a 广告列表
    * b 广告位置
    * c 优惠劵管理
    * d 专题管理
    * e 专题分类

* 五：订单管理
    * a 所有订单管理

* 六：系统管理
    * a 管理员列表
    * b 角色管理
    * c 菜单管理
    * d SQL监控
    * e 定时任务
    * f 参数管理
    * g 代码生成器
    * h 系统日志
    * i 文件上传
    * j 通用字典表
        
* 六：短信服务平台  
    * **需要短信验证码、短信通知、短信营销的客户进群私聊我**
    * a 配置短信平台账户信息
    * b 向外提供发送短信接口：
    ```
    http://域名:端口/api/sendSms?mobile=13000000000,15209831990&content=发送的短信内容  
    安全起见，需配置有效IP地址。platform.properties -> sms.validIp
    ```

## 安装教程

* 配置环境（推荐jdk1.8、maven3.3、tomcat8、mysql5.7、redis4.0.1）
* 创建数据库
* 初始化sql脚本 /doc/platform.sql
* 导入项目到IDE中
* 导入支付证书至/platform-shop/src/main/resources/cert/目录下（申请商户号、开通微信支付、下载支付证书）
* 修改配置文件 /platform-admin/src/main/resources/dev/platform.properties
    * jdbc.url
    * jdbc.username
    * jdbc.password
    * wx.appId
    * wx.secret
    * wx.mchId
    * wx.paySignKey
    * wx.notifyUrl
    * sms.validIp
* 修改配置文件 /platform-admin/src/main/resources/j2cache.properties
    * redis.hosts
    * redis.password
* 启动后台项目（参照<a href="#doc">开发文档</a>）
* 打开微信开发者工具
* 导入 /wx-mall填写appId
* 修改 /wx-mall/config/app.js里NewApiRootUrl的值
* 使用eclipse启动项目后默认访问路径
    * [http://localhost:8080/platform-framework](http://localhost:8080/platform-framework)
* 使用idea启动项目后默认访问路径
    * [http://localhost:8080](http://localhost:8080)

## 生产环境打包
    platform-wechat-mall>mvn package -P prod
    
## platform-vue启动
* npm install -g yarn
* yarn install
* yarn run dev
* 浏览器输入[http://127.0.0.1:8001](http://127.0.0.1:8001)

***
### 关注微信公众号，第一时间获取项目最新动向，即将推出视频教程
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/qr.jpg "微信公众号")

## 页面展示
### 登录页面
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/login.png "登录")
### 首页
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/index.png "首页")
### 发送短信
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/3.png "发送短信")
### 捐赠
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/4.png "捐赠")
### 小程序首页
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/5.png "小程序首页")
### 专题
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/6.png "专题")
### 分类
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/7.png "分类")
### 购物车
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/8.png "购物车")
### 登录授权
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/9.png "登录授权")
### 优惠券
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/10.png "优惠券")
### 小程序并联手机
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/11.png "并联手机")
### VUE页面
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/12.png "VUE页面")

***
## 小程序客户案例

### 便利主义超市
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/case/2.png "便利主义超市")
### 海数据在线
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/case/4.jpg "海数据在线")
### 美平超市
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/case/1.png "美平超市")

### <a name="doc">开发文档目录</a>
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/catalog.png "开发文档目录")