#!/bin/bash
cd "$(dirname "$0")/.." || exit #if script not into root directory
mvn clean install -DskipTests
docker-compose up

#TODO task 10 - docker-compose