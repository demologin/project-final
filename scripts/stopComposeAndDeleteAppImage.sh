#!/bin/bash
cd "$(dirname "$0")/.." || exit
export COMPOSE_ENV_FILE=./config/docker-compose.env
#docker-compose down --rmi 'local' # NO volume
docker-compose --env-file $COMPOSE_ENV_FILE down --rmi 'local' --volumes   # +volume