#!/bin/sh

./mvnw clean package

docker rmi -f book-api
docker rm  -f book
docker build -t book-api .
docker run --name book -p 8080:8080 -t book-api