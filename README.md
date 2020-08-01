# 注意
Entity里不是缺少get、set方法，Eclipse、IDEA请先安装lombok插件

# 使用须知
## ✅允许
- 个人学习使用
- 允许用于学习、毕设等
- 允许进行商业使用，请自觉遵守使用协议，如需要商业使用推荐购买商业版（进群联系群主）
- 请遵守 Apache License2.0 协议，再次开源请注明出处
- 推荐Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持

**如何交流、反馈、参与贡献？** 
- 官方社区：http://fly2you.cn
- gitee仓库：https://gitee.com/fuyang_lipengjun/platform
- github仓库：https://github.com/lipengjun92/platform-wxshop
* 官方QQ群：
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=HNLRmaIdvnj2e_TGkMspORvIn-AHNZCb&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ①群" title="微同科技 ①群"></a>
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=4i3Z9xgp7SlPnk_X1v0TWToSOoT_gJMz&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ②群" title="微同科技 ②群"></a>
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=hQLMx7vYLfP_C-d2-yP_udx1yciJXfHC&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ③群" title="微同科技 ③群"></a>

- 技术讨论、二次开发等咨询、问题和建议，请移步到官方社区，我会在第一时间进行解答和回复！
- 微信扫码并关注公众号，获得项目最新动态及更新提醒<br>
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/qr.jpg "微信公众号")

### 开发计划
* 1 使用uniapp重构小程序端所有页面
* 2 修复所以已知bug，使用遇到bug请给我们提交[issues](https://gitee.com/fuyang_lipengjun/platform/issues)
* 3 后台使用SpringBoot重构
* 4 适配H5、IOS、Android端
* 5 接入支付宝支付
* 6 出配套视频教程

### 微同商城商业版
##### 小程序
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/gh_9f71054867af_860.jpg "uniapp商城")

- [H5](https://fly2you.cn/h5)
- [IOS](https://testflight.apple.com/join/0tLEEwkS)
- [Android](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/wtkj.apk)

[商业版与开源版差异](http://fly2you.cn/business/index)

# 微信小程序商城（Java版）

## 获得荣誉
### GVP
![](
https://platform-wxmall.oss-cn-beijing.aliyuncs.com/GVP.jpg "GVP")

## 新手必看启动教程
- [https://www.bilibili.com/video/av66149752](https://www.bilibili.com/video/av66149752)

### 微同商城开源版体验：
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/gh_a7a467438863_344.jpg "微同商城开源版")

* git：[https://gitee.com/fuyang_lipengjun/platform](https://gitee.com/fuyang_lipengjun/platform)
* 代码生成工具IDEA插件
    * git：[https://gitee.com/fuyang_lipengjun/platform-gen](https://gitee.com/fuyang_lipengjun/platform-gen)

## 技术选型
* 1 后端使用技术
    * 1.1 springframework4.3.7.RELEASE
    * 1.2 mybatis3.1.0、MyBatis-Plus 3.1.0
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
    * 1.14 weixin-java-mp3.2.0
    * 1.15 MybatisPlus3.1.0
    * 1.16 lombok
        
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
platform
|--platform-admin 后台管理
|--platform-api 微信小程序商城api接口
|--platform-common 公共模块
|--platform-framework 系统WEB合并，请打包发布此项目
|--platform-gen 代码生成
|--platform-mp 微信公众号模块
|--platform-schedule 定时任务
|--platform-shop 商城后台管理
|--uni-color-ui uni-mall商城参考项目
|--uni-mall 移动端商城
|--wx-mall 微信小程序商城
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
        
* 七：短信服务平台  
    * a 配置短信平台账户信息
    * b 向外提供发送短信接口：
    ```
    http://域名:端口/api/sendSms?mobile=13000000000,15209831990&content=发送的短信内容  
    安全起见，需配置有效IP地址。platform.properties -> sms.validIp
    ```

## 安装教程

* 配置环境（推荐jdk1.8、maven3.3、tomcat8、mysql5.7、redis4.0.1）
* 创建数据库
* 依次初始化sql脚本 
    * /_sql/platform.sql
    * /_sql/sys_region.sql
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
    * mp.appId
    * mp.secret
    * mp.token
    * mp.aesKey
* 修改配置文件 /platform-admin/src/main/resources/j2cache.properties
    * redis.hosts
    * redis.password
* 启动redis服务
* 启动后台项目（参照<a href="#doc">开发文档</a>）
* 打开微信开发者工具
* 导入 /wx-mall填写appId
* 修改 /wx-mall/config/api.js里API_BASE_URL的值
* 使用eclipse启动项目后默认访问路径
    * [http://localhost:8080/platform-framework](http://localhost:8080/platform-framework)
* 使用idea启动项目后默认访问路径
    * [http://localhost:8080](http://localhost:8080)

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

### 开发文档目录
<a name="doc" href="http://fly2you.cn/guide/index" target="_blank">![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/catalog.png "开发文档目录")</a>

