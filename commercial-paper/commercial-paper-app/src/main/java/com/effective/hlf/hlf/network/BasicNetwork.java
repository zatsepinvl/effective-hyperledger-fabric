package com.effective.hlf.hlf.network;

import com.effective.hlf.hlf.resource.ResourceUtils;

import java.nio.file.Path;

public class BasicNetwork {
    public static Path getNetworkConfigPath() {
        return ResourceUtils.getResourcePath("gateway/networkConnection.yaml");
    }
}
