# 项目启动说明

把本文件或本文件的在线链接交给可以访问本地文件、执行终端命令的 AI，即可按以下步骤下载并启动本地环境。已下载项目时告诉 AI 项目所在路径；未下载时先执行下面的项目获取步骤。默认启动管理台、后台管理接口、商城接口、MySQL 和 Redis；微信小程序按最后一节单独处理。本文用于本地开发和体验，不作为公网生产部署方案。

## 0. 获取项目

如果用户尚未下载项目，先确认当前目录或用户指定目录适合存放源码，并检查 Git 是否可用。在该目录执行：

```bash
git clone https://gitee.com/fuyang_lipengjun/platform.git
cd platform
```

如果目标位置已存在 `platform` 目录，先检查是否为本项目仓库；是则使用已有目录继续，不覆盖文件、不重复克隆、不自动拉取或重置分支。若是其他目录或无法确认归属，选择其他空闲目录或询问用户。Git 未安装或克隆失败时，明确报告缺失工具、网络或权限问题，解决后继续。

进入项目后重新读取仓库中的 `AGENTS.md`，再执行以下启动步骤。通过在线链接收到本说明时，应继续下载和启动项目，不要停留在介绍步骤。

## 给 AI 的执行指令

请实际执行下面的检查、构建、启动和验证，直到项目可访问。不要只复述步骤。先读取仓库 AGENTS.md；所有命令以实际项目根目录为准，不使用作者电脑的绝对路径。默认采用本仓库 Docker Compose 方案，不要同时启动另一套本机后端。

1. 检查环境、配置和已有数据 → 验证工具版本、端口、数据库初始化条件。
2. 安装依赖、构建产物、启动五个服务 → 验证构建退出码、容器状态和日志。
3. 请求页面及两个后端接口 → 验证 HTTP 状态、响应类型和业务响应，并报告地址及未验证项。

保护已有文件和数据库，不自动提交或推送代码，不运行全量 Maven 测试。测试中可能包含邮件、退款、企业付款和微信调用。不要为了启动修改支付证书、微信密钥、支付宝或对象存储配置；不要调用支付、退款、发消息等外部业务。需要缺失凭据、安装系统软件、覆盖已有数据或受工具权限限制时，说明具体阻塞并向用户索取必要信息或授权。其他已授权的常规步骤继续执行。

## 1. 确认目录和环境

在项目根目录执行：

```bash
pwd
git status --short
java -version
mvn -version
node -v
npm -v
docker version
docker compose version
docker info
```

应存在根 `pom.xml`、`docker-compose.yml`、`scripts/`、`_sql/` 和 `platform-admin-ui/`。记录已有改动，不覆盖用户配置。

环境要求：

| 工具 | 要求 |
| --- | --- |
| JDK | 21，`mvn -version` 也必须使用 JDK 21 |
| Maven | 3.8 或兼容版本 |
| Node.js / npm | 用于 Vue 2、webpack 4、gulp 构建；先检查仓库是否新增版本约束，再使用机器已有版本验证兼容性 |
| Docker | Docker 引擎运行中，安装 Compose V2（`docker compose`） |
| 网络 | 可以访问 Maven、npm 仓库和 Docker 镜像仓库 |

Windows 用户使用 WSL2 或支持 Bash 的环境运行 `scripts/*.sh`，同时确保该环境可访问 Docker。默认 Docker 服务使用 MySQL 8.0.36、Redis 7.2、Java 21 JRE 和 Nginx 1.27，无需额外安装本机 MySQL/Redis。

检查宿主机端口 `8888`、`3306`、`6379` 和容器名 `platform-mysql`、`platform-redis`、`platform-admin`、`platform-api`、`platform-nginx` 是否被占用。可以使用 `docker ps -a`，以及系统提供的 `lsof`、`ss` 或同类工具。不要杀死其他项目的进程或删除容器。端口冲突优先调整下一节中的宿主机映射端口；容器名冲突先确认归属。

## 2. 准备本地配置与数据库初始化

仅当 `deploy/.env` 不存在时复制模板；已存在则保留并检查：

```bash
cp deploy/.env.example deploy/.env
```

默认本地配置包括：

```dotenv
TZ=Asia/Shanghai
MAVEN_PROFILE=dev
NGINX_PORT=8888
MYSQL_PORT=3306
REDIS_PORT=6379
MYSQL_DATABASE=platform
MYSQL_ROOT_PASSWORD=root1234
REDIS_PASSWORD=
PLATFORM_ADMIN_JAVA_OPTS=-Xms256m -Xmx512m
PLATFORM_API_JAVA_OPTS=-Xms256m -Xmx512m
```

这些是本地体验默认值。密码不应出现在最终报告或共享日志中。当前 Redis 启动命令没有配置密码，`REDIS_PASSWORD` 应为空；只填写该变量会导致后端认证失败。修改 MySQL/Redis 的宿主机端口不会改变后端容器内连接端口。

先检查 `_sql/`，应包含 `base.sql`、`mall.sql`、`sys_region.sql`。Compose 将整个目录挂载到 MySQL 初始化目录，新数据库首次启动时按文件名依次执行；不要重复手动导入。`sys_region.sql` 从本仓库 `v1.0.0` 分支恢复，包含历史地区数据，不代表最新行政区划。

**首次初始化前检查 MySQL 表名大小写：**地区脚本创建的是小写 `sys_region`，当前实体映射为大写 `SYS_REGION`。Linux MySQL 默认区分表名大小写。对于确认没有既有数据的全新 Docker 数据目录，在 `docker-compose.yml` 的 `mysql.command` 中加入 `--lower-case-table-names=1` 后再启动，并记录这项本地配置调整。MySQL 8 必须在初始化时确定此参数。若 `deploy/data/mysql` 已有数据，先查询实例现有参数和表名，不要直接更改该参数、删除数据或重建数据库；确有不兼容时说明迁移或表名修复方案，由用户确认后执行。

`deploy/data/mysql` 已有数据时，MySQL 不会重新执行初始化 SQL；更改 `.env` 中的数据库名和 root 密码也不会自动修改已有数据库。初始化脚本包含 `DROP TABLE`，不得直接覆盖已有库。若已有环境缺少地区表，先确认目标库并核对表是否存在，再按用户授权补导入；有同名表时先备份和确认。

## 3. 安装管理台依赖

进入前端目录：

```bash
cd platform-admin-ui
npm install
cd ..
```

若当前检出版本有可用且匹配的 `package-lock.json`，优先用 `npm ci`；否则使用 `npm install`。保留安装前已有锁文件，不为了规避错误删除锁文件或升级依赖。

前端是 Vue 2.6、Element UI 2.15、webpack 4，构建命令使用 gulp。遇到错误先保留具体日志并诊断：

- 若出现 `ERR_OSSL_EVP_UNSUPPORTED`，可仅为本次构建设置 `NODE_OPTIONS=--openssl-legacy-provider`，不要全局修改系统配置。
- 安装过程涉及 `chromedriver` 下载；若失败，先确认网络和日志，不直接删除依赖或禁用全部安装脚本。
- Maven/npm/Docker 下载失败时报告实际失败仓库和原因，不随意更换为不可信镜像源。

## 4. 构建后端与管理台

回到根目录执行，上一条成功后再执行下一条：

```bash
bash scripts/build-jars.sh
bash scripts/build-admin-ui.sh
```

后端脚本执行 `mvn -Pdev -pl platform-admin,platform-api -am clean package -DskipTests`。实际 profile 来自执行环境的 `MAVEN_PROFILE`，默认 `dev`，**脚本不会自动加载 `deploy/.env`**。若用户要求其他构建 profile，应显式传入，例如 `MAVEN_PROFILE=test bash scripts/build-jars.sh`。运行时两个后端仍由 Compose 启用 `docker` profile。

前端构建默认读取 `platform-admin-ui/static/config/index-prod.js`，接口基址为 `location.origin + '/platform-framework'`，适配 Nginx 同源代理。本流程不要设置 `NODE_ENV=testing`，当前配置目录没有 `index-test.js`。若终端已有相关环境变量，应仅对本次构建清除后再运行。

若出现前述 OpenSSL 错误，再使用：

```bash
NODE_OPTIONS=--openssl-legacy-provider bash scripts/build-admin-ui.sh
```

必须确认命令退出码为 0，且以下产物存在：

```text
deploy/packages/platform-admin.jar
deploy/packages/platform-api.jar
deploy/packages/platform-admin-ui-dist/index.html
deploy/packages/platform-admin-ui-dist/config/index.js
```

构建会清理对应 `target`、前端 `dist` 和部署用前端产物目录，不能把用户自定义文件放在这些目录内。检查最终 `config/index.js` 的 baseUrl 使用同源地址，避免修改了开发配置却没有影响构建产物。

## 5. 启动服务

先检查 Compose 配置合法性（使用 `--quiet` 避免输出展开后的密码）：

```bash
docker compose --env-file deploy/.env -f docker-compose.yml config --quiet
bash scripts/docker-up.sh
docker compose --env-file deploy/.env -f docker-compose.yml ps -a
docker compose --env-file deploy/.env -f docker-compose.yml logs --tail=100 mysql redis platform-admin platform-api nginx
```

脚本会创建数据目录并启动五个服务。MySQL 首次导入地区数据可能耗时，持续查看初始化日志和健康状态，不以脚本输出“启动完成”作为最终成功依据。

MySQL、Redis 应达到 `healthy`；两个后端和 Nginx 应持续运行，不能反复重启。确认后端日志中出现对应应用启动成功信息，没有数据库、Redis、建表或 Spring Bean 初始化阻断错误。后端未配置 Compose 健康检查，需要继续验证 HTTP。

若容器在启动后端时报告挂载错误，检查 JAR 是否为文件、前端产物是否为目录。若修改了构建产物或配置，需要重新加载对应服务；先确认是本项目容器，再对相关服务执行 `restart` 或 `up -d --force-recreate`，避免操作其他项目。

## 6. 验证页面与接口

根据 `.env` 的实际 `NGINX_PORT` 设置地址，以下以默认 `8888` 为例：

```bash
curl -sS --max-time 30 -o /tmp/platform-login.html -w '%{http_code}\n' http://localhost:8888/
curl -sS --max-time 30 http://localhost:8888/config/index.js
curl -sS --max-time 30 -D /tmp/platform-captcha.headers -o /tmp/platform-captcha.jpg 'http://localhost:8888/platform-framework/captcha.jpg?uuid=ai-startup-check'
curl -sS --max-time 30 -X POST http://localhost:8888/platform-framework-api/app/index/index
```

验收要求：

- 管理台返回 HTTP 200，内容是管理台 HTML；配置和引用的静态资源可以加载。
- 验证码接口返回 HTTP 200、图片类型及非空图片，不能是 Nginx 回退的 HTML 或 JSON 错误。
- 商城首页接口返回成功业务 JSON，结合 `RestResponse` 当前定义检查业务状态和数据字段，不能只看 HTTP 200。
- 如有浏览器工具，打开管理台确认登录页和验证码展示、无关键资源加载失败。用户提供有效管理员凭据时再验证登录与菜单；不要猜测默认密码、重置已有账号或绕过验证码。初始 SQL 有 `admin` 账号，密码明文不能从哈希推导。
- 检查数据库中系统表、商城表、地区表均存在且有初始化数据；核对 `@@lower_case_table_names` 与实际 SQL 映射兼容。可使用容器内 MySQL 客户端，避免把密码直接写入命令或输出。

默认访问地址：

| 服务 | 地址 |
| --- | --- |
| 管理台 | `http://localhost:8888` |
| 后台管理接口 | `http://localhost:8888/platform-framework` |
| 商城接口 | `http://localhost:8888/platform-framework-api` |

容器内部后台为 `8080`、商城为 `8081`；Compose 没有把这两个端口映射到宿主机，外部统一通过 Nginx 访问。接口根路径返回 404 不代表服务失败，应请求上面的具体接口。

## 7. 常见阻塞与后续启停

| 现象 | 检查与处理 |
| --- | --- |
| Nginx 502 | 后端是否启动完成、是否反复重启，查看后端首个异常和 Nginx 上游日志 |
| 数据库连接失败 | 检查 MySQL 健康、实际库名和已有库密码；容器中数据库主机应为 `mysql` |
| 表不存在 | 检查初始化是否完成、SQL 首个错误、表名大小写；已有目录不会重跑初始化 |
| Redis 认证失败 | 默认 Redis 无密码，检查是否错误配置了 `REDIS_PASSWORD` |
| 页面空白或请求错误地址 | 检查构建后的 `config/index.js`、版本目录静态资源、浏览器缓存和实际 Nginx 端口 |
| 微信或支付功能失败 | 基础启动与第三方联调分别报告；凭据及平台授权由用户提供，不替换为作者配置 |

停止本项目并保留数据：

```bash
bash scripts/docker-down.sh
```

再次启动已有产物：

```bash
bash scripts/docker-up.sh
```

代码改动后重新执行相关构建脚本，并重新加载对应容器。不要执行删除 `deploy/data`、清空数据库、`docker system prune` 或其他全局清理来解决启动问题。

## 8. 可选的微信小程序启动

仅当用户需要小程序时执行：原生微信小程序目录为 `wx-mall`，`uni-mall` 是另一套独立实现。

1. 检查微信开发者工具是否安装、用户是否登录，以及用户自己的小程序 AppID 和项目权限。
2. 导入 `wx-mall`，保留已有项目配置，按用户提供的信息设置 AppID。
3. 检查 `wx-mall/config/api.js`，使用 Docker 默认端口时，将本地 API 基址设为 `http://localhost:8888/platform-framework-api/app/`；若端口变化同步调整。
4. 本地开发工具调试按需开启“不校验合法域名”等调试选项。真机访问必须使用手机可访问的局域网地址或合规 HTTPS 域名，手机的 `localhost` 不是开发电脑。
5. 编译并验证首页接口及页面。微信登录、支付、消息等分别依赖 AppID、Secret、商户配置和平台权限，缺少时明确列为未验证，不宣称全业务可用。

## 9. AI 最终交付格式

启动完成后请向用户报告：实际项目路径、启动方式、五个服务状态、管理台和接口访问地址、各项 HTTP/业务验证结果、日志查看及停止命令、为启动修改的文件。浏览器、管理员登录、小程序和第三方业务未验证的部分必须单独说明。

如果启动失败，报告首个阻断错误、相关日志摘要、已经完成的步骤及下一步所需信息；不得以“容器已创建”或“构建成功”代替“项目启动成功”。
