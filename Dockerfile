FROM openjdk:11-slim-buster
COPY target/rig-0.0.1-SNAPSHOT.jar rig.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","rig.jar"]