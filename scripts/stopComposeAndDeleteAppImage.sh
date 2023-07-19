#!/bin/bash
cd "$(dirname "$0")/.." || exit
#docker-compose down --rmi 'local'
docker-compose down --rmi 'local' --volumes   # +volume