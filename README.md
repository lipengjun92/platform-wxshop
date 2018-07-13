# 微信小程序商城（Java版）

[![Fork me on Gitee](https://gitee.com/fuyang_lipengjun/platform/widgets/widget_3.svg?color=C71D24)](https://gitee.com/fuyang_lipengjun/platform)

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

## 官方首页
* [http://fly2you.cn](http://fly2you.cn)

* 如何交流、反馈、参与贡献？
    * 官方QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=75689ba2797dd88a208446088b029fbdeba87a29315ff2a021a6731f22ef5052"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="大平台系统开发" title="大平台系统开发"></a>
    * 演示地址：[http://fly2you.cn/platform](http://fly2you.cn/platform)  `账号密码：admin/admin`
    * git：[https://gitee.com/fuyang_lipengjun/platform](https://gitee.com/fuyang_lipengjun/platform)
    * 基础架构版
        * git：[https://gitee.com/fuyang_lipengjun/platform-framework](https://gitee.com/fuyang_lipengjun/platform-framework)
        * 演示地址：[http://fly2you.cn/platform-framework](http://fly2you.cn/platform-framework)
    * 如需获取项目最新源码，请Watch、Star项目，同时也是对项目最好的支持
    

## 安装教程

* 配置环境（推荐jdk1.8、maven3.3、tomcat8、mysql5.7）
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
* 启动后台项目（参照启动手册）
* 打开微信开发者工具
* 导入 /wx-mall填写appId
* 修改 /wx-mall/config/app.js里NewApiRootUrl的值
* 使用eclipse启动项目后默认访问路径
    * [http://localhost:8080/platform-framework](http://localhost:8080/platform-framework)
* 使用idea启动项目后默认访问路径
    * [http://localhost:8080](http://localhost:8080)

## 生产环境打包
    platform-wechat-mall>mvn package -P prod

***
### 关注微信公众号，第一时间获取项目最新动向，即将推出视频教程
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/qr.jpg "微信公众号")

## 页面展示
### 登录页面
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/14570217019439.png "登录")
### 首页
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/1457352514f28b.png "首页")
### 发送短信
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/145757629f5361.png "发送短信")
### 捐赠
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/145823816a2fe9.png "捐赠")
### 小程序首页
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/102947584b7f1f.png "小程序首页")
### 专题
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103157121abad8.png "专题")
### 分类
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103238930383af.png "分类")
### 购物车
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103319500ba517.png "购物车")
### 登录授权
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103355172c1903.png "登录授权")
### 优惠券
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/1034519508712e.png "优惠券")
### 小程序并联手机
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/1030474135e131.png "并联手机")
### VUE页面
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/14592343205eec.png "VUE页面")

***
## 小程序客户案例

### 美平超市
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/145949515b6f7c.png "美平超市")
### 便利主义超市
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/150000826e2864.png "便利主义超市")
### 汽车人E车宝
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/15001446746217.png "汽车人E车宝")
### 海数据在线
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103939911f7e76.png "海数据在线")
### T客定制
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/2018070801.jpg "T客定制")


***
## ◆免责条款:
**_感谢您的支持，此系统供个人学习、研究之用。如因使用本系统引起的相关法律法规责任，由使用者自行负责。_**