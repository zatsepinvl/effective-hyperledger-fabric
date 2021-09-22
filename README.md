# Effective Hyper Ledger Fabric

HLF playground repository.

## Getting started

### docker-compose mode

1. Build chaincode:

`cd commercial-paper && ./gradlew shadowJar`

2. Build custom chaincode docker image:

`cd dev-network && docker-compose build fabric-javaenv`

3. Start dev network:

`cd dev-network && ./start.sh`

4. Run commercial-paper/commercial-paper-app scripts.
