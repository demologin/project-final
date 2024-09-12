#!/bin/bash
DATA_DIR=data
if [ ! -f $DATA_DIR ]; then
    mkdir $DATA_DIR
fi

#mvn clean install -Pprod

docker-compose up -d
