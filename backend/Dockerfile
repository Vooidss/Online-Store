FROM maven:3.9.9-eclipse-temurin-21
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY . /basket/
