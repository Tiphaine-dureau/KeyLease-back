package com.infeco.keylease.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("key-lease.config")
public class KeyLeaseConfiguration {
    private String corsAllowedOrigin;

    public String getCorsAllowedOrigin() {
        return corsAllowedOrigin;
    }

    public void setCorsAllowedOrigin(String corsAllowedOrigin) {
        this.corsAllowedOrigin = corsAllowedOrigin;
    }
}
