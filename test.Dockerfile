FROM amazoncorretto:11-alpine-jdk
MAINTAINER Burak Vural <burak.vural>
COPY target/controller-0.0.1-SNAPSHOT.jar controller-0.0.1.jar
EXPOSE 20001
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=test", "/controller-0.0.1.jar"]