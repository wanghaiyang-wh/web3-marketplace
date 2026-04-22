# 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 复制整个backend目录
COPY backend/ ./backend/

# 安装父POM（使用backend作为根目录）
WORKDIR /build/backend
RUN rm -rf ~/.m2/repository/com/web3/marketplace && mvn install -N -B

# 安装子模块到本地仓库
RUN rm -rf ~/.m2/repository/com/web3/marketplace && mvn install -DskipTests -B

# 编译打包
RUN mvn package -DskipTests -B

# 运行阶段
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制JAR文件
COPY --from=builder /build/backend/client-api/target/*.jar client-api.jar
COPY --from=builder /build/backend/merchant-api/target/*.jar merchant-api.jar
COPY --from=builder /build/backend/admin-api/target/*.jar admin-api.jar

# 默认启动client-api
EXPOSE 8081 8082 8083
CMD ["java", "-jar", "client-api.jar"]
