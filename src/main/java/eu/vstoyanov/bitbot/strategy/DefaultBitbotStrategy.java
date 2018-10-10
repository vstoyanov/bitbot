package eu.vstoyanov.bitbot.strategy;

import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderType;
import eu.vstoyanov.bitbot.strategy.marketdata.MarketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * The default reference trading strategy.
 *
 * It is supposed to be extremely simple - keeps track of the last 24 hour hourly averages and invests everything in
 * crypto when the price drop velocity has declined under a given threshold.
 *
 * On the contrary, when the price rise velocity has declined under a threshold it sells everything.
 *
 */
@Component
public class DefaultBitbotStrategy implements TradingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBitbotStrategy.class);

    @Autowired
    public DefaultBitbotStrategy(MarketData marketData) {
        this.marketData = marketData;
    }

    @Override
    public Order tick(Instant timestamp, double price) {

        marketData.record(timestamp, price);

        if (marketData.didUpdate()) {

            computeVelocities();

            double lastVelocity = priceVelocities[22];

            if ( 0.985 < lastVelocity && lastVelocity < 0.991) {
                return new Order(OrderType.BUY, new BigDecimal(Double.MAX_VALUE));
            } else if ( 1.005 < lastVelocity && lastVelocity < 1.010)
                return new Order(OrderType.SELL, new BigDecimal(Double.MAX_VALUE));
        }

        return new Order(OrderType.BUY, new BigDecimal(0));
    }

    private void computeVelocities() {
        double[] hourlyMeans = marketData.getHourlyMeans();

        for (int i = 0; i < priceVelocities.length; i++) {
            priceVelocities[i] = hourlyMeans[i + 1]/hourlyMeans[i];
        }
    }

    private final MarketData marketData;
    private double[] priceVelocities = new double[23];
}
