# Use Java and Gradle
FROM gradle:9.7.1-jdk17

# Create app folder inside container
WORKDIR /app

# Copy project files into container
COPY . .

# Build the application
RUN ./gradlew build --no-daemon

# Web app uses port 7070
EXPOSE 7070

# Start the web application
CMD ["./gradlew", "run", "--no-daemon"]