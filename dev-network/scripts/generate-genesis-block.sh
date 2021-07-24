set -ev

ARTIFACTS_DIR=artifacts

mkdir -p $ARTIFACTS_DIR
configtxgen -profile OneOrgOrdererGenesis -outputBlock $ARTIFACTS_DIR/genesis.block
