#!/bin/bash

# .env file load
set -o allexport
source .env
set +o allexport

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [success] env loaded "

nohup java -jar ./build/libs/myapp-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

echo "[$(date '+%Y-%m-%d %H:%M:%S')] Spring Boot started "