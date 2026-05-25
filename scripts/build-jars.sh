#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PACKAGE_DIR="$ROOT_DIR/deploy/packages"
MAVEN_PROFILE="${MAVEN_PROFILE:-dev}"

mkdir -p "$PACKAGE_DIR"

cd "$ROOT_DIR"
mvn -P"$MAVEN_PROFILE" -pl platform-admin,platform-api -am clean package -DskipTests

cp "$ROOT_DIR/platform-admin/target/platform-admin.jar" "$PACKAGE_DIR/platform-admin.jar"
cp "$ROOT_DIR/platform-api/target/platform-api.jar" "$PACKAGE_DIR/platform-api.jar"

echo "JAR 已输出到:"
echo "  $PACKAGE_DIR/platform-admin.jar"
echo "  $PACKAGE_DIR/platform-api.jar"

