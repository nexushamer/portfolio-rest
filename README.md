# Portfolio REST API

This is a REST API for manage the profile of a user, the application consume 
the Twitter API for get the last tweets of the user.

## Requirements

- Java8+
- Maven
- Mysql
- Docker*
- Docker-compose* 

NOTE:
Please check if maven is install in your machine, you can check it with the
following command:

```bash
mvn -v
```

You must receive a response similar to the following:

```bash
Apache Maven 3.6.3
Maven home: /usr/share/maven
...
```

## Installation

Clone this repository with the following command:

```bash
git clone https://github.com/nexushamer/portfolio-rest.git
```

You can execute this project in local using maven or you can start the service with  
docker-compose.

## Executing with Maven

Execute the following command for start the application

```bash
mvn spring-boot:run
```

## Executing with Docker and Docker-Compose

For start the application with docker-compose you must execute the following command:
```bash
docker-compose up
```
This command will be start the MYSQL container and the JAVA container with the
application, after that you can test the service with any REST Client.

## Testing

For execute the test MAVEN is require, you can execute the test with the following command:
```bash
mvn clean compile verify
```

This will show the execution of the test, also you can check the coverage of the code opening
the jacoco report, that file is in the following path
```
target/site/jacoco/index.html
```

The Service run at the port 8088