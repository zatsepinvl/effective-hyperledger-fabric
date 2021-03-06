package com.effective.hlf.hlf.gateway;

import org.hyperledger.fabric.gateway.Gateway;

import java.io.IOException;

public interface GatewayFactory {
    Gateway createGateway() throws IOException;
}
