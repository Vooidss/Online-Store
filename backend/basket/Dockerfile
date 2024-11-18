FROM openjdk:21-jdk
WORKDIR /basket
COPY . .
WORKDIR /opt/basket
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/basket/basket.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "/opt/basket/basket.jar"]