FROM openjdk:17-alpine
RUN apk update
RUN apk add curl
COPY target/todolist-phase2-1.0.0.jar /app/todolist-phase2-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/todolist-phase2-1.0.0.jar"]