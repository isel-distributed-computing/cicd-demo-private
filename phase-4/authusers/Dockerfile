FROM openjdk:17-alpine
RUN apk update
RUN apk add curl
COPY target/authuser-phase2-1.0.0.jar /app/authuser-phase2-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/authuser-phase2-1.0.0.jar"]