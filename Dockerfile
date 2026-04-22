# 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 复制pom文件先下载依赖
COPY backend/pom.xml backend/
COPY backend/*/pom.xml backend/
RUN cd backend && mvn dependency:go-offline -B

# 复制源代码并编译
COPY backend/ ./backend/
RUN cd backend && mvn clean package -DskipTests -B

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
