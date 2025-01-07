FROM openjdk:17

# Copy built jar from build\libs directory over to spring-boot-artist-albums-docker image/container
COPY target/*.jar music.system.jar

# Expose port 8080
EXPOSE 8080

# set the startup command to execute the jar
CMD ["java", "-jar", "/music.system.jar"]

# to run open cmd propmt/terminal and run
# docker run -d -p 8080:8080 --name music.system music.system:latest