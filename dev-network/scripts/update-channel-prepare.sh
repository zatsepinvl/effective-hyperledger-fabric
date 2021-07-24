#https://hyperledger-fabric.readthedocs.io/en/release-2.2/config_update.html
set -ev

export CH_NAME=mychannel
export OUTPUT=update-channel
export ORDERER=orderer.example.com:7050

rm -rf update-channel
mkdir update-channel

peer channel fetch config ${OUTPUT}/config_block.pb -o $ORDERER -c $CH_NAME

configtxlator proto_decode --input ${OUTPUT}/config_block.pb --type common.Block --output ${OUTPUT}/config_block.json

jq .data.data[0].payload.data.config  ${OUTPUT}/config_block.json >  ${OUTPUT}/config.json

cp ${OUTPUT}/config.json ${OUTPUT}/modified_config.json
