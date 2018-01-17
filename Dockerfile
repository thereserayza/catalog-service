FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/catalog-service*.jar catalog-service.jar
RUN sh -c 'touch /catalog-service.jar'
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "catalog-service.jar" ]