# Effective Hyper Ledger Fabric

HLF playground repository.

## Getting started

### docker-compose mode

1. Build chaincode:

`cd commercial-paper && ./gradlew shadowJar`

2. Build custom chaincode docker image:

`docker-compose build fabric-javaenv`

3. Start dev network:

`cd dev-network && ./start.sh`

4. Run commercial-paper/commercial-paper-app scripts.

### Manual mode

1. Download fabric binaries into the root of the repository:

`curl -sSL http://bit.ly/2ysbOFE | bash -s -- 1.4.12`

More info here: https://hyperledger-fabric.readthedocs.io/en/release-1.4/install.html.

2. Start dev network:

`cd dev-network && ./start-manually.sh`

3. Run java app code.
