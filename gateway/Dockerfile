FROM openjdk:21-jdk-slim
RUN mkdir /gateway
WORKDIR /gateway
COPY target/*.jar /gateway/gateway.jar
CMD ["java","-jar","/gateway/gateway.jar"]