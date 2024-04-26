
FROM openjdk:latest

ENV JAR_NAME=internship-backend.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME