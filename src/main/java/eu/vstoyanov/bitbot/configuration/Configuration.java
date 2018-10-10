package eu.vstoyanov.bitbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(Configuration.CONFIGURATION_FILENAME)
public class Configuration {
    static final String CONFIGURATION_FILENAME = "classpath:/configuration.properties";

    @Value("${trading.fiatCurrency}")
    private String fiatCurrency;

    @Value("${trading.sleepBetweenCycles}")
    private Integer sleepBetweenCycles;

    @Value("${exchange.apiKey}")
    private String apiSecret;

    @Value("${exchange.apiSecret}")
    private String apiKey;

    public String getFiatCurrency() {
        return fiatCurrency;
    }

    public Integer getSleepBetweenCycles() {
        return sleepBetweenCycles;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getApiKey() {
        return apiKey;
    }
}
