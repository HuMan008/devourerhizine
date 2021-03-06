package com.petroun.devourerhizine.provider.gt;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="gtconfig")
public class GTConfig {
    private String url;
    private String copartnerId;
    private String copartnerPassword;
    private String headNumber;
}
