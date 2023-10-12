#!/bin/bash

# Kill process running on port 8080
fuser -k 8080/tcp

# Start Spring Boot project
./mvnw spring-boot:run
