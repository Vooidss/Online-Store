FROM openjdk:21-jdk as build
WORKDIR /telegrambot
COPY . .
WORKDIR /opt/telegrambot
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/telegrambot/telegrambot.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "/opt/telegrambot/telegrambot.jar"]