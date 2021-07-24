package com.effective.hlf.hlf.resource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourceUtils {
    public static Path getResourcePath(String resource) {
        try {
            return Paths.get(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource(resource)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
