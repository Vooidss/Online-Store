FROM openjdk:21-jdk
WORKDIR /orders
COPY . .
WORKDIR /opt/orders
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/orders/orders.jar
EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/opt/orders/orders.jar"]