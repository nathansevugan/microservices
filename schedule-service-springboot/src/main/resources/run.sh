#!/usr/bin/env bash


java -Dserver.port=9090 -Dmongodb.hostname=localhost -Dmongodb.port=27017 -jar ../schedule-service-springboot-0.0.1-SNAPSHOT.jar