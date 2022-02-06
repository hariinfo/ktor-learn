FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs /app/
ENTRYPOINT ["java","-jar","/app/com.example.ktor-exposed-demo-0.0.1-all.jar"]