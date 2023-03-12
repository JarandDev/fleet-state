#!/bin/bash

docker rm -f -v fleet-state-db

docker network create jarand-net || true

docker run -dt --name fleet-state-db -p 5432:5432 --network jarand-net \
  -e POSTGRES_DB=fleet-state-db \
  -e POSTGRES_USER=fleet-state-dbo \
  -e POSTGRES_PASSWORD=postgres \
  postgres:14
