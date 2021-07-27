package com.effective.hlf.hlf.commercialpaper;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;

import java.util.concurrent.TimeoutException;

public class CommercialPaperContract {
    private static final String CONTRACT_CHAINCODE_ID = "papercontract";
    private static final String CONTRACT_NAME = "org.papernet.commercialpaper";

    private final String channel;
    private final Gateway gateway;

    public CommercialPaperContract(String channel, Gateway gateway) {
        this.channel = channel;
        this.gateway = gateway;
    }

    public CommercialPaper issue(String... args) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = getContract();
        byte[] response = contract.submitTransaction("issue", args);
        return CommercialPaper.deserialize(response);
    }

    public CommercialPaper buy(String... args) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = getContract();
        byte[] response = contract.submitTransaction("buy", args);
        return CommercialPaper.deserialize(response);
    }

    public Contract getContract() {
        Network network = gateway.getNetwork(channel);
        return network.getContract(CONTRACT_CHAINCODE_ID, CONTRACT_NAME);
    }
}
