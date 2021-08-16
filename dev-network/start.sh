#!/bin/bash
set -ev

# don't rewrite paths for Windows Git Bash users
export MSYS_NO_PATHCONV=1

docker-compose down
docker-compose up -d cli
. update-genesis.sh

docker-compose up -d
. init.sh