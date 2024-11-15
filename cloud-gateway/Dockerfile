FROM ubuntu:latest
LABEL authors="vr-pc"

FROM openjdk:17

VOLUME /tmp

EXPOSE 8008

ARG JAR_FILE=target/cloud-gateway-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} cloud-gateway.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar","/cloud-gateway.jar"]