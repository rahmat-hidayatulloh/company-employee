FROM ubuntu:latest
LABEL authors="vr-pc"

FROM openjdk:17

VOLUME /tmp

EXPOSE 8882

ARG JAR_FILE=target/registry-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} registry-service.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar","/registry-service.jar"]