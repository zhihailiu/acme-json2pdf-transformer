# ACME JSON to PDF Transformer Engine

## Purpose
This is an example transformer used to demonstrate how to create a simple transformer based on
[alfresco-transform-core](https://github.com/Alfresco/alfresco-transform-core). The code is based on [alfresco-helloworld-transformer-engine](https://github.com/Alfresco/alfresco-helloworld-transformer/tree/master/alfresco-helloworld-transformer-engine). It takes a json file and "transforms" to a pre-configured dummy pdf file. AKA this is not real transformation but a proof of concept of custom transform engine. 

## Prerequisites
* Java 11
* Maven
* Docker

## Build using Maven
The project can be built by running the Maven command:

```bash
mvn clean install -Plocal
```

This will build the project as a Spring Boot fat jar in the {project directory}/target folder
and as a docker image in the local docker registry.

Before proceeding to start the container, confirm that the build was successful and the local docker
registry contains **acme/acme-json2pdf-transformer** image.

## Run in Docker

Execute the following commands to run the transformer container in detached mode on port 8090 and to show the logs:

```bash
docker run -d -p 8090:8090 --name acme-json2pdf-transformer acme/acme-json2pdf-transformer:latest
docker logs -f acme-json2pdf-transformer
```

> Since this is a Spring Boot application,
 it might be helpful to run it as such during development by either executing `mvn spring-boot:run`
 or `java -jar target/acme-json2pdf-transformer-{version}.jar` in the project directory.
 The application will be accessible on port 8090.

## Additional notes

##### Maven settings
Add Alfresco repository below to Maven settings.xml

```bash
https://artifacts.alfresco.com/nexus/content/repositories/public/
```

##### Issues with fabric8-maven-plugin
Ensure that the Docker installation has Docker Experimental Features enabled. For Docker Desktop on Windows, add to Docker Desktop > Settings > Docker Engine

```bash
"experimental":true
```
