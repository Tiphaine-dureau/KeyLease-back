#Build stage
FROM maven:3.8.7-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Pprod

#Package stage
FROM openjdk:17-alpine
COPY --from=build /home/app/target/key-lease-0.0.1-SNAPSHOT.jar key-lease.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","key-lease.jar"]

