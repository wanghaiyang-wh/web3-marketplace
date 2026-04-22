# 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 复制pom文件
COPY backend/pom.xml backend/
COPY backend/*/pom.xml backend/

# 先安装父POM到本地仓库
RUN cd backend && mvn install -N -B

# 安装子模块到本地仓库
RUN cd backend && mvn install -DskipTests -B

# 复制源代码并编译打包
COPY backend/ ./backend/
RUN cd backend && mvn package -DskipTests -B

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
