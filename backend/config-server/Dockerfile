FROM openjdk:21-jdk AS build
WORKDIR /config-server
COPY . /config-server

FROM openjdk:21-jdk
WORKDIR /opt/config-server
ARG JAR_FILE=/config-server/target/*.jar
COPY --from=build $JAR_FILE /opt/config-server/config-server.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/config-server/config-server.jar"]