FROM eclipse-temurin:21-jre-alpine
ARG VERSION
COPY target/aiBot-${VERSION}.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
