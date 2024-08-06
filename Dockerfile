
FROM openjdk:17-alpine

EXPOSE 8081
COPY target/librarymanagementapp.jar spring-librarymanagementapp.jar
ENTRYPOINT ["java", "-jar", "spring-librarymanagementapp.jar"]
