# 微信小程序商城（Java + uniapp）

## 注意
Entity里提示报错不是缺少get、set方法，Eclipse、IDEA请先安装`lombok`插件，如果不了解`lombok`的话，请先学习下相关知识，比如可以阅读[此文章](https://mp.weixin.qq.com/s/cUc-bUcprycADfNepnSwZQ)；

## 新手必看启动教程
- [https://www.bilibili.com/video/av66149752](https://www.bilibili.com/video/av66149752)
## 使用Hbuilder启动微同商城小程序端教程
- [https://www.bilibili.com/video/BV1ni4y1M7CC](https://www.bilibili.com/video/BV1ni4y1M7CC)

### 微同商城开源版体验：
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/gh_a7a467438863_344.jpg "微同商城开源版")

* git：[https://gitee.com/fuyang_lipengjun/platform](https://gitee.com/fuyang_lipengjun/platform)
* 代码生成工具IDEA插件
  * git：[https://gitee.com/fuyang_lipengjun/platform-gen](https://gitee.com/fuyang_lipengjun/platform-gen)

## 官网
[https://fly2you.cn](https://fly2you.cn)
## 文档
[http://doc.fly2you.cn](http://doc.fly2you.cn)

<p align="center">
  <b>特别赞助</b>
</p>
<br/>
<table align="center" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
      <td align="center" valign="middle" colspan="3">
	      <a href="http://www.ccflow.org/?from=fuyang_lipengjun" target="_blank">
					<img src="https://platform-wxmall-1251990035.cos.ap-shanghai.myqcloud.com/ccflow.png">
				</a>
      </td>
    </tr>
  </tbody>
</table>

## 获得荣誉
### GVP
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/GVP.jpg "GVP")

## 重要信息
1. 项目合作洽谈，请联系客服微信（使用微信扫码添加好友，请注明来意）。
2. 如需购买 [商业版源码](https://fly2you.cn/mall.html) 请联系客服。<br>
![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/wx.png "微信")
3. 微信扫码并关注公众号回复“文档”，获取《微同开源商城启动部署手册.docx》<br>
  ![](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180708/qr.jpg "微信公众号")

## 使用须知
### ✅允许
- 个人学习使用
- 允许用于学习、毕设等
- 允许进行商业使用，请自觉遵守使用协议，如需要商业使用推荐购买[商业版源码](https://fly2you.cn/mall.html)
- 请遵守 Apache License2.0 协议，再次开源请注明出处
- 推荐Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持
- 希望大家多多支持原创作品

**如何交流、反馈、参与贡献？** 
- gitee仓库：https://gitee.com/fuyang_lipengjun/platform
- github仓库：https://github.com/lipengjun92/platform-wxshop
* 官方QQ群：
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=HNLRmaIdvnj2e_TGkMspORvIn-AHNZCb&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ①群" title="微同科技 ①群"></a>：66502035
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=4i3Z9xgp7SlPnk_X1v0TWToSOoT_gJMz&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ②群" title="微同科技 ②群"></a>：870579539
    * <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=hQLMx7vYLfP_C-d2-yP_udx1yciJXfHC&jump_from=webapi"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="微同科技 ③群" title="微同科技 ③群"></a>：151602347

## 开发计划
* 1 使用uniapp重构小程序端所有页面【100%】
* 2 修复所以已知bug，使用遇到bug请给我们提交[issues](https://gitee.com/fuyang_lipengjun/platform/issues)
* 3 后台使用SpringBoot重构
* 4 适配H5、IOS、Android端
* 5 接入支付宝支付
* 6 出配套视频教程

## 项目结构
~~~
platform
|--platform-admin 后台管理
|--platform-api 微信小程序商城api接口
|--platform-common 公共模块
|--platform-framework 打包发布此项目
|--platform-gen 代码生成
|--platform-mp 微信公众号模块
|--platform-schedule 定时任务
|--platform-shop 商城后台管理
|--uni-mall uniapp版商城
|--wx-mall 微信小程序原生商城
~~~

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
* 出现404问题的同学请检查设置的`Application context`

## 驰骋工作流引擎的安装
   1. 请参考: https://gitee.com/opencc/JFlow/wikis/pages/preview?sort_id=4199224&doc_id=31094


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

## Stargazers over time
[![Stargazers over time](https://whnb.wang/stars/fuyang_lipengjun/platform)](https://whnb.wang/fuyang_lipengjun/platform)

## contributors
[![contributors](https://whnb.wang/contributors/fuyang_lipengjun/platform)](https://whnb.wang/fuyang_lipengjun/platform)
 
