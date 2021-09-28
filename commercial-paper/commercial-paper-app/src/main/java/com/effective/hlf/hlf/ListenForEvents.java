package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContractStub;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.network.DevNetwork;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.wallet.UserIdentity;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;

import java.nio.file.Path;

public class ListenForEvents {
    public static void main(String[] args) throws InterruptedException {
        UserIdentity isabellaUser = NetworkUsers.getBalajiUserIdentity();
        Path networkConfigFile = DevNetwork.getNetworkConfigPath();

        TransactionEventManager eventManager = new TransactionEventManagerImpl();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway = gatewayPool.getGateway();
        Network network = gateway.getNetwork(DevNetwork.CHANNEL_NAME);
        Contract contract = network.getContract(CommercialPaperContractStub.CONTRACT_CHAINCODE_ID, CommercialPaperContractStub.CONTRACT_NAME);

        network.addBlockListener(blockEvent -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + ": " + blockEvent.getBlockNumber());
        });

        contract.addContractListener(contractEvent -> {
            System.out.print("Contract name: " + contractEvent.getName());
            contractEvent.getPayload().ifPresent(p -> System.out.println(", Payload: " + CommercialPaper.deserialize(p)));
        });

        Thread.sleep(1_000_000_000);
    }
}
