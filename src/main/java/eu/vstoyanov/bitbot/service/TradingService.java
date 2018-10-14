package eu.vstoyanov.bitbot.service;

import eu.vstoyanov.bitbot.configuration.BitbotConfiguration;
import eu.vstoyanov.bitbot.exchange.Exchange;
import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.strategy.TradingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

/**
 * This is where pretty much everything comes together. Called at every tick by the engine it queries the
 * {@link Exchange} for the current rate, passes it to the {@link TradingStrategy} for evaluation and registers the order
 * execution info with {@link AccountService}
 *
 */
@Component
public class TradingService {

    private static final Logger logger = LoggerFactory.getLogger(TradingService.class);

    @Autowired
    public TradingService(TradingStrategy strategy, Exchange exchange, BitbotConfiguration configuration,
                          AccountService accountService, Clock clock) {

        this.strategy = strategy;
        this.exchange = exchange;
        this.configuration = configuration;
        this.accountService = accountService;
        this.clock = clock;
    }

    public void tick() {

        BigDecimal lastPrice = exchange.pollPrice(configuration.getFiatCurrency());

        Order toExecute = strategy.tick(Instant.now(clock), lastPrice);

        if (toExecute.getAmount().compareTo(BigDecimal.ZERO) != 0) {
            logger.info("Placing an order: {}", toExecute);

            OrderExecution orderExecution = exchange.placeOrder(toExecute, configuration.getFiatCurrency());

            accountService.registerOrder(orderExecution);

        }

    }

    private TradingStrategy strategy;
    private Exchange exchange;

    private BitbotConfiguration configuration;
    private AccountService accountService;

    private Clock clock;
}