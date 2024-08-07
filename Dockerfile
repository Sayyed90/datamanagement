
FROM openjdk:17-alpine

EXPOSE 8005
COPY target/librarymanagementapp.jar spring-librarymanagementapp.jar
ENTRYPOINT ["java", "-jar", "spring-librarymanagementapp.jar"]
