/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContract;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.network.BasicNetworkUsers;
import com.effective.hlf.hlf.logging.LoggingUtils;
import com.effective.hlf.hlf.network.BasicNetwork;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class Buy {

    private static final String CHANNEL_NAME = "mychannel";

    public static void main(String[] args) throws InterruptedException, ContractException, TimeoutException {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.INFO);

        UserIdentity isabellaUser = BasicNetworkUsers.getBalajiUserIdentity();
        Path networkConfigFile = BasicNetwork.getNetworkConfigPath();

        TransactionEventManager eventManager = new TransactionEventManagerImpl();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway = gatewayPool.getGateway();

        CommercialPaperContract contract = new CommercialPaperContract(CHANNEL_NAME);
        CommercialPaper result = contract.buy(gateway, "MagnetoCorp", "00003", "MagnetoCorp", "DigiBank", "4900000", "2020-05-31");
        System.out.println(result.toString());
    }

}
