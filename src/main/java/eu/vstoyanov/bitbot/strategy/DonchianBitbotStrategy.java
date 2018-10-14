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
 * The Donchian reference trading strategy.
 *
 * Keeps track of the minimum and maximum value for last 23 periods.
 * Trades everything when current price breaks through the lower and upper
 * bounds of this channel.
 *
 */
@Component
public class DonchianBitbotStrategy implements TradingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBitbotStrategy.class);

    @Autowired
    public DonchianBitbotStrategy(MarketData marketData) {
        this.marketData = marketData;
    }

    @Override
    public Order tick(Instant timestamp, double price) {

        marketData.record(timestamp, price);

        if (marketData.didUpdate()) {

            computeChannel();

            double upperChannel = minPrice + channelSize * 0.75;
            double lowerChannel = minPrice + channelSize * 0.25;

            if (price > upperChannel) {
                return new Order(OrderType.BUY, new BigDecimal(Double.MAX_VALUE));
            } else if (price < lowerChannel) {
                return new Order(OrderType.SELL, new BigDecimal(Double.MAX_VALUE));
            }
        }

        return new Order(OrderType.BUY, new BigDecimal(0));
    }

    private void computeChannel() {
        double[] hourlyMeans = marketData.getHourlyMeans();

        for (int i = 0; i < hourlyMeans.length; i++) {
            minPrice = Math.min(minPrice, hourlyMeans[i]);
            maxPrice = Math.max(maxPrice, hourlyMeans[i]);
        }
        channelSize = maxPrice - minPrice;
    }

    private final MarketData marketData;
    private double minPrice = Double.MAX_VALUE;
    private double maxPrice = Double.MIN_VALUE;
    private double channelSize = 0;
}
