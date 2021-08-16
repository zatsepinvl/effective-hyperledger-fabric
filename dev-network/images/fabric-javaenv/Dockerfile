#https://github.com/hyperledger/fabric-chaincode-java/blob/release-1.4/fabric-chaincode-docker/Dockerfile
FROM hyperledger/fabric-javaenv:1.4.8

COPY build.sh /root/chaincode-java/build.sh
COPY start.sh /root/chaincode-java/start.sh

RUN chmod +x /root/chaincode-java/build.sh
RUN chmod +x /root/chaincode-java/start.sh

#java debug socket
EXPOSE 5005