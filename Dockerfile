FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY target/project-1.0.jar .
COPY mazes/ ./mazes/
COPY tests/ ./tests/

ENTRYPOINT ["java", "-jar", "project-1.0.jar"]
