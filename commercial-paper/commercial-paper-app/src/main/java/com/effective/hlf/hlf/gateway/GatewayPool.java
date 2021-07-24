package com.effective.hlf.hlf.gateway;

import org.hyperledger.fabric.gateway.Gateway;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface GatewayPool {
    Gateway getGateway();
}
