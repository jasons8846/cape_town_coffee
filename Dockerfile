FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} coffeewiki-0.0.1-SNAPSHOT.jar

EXPOSE 5000

ENTRYPOINT ["java","-jar","/app/app.jar"]