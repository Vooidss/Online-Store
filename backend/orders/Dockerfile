FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY ./pom.xml /backend/
WORKDIR /backend/
COPY pom.xml /orders/build/
RUN mvn dependency:go-offline
WORKDIR /backend/orders/build/
COPY . /backend/orders


FROM openjdk:21-jdk
ARG JAR_FILE=/backend/orders/build/target/*.jar
COPY --from=build $JAR_FILE /opt/orders/orders.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/orders/orders.jar"]
