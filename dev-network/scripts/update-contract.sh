set -ev

CH_NAME=mychannel
VERSION="$1"
CONTRACT_PATH=$(dirname  "$0")/../contract-java
: ${VERSION:=1}

# Install chaincode
peer chaincode install -n papercontract -v $VERSION -p "$CONTRACT_PATH" -l java --peerAddresses peer0.org1.example.com:7051
peer chaincode install -n papercontract -v $VERSION -p "$CONTRACT_PATH" -l java --peerAddresses peer1.org1.example.com:7051

# Instantiate chaincode
peer chaincode upgrade -n papercontract -v $VERSION -l java -c '{"Args":["org.papernet.commercialpaper:instantiate"]}' -C $CH_NAME -P "AND ('Org1MSP.member')"
