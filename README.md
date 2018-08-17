[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ba6b8d35e789468895cfcfe053a1515f)](https://www.codacy.com/app/ander-f-silva/book-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ander-f-silva/book-api&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/ander-f-silva/book-api.svg?branch=master)](https://travis-ci.org/ander-f-silva/book-api)
[![Coverage Status](https://coveralls.io/repos/github/ander-f-silva/book-api/badge.svg)](https://coveralls.io/github/ander-f-silva/book-api)

# Projeto API Books

Construir um microserviço para realizar inserir,consultar e listar livros.

## Reflexão sobre o problema

Para elaborar um mvp (projeto inicial) pensei em dois frameworks.
Primeiro o Vert.x, pois este framework é gerar um container leve e trabalhar com modelo de programação reativa.
Segundo foi Spring que possui muitos projetos que são faceis de integrar com banco de dados NSQL e segue padrão de cofigurações muito fácil de usar .

Acabei adotando o Spring por que oferecia mais recursos para integrar de forma rápida e também possui um suite mais completar para lidar com Testes.

Para não haver depedência com infra local estou virtualizado o projeto com container docker.

Os banco de dados relacionais não são locais, estou utilizando hospedagem free do mlab.

Pensei em uma solução minimalista para ser rápido para desenvolver e por que tenho maior dominio. 

Outro motivações como uso do Banco pensei um modelo nsql, pois os dados não possuem relacionademento e podem ser guardados em um banco não relacional.

Com Spring Boot é possivel realizar um setup de projeto simples e facill para montar o ambiente.

## Tecnologias utilizadas

* Linguagem Java - Versão 1.8 (Oracle 1.8.0_121)

```
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)
```

* Maven 3 - Ferramenta de Build

```
Apache Maven 3.5.3 (3383c37e1f9e9b3bc3df5050c29c8aff9f295297; 2018-02-24T16:49:05-03:00)
Maven home: /home/anderson/Tools/apache-maven-3.5.3
Java version: 1.8.0_181, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-oracle/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "4.15.0-32-generic", arch: "amd64", family: "unix"

```

* Versionador - Git

* Banco de Dados - Mongo (emmbed para ambiente de teste e mLab para hospedagem em prod e local)

* Spring Web (MVC) - Framerwork Web para geração das API's (versão 2.0.4) com Tomcat 8

* Spring Boot - Setup de projeto

O repositório utilizado é o Github, onde utilizei o Git flow com a branch develop e master para gerenciar o fonte.

Para realizar o CI usei o Travis com a plataforma Pass Heroku para relizar o deploy da API.

Na pasta postman tem um projeto que poderá importar para testar  as apis na plataforma Heroku ou local.

Quando realizo um push o pull request, automanticamente o Travis já efetua o build e realizava o deploy no heroku.

Utilizei o framerk Junit para teste integrado (sufixo Test).

# Documentação através do swagger

O projeto possui um documentação de API através do swagger.

Para acessar:

### Api: beer-style-resource : Beer Style Resource

http://localhost:8080/swagger-ui.html ou https://api-books.herokuapp.com/swagger-ui.htm

![Swagger](https://github.com/ander-f-silva/book-api/blob/master/images/swagger.png)


## Para realizar o build, teste unitários e iniciar o software

Para executar o versão no ambiente local é necessario ter versão a partir do 8.

Não precisa instalar o gradlew, pois na raiz do projeto possui o gradle wrapper que pode ser usado no Windows o Linux.

Antes de realizar o deploy execute o comando abaixo para executar os testes:

```
./mvnw test
```

Para realizar build:

```
./mvnw clean package
```

Acesse a pasta build/libs e execute o comando:

```
java -jar book-api-0.0.1.jar
```

Outra solução usando docker para não depender do SO da máquina

Fiz um script para execultar no Linux. Execute os comando abaixo:

```
./mvnw clean package
 script.sh
```
Para ambiente Windows. Execute os comando abaixo:

```
./mvnw clean package
docker rmi -f book-api
docker rm  -f book
docker build -t book-api .
docker run --name book -p 8080:8080 -t book-api
```

## Gestão do Projeto e técnicas para construção da API

Não precisei usar Kaban paraa administrar as atividades, tendo conhecimento do problema.

Mas as etapas foram:

* Passo 0: Criação do projeto no https://start.spring.io/
* Passo 1: Contrução das classes de dominio;
* Passo 2: Construção dos testes unitários;
* Passo 3: Construção da API e mecanismo para armazenar os dados;
* Passo 4: Inclui os serviço na plataforma Heroku.

Para o desenvolvimento utilizei TDD para ajudar na elaboração do Desing e garantir que feedback de erro caso ocorrece.
A modelagem pensei nos pattners GOF e DDD, tanto que os diretórios do projeto estão influenciados pelas camandas de Aplicação
, Dominio e Infraestrutura.

Acesso link do trello https://trello.com/b/a1Rzue54/guia-bolso-book-api (caso não visualize me informe para dar permissão por email)

Obs: Acesso os markdowns para olhar cobertura de teste, analise estatica do código e CI do projeto.
