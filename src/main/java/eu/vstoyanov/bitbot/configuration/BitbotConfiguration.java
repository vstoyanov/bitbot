package eu.vstoyanov.bitbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@PropertySource(BitbotConfiguration.CONFIGURATION_FILENAME)
public class BitbotConfiguration {
    static final String CONFIGURATION_FILENAME = "classpath:/configuration.properties";

    @Value("${trading.fiatCurrency}")
    private String fiatCurrency;

    @Value("${trading.sleepBetweenCycles}")
    private Integer sleepBetweenCycles;

    @Value("${exchange.apiKey}")
    private String apiSecret;

    @Value("${exchange.apiSecret}")
    private String apiKey;

    @Value("${account.balance.starting.crypto}")
    private BigDecimal startingCryptoBalance;

    @Value("${account.balance.starting.fiat}")
    private BigDecimal startingFiatBalance;

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

    public BigDecimal getStartingCryptoBalance() {
        return startingCryptoBalance;
    }

    public BigDecimal getStartingFiatBalance() {
        return startingFiatBalance;
    }
}
