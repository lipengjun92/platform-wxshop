    微信小程序商城（Java版）

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
    微信公众号商城（待开发）

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
		

    后台管理项目演示
    演示地址：https://fly2you.cn/platform
    账号密码：admin/admin
    
    如何交流、反馈、参与贡献？
    官方QQ群：66502035
    博客：https://fly2you.cn/mblog
    git：https://gitee.com/fuyang_lipengjun/platform
    基础架构版
        git：https://gitee.com/fuyang_lipengjun/platform-framework
        演示地址：https://fly2you.cn/platform-framework
    如需获取项目最新源码，请Watch、Star项目，同时也是对项目最好的支持
    

#### 安装教程

1. 配置环境（推荐jdk1.8、maven3.3、tomcat8、mysql5.7）
2. 创建数据库
3. 初始化sql脚本 /doc/platform.sql
4. 导入项目到IDE中
5. 导入支付证书至/platform-shop/src/main/resources/cert/目录下（申请商户号、开通微信支付、下载支付证书）
6. 修改配置文件 /platform-admin/src/main/resources/dev/platform.properties

        jdbc.url
        jdbc.username
        jdbc.password
        小程序ID
        wx.appId
        小程序密钥
        wx.secret
        商户号
        wx.mchId
        支付签名
        wx.paySignKey
        支付回调地址
        wx.notifyUrl
        短信接口有效IP
        sms.validIp
7. 启动后台项目
8. 打开微信开发者工具
9. 导入 /wx-mall填写appId
10. 修改 /wx-mall/config/app.js里NewApiRootUrl的值
11.使用eclipse启动项目后默认访问路径

        http://localhost:8080/platform-framework
        
12.使用idea启动项目后默认访问路径

        http://localhost:8080/



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

### 小程序客户案例
#### 美平超市
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/145949515b6f7c.png "美平超市")
#### 便利主义超市
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/150000826e2864.png "便利主义超市")
#### 汽车人E车宝
![](http://p9kyr79ne.bkt.clouddn.com/1/20180531/15001446746217.png "汽车人E车宝")
#### 海数据在线
![](http://p9kyr79ne.bkt.clouddn.com/1/20180614/103939911f7e76.png "海数据在线")

### ◆免责条款:
感谢您的支持，此系统仅供个人学习、研究之用，请勿用于商业及非法用途。

如因使用本系统引起的相关法律法规责任，由使用者自行负责。