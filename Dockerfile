FROM java:8u111-jdk

MAINTAINER infoander@gmail.com

VOLUME /tmp

EXPOSE 8080

COPY ./target/book-api-0.0.1.jar app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom -Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=n","-jar","/app.jar"]