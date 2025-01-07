To Build and deploy to Docker

Open a terminal and cd to,
C:\Users\softw\eclipse-workspace\music.system

Build your project, 
mvnw clean package


Now in eclipse-workspace\music.system\target, 
re-name music.system-0.0.1-SNAPSHOT.jar file to music.system.jar and delete music.system-0.0.1-SNAPSHOT.jar.original


Now in your terminal run,
docker image build -t music.system .


Now in your terminal start docker running,
docker-compose up -d
or
docker run -d -p 8080:8080 --name music.system music.system:latest

docker-compose down to stop

Now open Visual Studio Code and start your react app open a browser at http://localhost:3000/