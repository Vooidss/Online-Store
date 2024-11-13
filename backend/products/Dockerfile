FROM openjdk:21-jdk AS build
WORKDIR /products
COPY . /products

FROM openjdk:21-jdk
WORKDIR /opt/products
ARG JAR_FILE=/products/target/*.jar
COPY --from=build $JAR_FILE /opt/products/products.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/products/products.jar"]