package com.effective.hlf.hlf.network;

import com.effective.hlf.hlf.resource.ResourceUtils;

import java.nio.file.Path;

public class DevNetwork {
    public static final String CHANNEL_NAME = "mychannel";

    public static Path getNetworkConfigPath() {
        return ResourceUtils.getResourcePath("gateway/networkConnection.yaml");
    }
}
