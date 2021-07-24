/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContract;
import com.effective.hlf.hlf.gateway.UserIdentity;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.network.BasicNetworkUsers;
import com.effective.hlf.hlf.network.BasicNetwork;
import com.effective.hlf.hlf.logging.LoggingUtils;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class Issue {

    private static final String CHANNEL_NAME = "mychannel";

    public static void main(String[] args) throws ContractException, InterruptedException, TimeoutException {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.DEBUG);

        UserIdentity isabellaUser = BasicNetworkUsers.getIsabellaUserIdentity();
        Path networkConfigFile = BasicNetwork.getNetworkConfigPath();

        TransactionEventManager eventManager = new TransactionEventManagerImpl();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(2, factory);
        Gateway gateway = gatewayPool.getGateway();

        CommercialPaperContract contract = new CommercialPaperContract(CHANNEL_NAME);
        CommercialPaper result = contract.issue(gateway, "MagnetoCorp", "00003", "2020-05-31", "2020-11-30", "5000000");
        System.out.println(result.toString());
    }

}
