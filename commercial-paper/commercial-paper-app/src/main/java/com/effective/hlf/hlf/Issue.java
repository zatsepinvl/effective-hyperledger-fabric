/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContractStub;
import com.effective.hlf.hlf.wallet.UserIdentity;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.network.BasicNetwork;
import com.effective.hlf.hlf.logging.LoggingUtils;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;

import java.nio.file.Path;
import java.util.concurrent.*;

public class Issue {

    private static final String CHANNEL_NAME = "mychannel";

    public static void main(String[] args) throws ContractException, InterruptedException, TimeoutException, BrokenBarrierException {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.WARN);

        UserIdentity isabellaUser = NetworkUsers.getIsabellaUserIdentity();
        Path networkConfigFile = BasicNetwork.getNetworkConfigPath();

        SingleNetworkTransactionEventManager eventManager = new SingleNetworkTransactionEventManager();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway = gatewayPool.getGateway();
        Network network = gateway.getNetwork(CHANNEL_NAME);
        eventManager.startListening(network);

        CommercialPaperContractStub contract = new CommercialPaperContractStub(CHANNEL_NAME, gateway);
        CommercialPaper result = contract.issue("MagnetoCorp", "001", "2020-05-31", "2020-11-30", "5000000");
        System.out.println(result.toString());
        System.exit(0);
    }

}
