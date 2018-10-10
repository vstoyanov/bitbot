package eu.vstoyanov.bitbot.core;

import eu.vstoyanov.bitbot.configuration.Configuration;
import eu.vstoyanov.bitbot.exchange.ExchangeException;
import eu.vstoyanov.bitbot.service.TradingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The TradingEngine takes care of maintaining the tick cycle, ensuring the trading service is called at each cycle.
 * It also handles errors - one-time communication failures are logged and the tick is dropped, more serious issues
 * would bring the engine down.
 *
 */
@Component
public class TradingEngine {

    private static final Logger logger = LoggerFactory.getLogger(TradingEngine.class);

    @Autowired
    public TradingEngine(TradingService tradingService, Configuration configuration) {

        this.tradingService = tradingService;
        this.configuration = configuration;

        executor = Executors.newScheduledThreadPool(1);
        tick = this::tick;
    }

    public synchronized void start() {

        if (!running) {

            tickFuture = executor.scheduleAtFixedRate(tick, 0, configuration.getSleepBetweenCycles(), TimeUnit.MILLISECONDS);
            running = true;

        } else {
            logger.warn("Attempted to start the trading engine while it was already running");
        }
    }

    public synchronized void stop() {

        if (running) {

            tickFuture.cancel(true);
            running = false;

        } else {
            logger.warn("Attempted to stop the trading engine whitout it running");
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    private void tick() {
        try {
            tradingService.tick();
        } catch (ExchangeException e) {
            logger.error("There was a problem communicating with the exchange", e);
        }
    }

    private final TradingService tradingService;
    private final Configuration configuration;

    private final ScheduledExecutorService executor;
    private final Runnable tick;

    private ScheduledFuture<?> tickFuture;

    private boolean running;
}
