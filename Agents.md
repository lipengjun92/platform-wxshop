# platform AGENTS.md

## 1. 目标

本文件供后续开发型 Agent 在本仓库内工作时使用。目标不是介绍项目，而是降低误判技术栈、误改模块、误用验证方式、误报“已验证”的概率。

默认原则：

- 始终使用中文简体回复。
- 先查真实代码、真实配置、真实运行路径，再下结论。
- 只做最小必要改动，不顺手重构老代码。
- 每一处改动都要能追溯到当前任务本身。
- 未验证的内容必须明确写“未验证”，不要把推断说成事实。

## 2. 仓库结构与职责边界

这是一个多模块仓库，不要按“单体应用”理解。

- `platform-admin`：后台管理接口服务。
- `platform-api`：商城 / 小程序 API 服务。
- `platform-biz`：核心业务逻辑、Service、DAO、MyBatis XML mapper。
- `platform-common`：公共配置、工具、缓存与通用能力。
- `platform-admin-ui`：后台管理前端，技术栈为 Vue 2 + Element UI + webpack。
- `wx-mall`：原生微信小程序。
- `uni-mall`：uni-app 商城前端。
- `_sql`：数据库初始化脚本。

排查和改动时，优先按职责归位：

- 后台菜单、后台登录、后台接口、权限问题，先看 `platform-admin`。
- 商城接口、购物车、订单、支付、用户侧登录，先看 `platform-api`。
- SQL、分页、条件查询、字段映射、联表查询，先看 `platform-biz` 下 DAO 与 XML mapper。
- Redis、公共工具、共用配置，先看 `platform-common`。
- 管理台页面、表单、列表、请求封装，先看 `platform-admin-ui`。
- 小程序问题先确认是 `wx-mall` 还是 `uni-mall`，不要两边混改。

## 3. 技术栈现实

后续工作必须以当前仓库代码为准，不要按老 README、网上教程或默认新栈习惯猜测。

- 后端基线：Spring Boot `2.7.15`
- JDK 基线：Java `21`
- 持久层：MyBatis-Plus `3.5.3` + MyBatis XML mapper
- Web 容器：Undertow
- 权限：Shiro
- 缓存：Redis
- 接口文档：Springdoc + Knife4j
- 管理台前端：Vue `2.6.x` + Element UI `2.15.x` + webpack `4`
- 小程序：`wx-mall` 为原生微信小程序，`uni-mall` 为 uni-app

明确禁止的默认假设：

- 不要把 `platform-admin-ui` 按 Vue 3 / Vite / Composition API 项目处理。
- 不要默认可以直接引入 Pinia、Vue Router 4、`<script setup>` 等新栈写法。
- 不要把 `platform-biz` 当成纯“公共模块”；它承载大量真实业务逻辑和 SQL。

## 4. 本地启动与联调基线

### 4.1 后端服务

按当前配置文件，默认本地联调基线如下：

- `platform-admin`
  - 端口：`8080`
  - context-path：`/platform-framework`
  - 启动类：`PlatformAdminApplication`
- `platform-api`
  - 端口：`8081`
  - context-path：`/platform-framework-api`
  - 启动类：`PlatformApiApplication`

相关配置位于各模块：

- `platform-admin/src/main/resources/application.yml`
- `platform-admin/src/main/resources/application-*.yml`
- `platform-api/src/main/resources/application.yml`
- `platform-api/src/main/resources/application-*.yml`

环境通过 Maven profile 切换：

- `dev`
- `test`
- `prod`

排查启动问题时，优先核对：

- 当前激活 profile
- 数据源配置
- Redis 配置
- 证书与第三方配置
- 端口占用
- context-path 是否与前端联调地址一致

### 4.2 管理台前端

`platform-admin-ui` 不是 Vite 项目，使用 webpack-dev-server。

- 启动命令：`npm run dev`
- 本地默认开发地址：`http://localhost:8000`
- 请求封装在 `platform-admin-ui/src/utils/httpRequest.js`
- 开发态 `baseUrl` 当前来自 `platform-admin-ui/static/config/index.js`
- 默认值当前指向：`http://localhost:8888/platform-framework`

因此，若管理台页面请求失败，不要只看浏览器报错。先核对：

- 前端实际读到的 `window.SITE_CONFIG.baseUrl`
- 是否配置了代理
- 后台服务真实端口和 context-path
- 是否请求到了错误服务，例如把后台请求错打到 `platform-api`

### 4.3 小程序前端

`wx-mall` 当前本地 API 基址在 `wx-mall/config/api.js`，默认写的是：

- `http://localhost:8081/platform-framework-api/app/`

因此，小程序联调异常时，先确认：

- `platform-api` 是否已启动
- 端口是否仍是 `8081`
- context-path 是否仍是 `/platform-framework-api`
- 本地开发工具里是否用了旧域名或旧配置

`uni-mall` 是独立前端实现，处理问题前先确认任务目标是否真的是它，不要把 `wx-mall` 的修法直接套过去。

## 5. 配置与外部依赖约束

本仓库依赖多类本地或第三方配置，处理前不要擅自改值：

- MySQL
- Redis
- 微信公众号 / 小程序配置
- 微信支付配置
- 支付证书
- 支付宝配置
- 邮件配置
- 对象存储配置

约束：

- 没有充分证据，不要修改支付、证书、微信 appid / secret 等敏感配置。
- 如果问题看起来像“代码没生效”，先排查是否是本地配置、环境 profile、旧服务实例或前端缓存导致。
- 如果需要改配置，优先局部、最小化修改，并在结果中明确说明影响范围。

## 6. 查询、SQL 与 MyBatis 约束

这个仓库的数据问题大多落在 XML mapper，不要从 controller/service 开始盲猜。

处理 SQL、列表、搜索、分页、字段缺失时，优先路径是：

1. 找到对应 controller / service 的 DAO 调用点
2. 找到对应 DAO 接口
3. 打开对应 XML mapper
4. 核对查询条件、分页参数、返回字段、结果映射
5. 再决定是否需要改 service / VO / 前端消费代码

重点约束：

- 对 `LIKE` 查询，优先使用 `CONCAT('%', #{...}, '%')` 风格，不要继续拼接 `${}`。
- 改 XML mapper 时，要同步检查 DAO 方法签名、实体/VO 字段、调用方是否一致。
- 不要只改前端显示字段而不改 SQL 映射；也不要只改 SQL 而忘记前端消费点。

## 7. 验证规则

### 7.1 不要把“跑过命令”当成“验证通过”

本仓库后续必须明确区分：

- 编译通过
- 测试真实执行
- 本地联调通过
- 仅静态审查，未运行验证

### 7.2 测试存在外部副作用风险

当前已知测试包含真实外部副作用风险，例如：

- 发邮件
- 企业付款
- 退款
- 生成小程序码
- 同步微信用户

因此约束如下：

- 不要默认执行全量 `mvn test`。
- 执行测试前先阅读目标测试代码，确认是否会访问真实外部系统。
- 若测试会触发外部副作用，优先说明风险，再选择更安全的验证方式。
- 如果只做了代码审查或编译验证，必须明确写出“未做运行态验证”。

### 7.3 推荐验证思路

根据任务类型选择最小且可信的验证方式：

- 改 Java 代码但无安全测试入口：优先做定向编译或定向测试，不跑全量。
- 改 XML mapper：优先做针对性查询路径验证，不要直接跑所有测试。
- 改管理台页面：优先启动前端并核对实际请求、路由和页面行为。
- 改小程序：优先核对调用接口、配置地址、开发者工具实际行为。

## 8. 常见任务的处理模式

### 8.1 修接口 / 字段问题

不要只改一层。至少核对：

- controller
- service
- DAO
- XML mapper
- 返回 VO / entity
- 前端消费点

### 8.2 修后台列表 / 搜索 / 分页问题

优先查：

- `platform-admin-ui` 页面请求参数
- 后台 controller 入参
- service 分页逻辑
- XML mapper 条件拼装

### 8.3 修登录 / 权限问题

优先查：

- 登录接口
- token 传递
- Shiro 过滤 / 鉴权
- 前端请求拦截器
- 前端 token 清理和跳转逻辑

### 8.4 修联调失败 / 404 / 跨服务请求错位

优先查：

- 实际请求 URL
- 服务端口
- context-path
- 前端 baseUrl
- 是否调用错了 `platform-admin` / `platform-api`

### 8.5 修小程序问题

先确认目标端：

- `wx-mall`
- `uni-mall`

再确认问题层次：

- 配置问题
- API 地址问题
- 接口返回问题
- 页面逻辑问题

## 9. 改动原则

- 只改与当前任务直接相关的文件。
- 不顺手升级依赖、不顺手重构、不顺手统一风格。
- 匹配现有代码风格，即使风格较旧也不要擅自改栈。
- 如果改动导致你自己引入的 import、变量、方法变成未使用，要一起清理。
- 发现无关坏味道可以提示，但不要顺手删除。

## 10. 输出要求

每次完成任务后，回复中应尽量明确这四类信息：

- 改了什么
- 为什么改这里
- 做了什么验证
- 哪些地方没有验证、剩余什么风险

如果问题涉及运行态、联调、数据库、缓存或第三方配置，还应明确说明：

- 结论是来自代码、配置，还是来自实际运行验证
- 是否可能受到当前本地环境差异影响

## 11. 禁止事项

- 不要用现代前端默认习惯重写 `platform-admin-ui`。
- 不要把未验证说成“已修复”或“已通过”。
- 不要在未阅读测试内容前直接跑全量测试。
- 不要未经确认擅自修改支付、证书、微信配置等敏感项。
- 不要把 `platform-admin` 和 `platform-api` 的职责混为一谈。
- 不要把 `wx-mall` 和 `uni-mall` 混成同一前端实现。

## 12. 快速检查清单

开始动手前，先快速自检：

1. 我是否先确认了要改的是哪个模块？
2. 我是否确认了相关请求到底落到哪个服务？
3. 我是否看到了真实 XML mapper / 配置 / 前端请求代码？
4. 我的改动是否最小且能直接对应任务？
5. 我的验证方式是否安全、可信，且没有夸大？

