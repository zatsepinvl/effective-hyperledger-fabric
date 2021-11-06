package com.effective.hlf.hlf.gateway;

import com.effective.hlf.hlf.wallet.UserIdentity;
import org.hyperledger.fabric.gateway.DefaultCommitHandlers;
import org.hyperledger.fabric.gateway.Gateway;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class GatewayFactoryDefault implements GatewayFactory {
    private final UserIdentity user;
    private final Path networkConfigFile;

    public GatewayFactoryDefault(UserIdentity user, Path networkConfigFile) {
        this.user = user;
        this.networkConfigFile = networkConfigFile;
    }

    @Override
    public Gateway createGateway() throws IOException {
        return Gateway.createBuilder()
                .identity(user.getWallet(), user.getUsername())
                .networkConfig(networkConfigFile)
                .commitTimeout(30, TimeUnit.SECONDS)
                .discovery(false)
                .commitHandler(DefaultCommitHandlers.MSPID_SCOPE_ANYFORTX)
                .connect();
    }
}