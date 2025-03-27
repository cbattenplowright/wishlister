# Use a specific version of Maven and JDK for the build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
LABEL authors="callu"

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src src
RUN mvn package -DskipTests

# Extract the built JAR for the runtime stage
RUN java -Djarmode=layertools -jar target/*.jar extract

# Use a specific version of JDK for the runtime stage
FROM eclipse-temurin:17-jdk-jammy AS runner

# Set the working directory
WORKDIR /app

# Copy the extracted layers from the build stage
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Set the entry point to run the application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]