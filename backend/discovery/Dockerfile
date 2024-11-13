FROM openjdk:21-jdk AS build
WORKDIR /discovery
COPY . /discovery

FROM openjdk:21-jdk
WORKDIR /opt/discovery
ARG JAR_FILE=/discovery/target/*.jar
COPY --from=build $JAR_FILE /opt/discovery/discovery.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/discovery/discovery.jar"]