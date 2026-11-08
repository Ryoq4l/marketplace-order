FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY marketplace-common/pom.xml ./marketplace-common/pom.xml
COPY marketplace-common/src ./marketplace-common/src
RUN cd marketplace-common && mvn clean install -DskipTests

COPY marketplace-order/pom.xml ./marketplace-order/pom.xml
COPY marketplace-order/src ./marketplace-order/src
RUN cd marketplace-order && mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/marketplace-order/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]