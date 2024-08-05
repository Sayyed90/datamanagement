
FROM openjdk:17-alpine

EXPOSE 8081
COPY target/librarymanagementsystem-0.0.1-SNAPSHOT.jar spring-librarymanagementsystem-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "spring-librarymanagementsystem-0.0.1-SNAPSHOT.jar"]
