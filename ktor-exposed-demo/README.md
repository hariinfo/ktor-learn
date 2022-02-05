### Quickstart:Docker
Prerequisite to run the sample:
To bring up PostgreSQL and Kafka docker container
```
docker compose up
```

Run either from IDE Application.kt main method or run the gradle command to start server
```./gradlew run```

### Build and Run using JAR
Run this in the terminal from ktor-exposed-demo directory
```
./gradlew shadowJar
```
change directory to build/libs and run
```
java -jar com.example.ktor-exposed-demo-0.0.1-all.jar
```