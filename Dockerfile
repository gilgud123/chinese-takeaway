FROM openjdk:10.0.2-jre-slim

MAINTAINER Katya de Vries kdvblended@gmail.com

# copy JAR into image
COPY target/takeaway-0.0.1-SNAPSHOT.jar /app.jar

# copy logging file into image
# docker run -d -v /var/log/app:/var/log/Application/ -p 8090:8080 takeaway:latest
COPY logback.xml /logback.xml

# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "-Dlogging.config=/logback.xml", "/app.jar"]