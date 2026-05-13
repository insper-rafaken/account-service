FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY api/account/account/pom.xml ./api/account/account/pom.xml
COPY api/account/account/src     ./api/account/account/src
RUN cd api/account/account && mvn install -DskipTests -q

COPY api/account/account-service/pom.xml ./api/account/account-service/pom.xml
COPY api/account/account-service/src     ./api/account/account-service/src
RUN cd api/account/account-service && mvn clean package -DskipTests -q

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/api/account/account-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
