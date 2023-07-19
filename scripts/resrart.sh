#!/bin/bash
cd "$(dirname "$0")/.." || exit
docker-compose down --rmi 'local' --volumes
mvn clean install -DskipTests
docker-compose up