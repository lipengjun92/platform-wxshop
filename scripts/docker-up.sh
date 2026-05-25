#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
COMPOSE_FILE="$ROOT_DIR/docker-compose.yml"
ENV_FILE="$ROOT_DIR/deploy/.env"

ADMIN_JAR="$ROOT_DIR/deploy/packages/platform-admin.jar"
API_JAR="$ROOT_DIR/deploy/packages/platform-api.jar"
UI_DIR="$ROOT_DIR/deploy/packages/platform-admin-ui-dist"

if ! command -v docker >/dev/null 2>&1; then
  echo "未检测到 docker 命令"
  exit 1
fi

if ! docker compose version >/dev/null 2>&1; then
  echo "未检测到 docker compose"
  exit 1
fi

if [[ ! -f "$ADMIN_JAR" ]]; then
  echo "缺少 $ADMIN_JAR，请先执行 scripts/build-jars.sh"
  exit 1
fi

if [[ ! -f "$API_JAR" ]]; then
  echo "缺少 $API_JAR，请先执行 scripts/build-jars.sh"
  exit 1
fi

if [[ ! -f "$UI_DIR/index.html" ]]; then
  echo "缺少 $UI_DIR/index.html，请先执行 scripts/build-admin-ui.sh"
  exit 1
fi

mkdir -p "$ROOT_DIR/deploy/data/mysql" "$ROOT_DIR/deploy/data/redis"

if [[ ! -f "$ENV_FILE" ]]; then
  cp "$ROOT_DIR/deploy/.env.example" "$ENV_FILE"
  echo "已生成默认环境文件: $ENV_FILE"
fi

NGINX_PORT_VALUE="$(grep -E '^NGINX_PORT=' "$ENV_FILE" | tail -n 1 | cut -d '=' -f 2-)"
if [[ -z "$NGINX_PORT_VALUE" ]]; then
  NGINX_PORT_VALUE="8888"
fi

cd "$ROOT_DIR"
docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" up -d

echo "启动完成，可访问:"
echo "  管理台: http://localhost:${NGINX_PORT_VALUE}"
echo "  后台接口: http://localhost:${NGINX_PORT_VALUE}/platform-framework"
echo "  商城接口: http://localhost:${NGINX_PORT_VALUE}/platform-framework-api"
