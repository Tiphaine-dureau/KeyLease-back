FROM maven:3.8.7-amazoncorretto-17
WORKDIR /app
COPY target/key-lease-0.0.1-SNAPSHOT.jar /app/key-lease.jar
ENTRYPOINT ["java","-jar","key-lease.jar"]
