#!/bin/bash

docker build -t fleet-state:local .

docker rm -f -v fleet-state

docker network create jarand-net || true

docker run -dt --name fleet-state -p 8080:8080 --network jarand-net \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://fleet-state-db:5432/fleet-state-db \
  -e SPRING_DATASOURCE_USERNAME=fleet-state-dbo \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  fleet-state:local
