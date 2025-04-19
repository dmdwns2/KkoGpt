#!/bin/bash

# .env file load
set -o allexport
source .env
set +o allexport

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [success] env loaded "

APP_NAME=demo-0.0.1-SNAPSHOT.jar
LOG_FILE=app.log

PID=$(pgrep -f $APP_NAME)
if [ -n "$PID" ]; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [stopped] old process killed (PID=$PID)..."
  kill -15 $PID
  sleep 3
else
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [info] no running process found"
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [starting] launching Spring Boot"
nohup java -jar ./$APP_NAME > $LOG_FILE 2>&1 &

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [success] Spring Boot started (log: $LOG_FILE)"