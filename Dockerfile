FROM java:8-jdk-alpine
COPY ./target/rig-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch demo-docker-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","rig-0.0.1-SNAPSHOT.jar"]