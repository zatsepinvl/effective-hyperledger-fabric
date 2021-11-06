/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaper;
import com.effective.hlf.hlf.commercialpaper.CommercialPaperContractStub;
import com.effective.hlf.hlf.gateway.GatewayFactory;
import com.effective.hlf.hlf.gateway.GatewayFactoryDefault;
import com.effective.hlf.hlf.logging.LoggingUtils;
import com.effective.hlf.hlf.network.DevNetwork;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.wallet.UserIdentity;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.Gateway;

import java.nio.file.Path;

public class Issue {

    private static final String CHANNEL_NAME = "mychannel";

    public static void main(String[] args) throws Exception {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.WARN);

        UserIdentity isabellaUser = NetworkUsers.getIsabellaUserIdentity();
        Path networkConfigFile = DevNetwork.getNetworkConfigPath();

        GatewayFactory factory = new GatewayFactoryDefault(isabellaUser, networkConfigFile);
        Gateway gateway = factory.createGateway();

        CommercialPaperContractStub contract = new CommercialPaperContractStub(CHANNEL_NAME, gateway);
        CommercialPaper result = contract.issue("MagnetoCorp", "001", "2020-05-31", "2020-11-30", "5000000");
        System.out.println(result.toString());
    }

}