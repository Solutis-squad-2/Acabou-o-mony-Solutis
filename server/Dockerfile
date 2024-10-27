FROM openjdk:21-jdk-slim
RUN mkdir /server
WORKDIR /server
COPY target/*.jar /server/server.jar
RUN ls -al /server
CMD ["java", "-jar", "/server/server.jar"]