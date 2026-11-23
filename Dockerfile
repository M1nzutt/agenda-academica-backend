FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 10001 agenda
COPY --from=build /app/target/agenda-academica-1.0.0.jar app.jar
USER agenda
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
