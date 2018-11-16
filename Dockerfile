FROM openjdk:10.0.2-jre-slim

MAINTAINER Katya de Vries kdvblended@gmail.com

# copy WAR into image
COPY target/takeaway-0.0.1-SNAPSHOT.jar /app.jar

# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]