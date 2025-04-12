# Use Java 17 base image
FROM openjdk:17-alpine

RUN apk update && apk add maven

# RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
 

#moving
# ADD target/VMServiceFile.jar /usr/src/VMServiceFile.jar
ENTRYPOINT ["java", "-jar", "target/VMServiceFile.jar"]
# Expose service port
EXPOSE ${BACKEND_PORT}
 
#run
# ENTRYPOINT ["java", "-jar", "/usr/src/VMServiceFile.jar"]