#!/bin/bash
cd "$(dirname "$0")/.." || exit #if script not into root directory
export COMPOSE_ENV_FILE=./config/docker-compose.env
mvn clean install -DskipTests

echo "Starting docker-compose"
docker-compose --env-file $COMPOSE_ENV_FILE up -d
echo "Docker-compose started"

echo "Running health check..."
java -jar target/jira-1.0-healthcheck.jar
echo "Health check complete"

echo "Waiting for nginx to be up..."
./scripts/wait-for-it.sh localhost:80 --timeout=30 --strict -- echo "nginx on 80 is up"
echo "Nginx and App should be up now"

echo "Current OS: $OSTYPE"
if [[ "$OSTYPE" == "linux-gnu"* ]] || [[ "$OSTYPE" == "linux-musl"* ]] || [[ "$OSTYPE" == "linux"* ]]; then
        echo "Opening in Linux"
        xdg-open http://localhost:80 &
elif [[ "$OSTYPE" == "darwin"* ]]; then
        echo "Opening in Mac"
        open http://localhost:80 &
elif [[ "$OSTYPE" == "cygwin" ]] || [[ "$OSTYPE" == "msys" ]]; then
        echo "Opening in Windows"
        start http://localhost:80 &
else
        echo "Could not detect the operating system."
fi

# TODO p10 - docker-compose