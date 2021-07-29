#!/bin/bash

#https://hyperledger-fabric.readthedocs.io/en/release-2.2/config_update.html

set -ev

CH_NAME=mychannel
OUTPUT=$(dirname "$0")/update-channel
ORDERER=orderer.example.com:7050

configtxlator proto_encode --input "$OUTPUT"/config.json --type common.Config --output "$OUTPUT"/config.pb

configtxlator proto_encode --input "$OUTPUT"/modified_config.json --type common.Config --output "$OUTPUT"/modified_config.pb

configtxlator compute_update --channel_id $CH_NAME --original "$OUTPUT"/config.pb --updated "$OUTPUT"/modified_config.pb --output "$OUTPUT"/config_update.pb

configtxlator proto_decode --input "$OUTPUT"/config_update.pb --type common.ConfigUpdate --output "$OUTPUT"/config_update.json

echo '{"payload":{"header":{"channel_header":{"channel_id":"'$CH_NAME'", "type":2}},"data":{"config_update":'$(cat "$OUTPUT"/config_update.json)'}}}' | jq . > "$OUTPUT"/config_update_in_envelope.json

configtxlator proto_encode --input "$OUTPUT"/config_update_in_envelope.json --type common.Envelope --output "$OUTPUT"/config_update_in_envelope.pb

peer channel update -f "$OUTPUT"/config_update_in_envelope.pb -c $CH_NAME -o $ORDERER