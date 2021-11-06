set -ev

CH_NAME=mychannel
CONTRACT_NAME=papercontract
VERSION="${1:-1}"
CONTRACT_PATH=contract-java
COLLECTIONS=./collections/collection_config.json

# Install chaincode
peer chaincode install -n "$CONTRACT_NAME" -v "$VERSION" -p $CONTRACT_PATH -l java --peerAddresses peer0.org1.example.com:7051
peer chaincode install -n "$CONTRACT_NAME" -v "$VERSION" -p $CONTRACT_PATH -l java --peerAddresses peer1.org1.example.com:7051

# Instantiate chaincode
peer chaincode upgrade -n "$CONTRACT_NAME" -v "$VERSION" -l java -c '{"Args":["org.papernet.commercialpaper:instantiate"]}' --collections-config "$COLLECTIONS" -C $CH_NAME -P "AND ('Org1MSP.member')"