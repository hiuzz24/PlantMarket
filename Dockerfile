# Bước 1: Build dự án bằng Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng với Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Giới hạn RAM cực kỳ quan trọng để không bị crash
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]