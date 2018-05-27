    致力打造通用综合管理平台

    技术选型
    1、后端使用技术
        springframework4.3.7.RELEASE
        mybatis3.4.1
        shiro1.3.2
        servlet3.1.0
        druid1.0.28
        slf4j1.7.19
        fastjson1.2.30
        poi3.15
        velocity1.7
        alisms1.0
        quartz2.2.3
        mysql5.1.39
        swagger2.4
        
    前端使用技术
        Vue2.5.1
        iview
        layer3.0.3
        jquery2.2.4
        bootstrap3.3.7
        jqgrid5.1.1
        ztreev3.5.26
        froala_editor1.2.2
        iNotify

    platform-admin 
    后台管理

    platform-api 
    微信小程序商城api接口

    platform-common 
    公共模块

    platform-framework 
    系统WEB合并
    
    platform-gen 
    代码生成

    platform-schedule 
    定时任务

    platform-shiro 
    登陆权限相关
    
    platform-shop
    商城后台管理

    wx-mall 
    微信小程序商城

    platform-vue 
    H5商城

    实现功能

    一：会员管理
        会员管理
        会员等级
        收货地址管理
        会员优惠劵
        会员收藏
        会员足迹
        搜索历史
        购物车

    二：商城配置
        区域配置
        商品属性种类
        品牌制造商
        商品规格
        订单管理
        商品类型
        渠道管理
        商品问答
        反馈
        关键词

    三：商品编辑
        所有商品
        用户评论
        产品设置
        商品满减搭配
        商品规格
        商品回收站
        团购设置

    四：推广管理
        广告列表
        广告位置
        优惠劵管理
        专题管理
        专题分类

    五：系统管理
        管理员列表
        角色管理
        菜单管理
        SQL监控
        定时任务
        参数管理
        代码生成器
        系统日志
        文件上传
        通用字典表
        
    六：短信服务平台(需要短信验证码、短信通知、短信营销的客户进群私聊我)
            配置短信平台账户信息
		    向外提供发送短信接口：
		        http://域名:端口/api/sendSms?mobile=13000000000,15209831990&content=发送的短信内容
                安全起见，需配置有效IP地址。platform.properties -> sms.validIp
		


    注意
    若使用微信支付，请下载证书放入platform-shop/src/main/resources/cert/目录下
    apiclient_cert.p12
    apiclient_cert.pem
    apiclient_key.pem
    rootca.pem
    
    后台管理项目演示
    演示地址：http://47.100.0.48
    账号密码：admin/admin
    
    如何交流、反馈、参与贡献？
    官方QQ群：66502035
    git：https://gitee.com/fuyang_lipengjun/platform
    如需获取项目最新源码，请Watch、Star项目，同时也是对项目最好的支持
    

### 登录页面
![](http://image.meiping123.com/upload/20180413/1146147845f6e4.png "登录")
### 首页
![](http://image.meiping123.com/upload/20180413/1147025253e40d.png "首页")
### 发送短信
![](http://image.meiping123.com/upload/20180413/1147169100e836.png "发送短信")
### 捐赠
![](http://image.meiping123.com/upload/20180413/114740282ff88f.png "捐赠")
### 小程序首页
![](http://image.meiping123.com/upload/20180413/114828132c9d85.png "小程序首页")
### VUE页面
![](http://image.meiping123.com/upload/20180413/1148416407a795.png "VUE页面")

### 小程序客户案例
#### 美平超市
![](https://meiping123.oss-cn-shenzhen.aliyuncs.com/upload/20180527/22324382435684.jpg "美平超市")
#### 便利主义超市
![](https://meiping123.oss-cn-shenzhen.aliyuncs.com/upload/20180527/2231283403399b.jpg "便利主义超市")
