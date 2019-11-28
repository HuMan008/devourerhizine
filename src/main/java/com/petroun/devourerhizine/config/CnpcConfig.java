package com.petroun.devourerhizine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cnpc")
public class CnpcConfig {

    private boolean mock;

    @Override
    public String toString() {
        return "CnpcConfig{" +
                "mock=" + mock +
                '}';
    }

    public boolean isMock() {
        return mock;
    }

    public void setMock(boolean mock) {
        this.mock = mock;
    }
}
