FROM openjdk:8
ADD /target/BookManager-0.0.1-SNAPSHOT.jar BookManager-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","BookManager-0.0.1-SNAPSHOT.jar"]