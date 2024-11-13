FROM openjdk:21-jdk AS build
WORKDIR /authorization
COPY . /authorization

FROM openjdk:21-jdk
WORKDIR /opt/authorization
ARG JAR_FILE=/authorization/target/*.jar
COPY --from=build $JAR_FILE /opt/authorization/authorization.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/authorization/authorization.jar"]