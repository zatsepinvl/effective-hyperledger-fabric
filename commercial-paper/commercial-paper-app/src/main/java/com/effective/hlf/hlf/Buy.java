/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContractStub;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.logging.LoggingUtils;
import com.effective.hlf.hlf.network.DevNetwork;
import com.effective.hlf.hlf.wallet.UserIdentity;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import static com.effective.hlf.hlf.network.DevNetwork.CHANNEL_NAME;

public class Buy {

    public static void main(String[] args) throws InterruptedException, ContractException, TimeoutException {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.INFO);

        UserIdentity isabellaUser = NetworkUsers.getBalajiUserIdentity();
        Path networkConfigFile = DevNetwork.getNetworkConfigPath();

        TransactionEventManager eventManager = new TransactionEventManagerImpl();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway = gatewayPool.getGateway();

        CommercialPaperContractStub contract = new CommercialPaperContractStub(CHANNEL_NAME, gateway);
        CommercialPaper result = contract.buy( "MagnetoCorp", "00003", "MagnetoCorp", "DigiBank", "4900000", "2020-05-31");
        System.out.println(result.toString());
    }

}
