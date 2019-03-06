FROM openjdk:8-jre-slim

RUN apt-get upgrade && apt-get update

RUN mkdir -p /opt/service

COPY build/libs/product-service*.jar /opt/service/product-service.jar

ENTRYPOINT ["java", "-jar", "/opt/service/product-service.jar"]

