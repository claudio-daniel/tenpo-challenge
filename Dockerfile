FROM openjdk:22
COPY target/rest.api-0.0.1-SNAPSHOT.jar rest.api-0.0.1-SNAPSHOT.jar
EXPOSE 9009 6565
ENTRYPOINT ["java","-jar","/rest.api-0.0.1-SNAPSHOT.jar"]