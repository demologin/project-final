#!/bin/bash
java -Dspring.profiles.active=prod -jar ./target/jira-1.0.jar
#while :; do echo 'Press <CTRL+C> to exit.'; sleep 1; done
