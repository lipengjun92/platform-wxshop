# Docker 部署说明

## 产物准备

1. 执行 `scripts/build-jars.sh`
2. 执行 `scripts/build-admin-ui.sh`

产物会输出到：

- `deploy/packages/platform-admin.jar`
- `deploy/packages/platform-api.jar`
- `deploy/packages/platform-admin-ui-dist/`

## 启动

首次启动前，如需调整端口、数据库名或 JVM 参数，先复制并修改：

```bash
cp deploy/.env.example deploy/.env
```

其中：

- `MAVEN_PROFILE` 控制 `scripts/build-jars.sh` 使用的 Maven profile，默认是 `dev`

然后执行：

```bash
scripts/docker-up.sh
```

## 停止

```bash
scripts/docker-down.sh
```

## 说明

- `mysql` 初始化脚本直接挂载仓库 `_sql/` 目录，首次启动会按文件名顺序执行。
- `nginx` 对外暴露默认端口 `80`，同时托管前端静态资源并反代两个后端服务。
- 后端容器运行时启用 `docker` profile，只覆盖 MySQL 和 Redis 地址，不改你现有 `dev/test/prod` 配置。
