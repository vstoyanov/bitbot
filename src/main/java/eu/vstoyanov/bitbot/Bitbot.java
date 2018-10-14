package eu.vstoyanov.bitbot;

import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;
import eu.vstoyanov.bitbot.configuration.BitbotConfiguration;
import eu.vstoyanov.bitbot.exchange.CoinbaseAdapter;
import eu.vstoyanov.bitbot.exchange.Exchange;
import eu.vstoyanov.bitbot.web.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.Clock;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "eu.vstoyanov.bitbot.web.*"))
@SpringBootApplication
public class Bitbot {

    private static final Logger logger = LoggerFactory.getLogger(Bitbot.class);

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{Bitbot.class, WebConfig.class}, args);
    }

    @Bean
    public Exchange coinbase(BitbotConfiguration configuration, Clock clock) {
        Coinbase coinbase = new CoinbaseBuilder()
                .withApiKey(System.getenv(configuration.getApiKey()), configuration.getApiSecret())
                .build();
        return new CoinbaseAdapter(coinbase, clock);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
