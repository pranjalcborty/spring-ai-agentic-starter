FROM gradle:jdk17 AS builder
COPY src /usr/src
COPY build.gradle /usr/
WORKDIR /usr/
RUN gradle clean bootJar

FROM eclipse-temurin:17-jre
COPY --from=builder /usr/build/libs/app.jar /usr/app.jar
ENTRYPOINT ["java", "-jar", "/usr/app.jar"]