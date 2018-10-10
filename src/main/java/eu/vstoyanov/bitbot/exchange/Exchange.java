package eu.vstoyanov.bitbot.exchange;

import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderExecution;

import java.math.BigDecimal;

/**
 * The underlying exchange with which the bot is to trade
 */
public interface Exchange {

    /**
     * Returns the current price of BTC at the
     * @param currency the currency for which to get BTC rate
     * @return
     */
    BigDecimal pollPrice(String currency);

    /**
     * Places an order
     * @param order the order to be executed
     * @param currency the currency in which BTC is to be bought
     * @return information on the actual order execution
     */
    OrderExecution placeOrder(Order order, String currency);
}
