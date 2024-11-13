FROM openjdk:21-jdk AS build
WORKDIR /gatway
COPY . /gatway

FROM openjdk:21-jdk
WORKDIR /opt/gatway
ARG JAR_FILE=/gatway/target/*.jar
COPY --from=build $JAR_FILE /opt/gatway/gatway.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/gatway/gatway.jar"]