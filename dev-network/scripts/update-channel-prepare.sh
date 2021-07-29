#https://hyperledger-fabric.readthedocs.io/en/release-2.2/config_update.html
set -ev

CH_NAME=mychannel
OUTPUT=$(dirname "$0")/update-channel
ORDERER=orderer.example.com:7050

rm -rf "$OUTPUT"
mkdir "$OUTPUT"

peer channel fetch config "$OUTPUT"/config_block.pb -o $ORDERER -c $CH_NAME

configtxlator proto_decode --input "$OUTPUT"/config_block.pb --type common.Block --output "$OUTPUT"/config_block.json

jq .data.data[0].payload.data.config  "$OUTPUT"/config_block.json >  "$OUTPUT"/config.json

cp "$OUTPUT"/config.json "$OUTPUT"/modified_config.json
