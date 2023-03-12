FROM maven:3-openjdk-17 AS builder

COPY pom.xml pom.xml
RUN mvn -B dependency:go-offline

COPY src src
RUN mvn -B package

FROM openjdk:17-jdk-alpine

RUN addgroup -S app && adduser -S app -G app
WORKDIR /home/app
USER app

COPY --from=builder /target/fleet-state.jar fleet-state.jar

CMD ["java", "-jar", "/home/app/fleet-state.jar"]
