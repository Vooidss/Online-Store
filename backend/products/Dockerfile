FROM openjdk:21-jdk as build
WORKDIR /products
COPY . .
WORKDIR /opt/products
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/products/products.jar
EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/products/products.jar"]