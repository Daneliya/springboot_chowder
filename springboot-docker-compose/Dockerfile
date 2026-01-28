# 基础镜像，使用官方 OpenJDK 21 镜像（Alpine 版本）
FROM openjdk:21-alpine AS builder
# 复制jar包
COPY target/springboot-docker-compose-1.0.0.jar /springboot-docker-compose.jar
# 构建应用（跳过测试）
RUN mvn clean package -DskipTests
# 启动容器时执行的命令
CMD ["java", "-jar", "springboot-docker-compose.jar"]
# 暴露端口
EXPOSE 18181