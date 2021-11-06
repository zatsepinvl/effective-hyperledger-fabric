package com.effective.hlf.hlf.commercialpaper;

import org.hyperledger.fabric.gateway.*;

import java.util.concurrent.TimeoutException;

public class CommercialPaperContractStub {
    private static final String CONTRACT_CHAINCODE_ID = "papercontract";
    private static final String CONTRACT_NAME = "org.papernet.commercialpaper";

    private final Contract contract;

    public CommercialPaperContractStub(String channel, Gateway gateway) {
        Network network = gateway.getNetwork(channel);
        this.contract = network.getContract(CONTRACT_CHAINCODE_ID, CONTRACT_NAME);
    }

    public CommercialPaper issue(String... args) throws ContractException, InterruptedException, TimeoutException {
        byte[] response = contract.submitTransaction("issue", args);
        Transaction transaction = contract.createTransaction("issue");
        return CommercialPaper.deserialize(response);
    }

    public CommercialPaper buy(String... args) throws ContractException, InterruptedException, TimeoutException {
        byte[] response = contract.submitTransaction("buy", args);
        return CommercialPaper.deserialize(response);
    }

    public CommercialPaper redeem(String... args) throws ContractException, InterruptedException, TimeoutException {
        byte[] response = contract.submitTransaction("redeem", args);
        return CommercialPaper.deserialize(response);
    }

    public CommercialPaper read(String... args) throws ContractException, InterruptedException, TimeoutException {
        byte[] response = contract.evaluateTransaction("read", args);
        return CommercialPaper.deserialize(response);
    }


}