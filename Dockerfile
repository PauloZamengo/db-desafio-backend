FROM maven:3.8.2-jdk-11

COPY target/db-desafio-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]