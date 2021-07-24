set -ev

CH_NAME=mychannel
ARTIFACTS_DIR=artifacts
ORDERER=orderer.example.com:7050

# Generate channel transaction
mkdir -p $ARTIFACTS_DIR
configtxgen -profile OneOrgChannel -outputCreateChannelTx $ARTIFACTS_DIR/channel.tx -channelID $CH_NAME

# Create the channel
peer channel create -o $ORDERER -c $CH_NAME -f $ARTIFACTS_DIR/channel.tx --outputBlock $ARTIFACTS_DIR/$CH_NAME.block

# Join peers to the channel.
peer channel join -b $ARTIFACTS_DIR/$CH_NAME.block
CORE_PEER_ADDRESS=peer1.org1.example.com:7051
peer channel join -b $ARTIFACTS_DIR/$CH_NAME.block
CORE_PEER_ADDRESS=peer0.org1.example.com:7051

# Install chaincode
peer chaincode install -n papercontract -v 0 -p contract-java -l java --peerAddresses peer0.org1.example.com:7051
peer chaincode install -n papercontract -v 0 -p contract-java -l java --peerAddresses peer1.org1.example.com:7051

# Instantiate chaincode
peer chaincode instantiate -n papercontract -v 0 -l java -c '{"Args":["org.papernet.commercialpaper:instantiate"]}' -C $CH_NAME -P "AND ('Org1MSP.member')"
