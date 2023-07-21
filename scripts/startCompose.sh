#!/bin/bash
cd "$(dirname "$0")/.." || exit
export COMPOSE_ENV_FILE=./config/docker-compose.env
docker-compose --env-file $COMPOSE_ENV_FILE up