#!/bin/bash

set -ev

ARTIFACTS_DIR=$(dirname  "$0")/artifacts

mkdir -p "$ARTIFACTS_DIR"

configtxgen -profile OneOrgOrdererGenesis -outputBlock "$ARTIFACTS_DIR"/genesis.block
