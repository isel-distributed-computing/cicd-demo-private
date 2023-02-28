FROM openjdk:17-alpine
RUN apk update
RUN apk add curl
COPY target/todolist-phase2-notifications-1.0.0.jar /app/todolist-phase2-notifications-1.0.0.jar
ENTRYPOINT ["java","-jar","/app/todolist-phase2-notifications-1.0.0.jar"]