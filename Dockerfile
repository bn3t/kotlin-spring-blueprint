FROM amazoncorretto:21-alpine-jdk AS build

WORKDIR /usr/src/app

COPY . .

RUN ./mvnw -B clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/*.jar kotlin-spring-blueprint.jar

EXPOSE 8080

CMD ["java", "-jar", "kotlin-spring-blueprint.jar"]
