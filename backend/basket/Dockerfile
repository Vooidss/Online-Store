FROM openjdk:21-jdk AS build
WORKDIR /basket
COPY . /basket

FROM openjdk:21-jdk
WORKDIR /opt/basket
ARG JAR_FILE=/basket/target/*.jar
COPY --from=build $JAR_FILE /opt/basket/basket.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/basket/basket.jar"]