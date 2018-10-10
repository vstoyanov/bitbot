package eu.vstoyanov.bitbot.service;

import eu.vstoyanov.bitbot.configuration.Configuration;
import eu.vstoyanov.bitbot.exchange.Exchange;
import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.strategy.TradingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TradingService(TradingStrategy strategy, Exchange exchange, Configuration configuration,
                          AccountService accountService, Clock clock) {

        this.strategy = strategy;
        this.exchange = exchange;
        this.configuration = configuration;
        this.accountService = accountService;
        this.clock = clock;
    }

    public void tick() {

        BigDecimal lastPrice = exchange.pollPrice(configuration.getFiatCurrency());

        Order toExecute = strategy.tick(Instant.now(clock), lastPrice.doubleValue());

        if (!toExecute.getAmount().equals(BigDecimal.ZERO)) {
            logger.info("Placing an order: %s", toExecute);

            OrderExecution orderExecution = exchange.placeOrder(toExecute, configuration.getFiatCurrency());

            accountService.registerOrder(orderExecution);

        }

    }

    private TradingStrategy strategy;
    private Exchange exchange;

    private Configuration configuration;
    private AccountService accountService;

    private Clock clock;
}