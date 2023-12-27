FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/internship-0.0.1-SNAPSHOT.jar internship.jar
EXPOSE 80
CMD ["java","-jar","internship"]