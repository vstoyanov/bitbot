package eu.vstoyanov.bitbot.exchange;

import com.coinbase.api.Coinbase;
import com.coinbase.api.entity.Transfer;
import com.coinbase.api.exception.CoinbaseException;
import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.model.OrderType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

public class CoinbaseAdapter implements Exchange {

    private static final Logger logger = LoggerFactory.getLogger(CoinbaseAdapter.class);

    private static final String EXCHANGE_RATE_PREFIX = "btc_to_";

    public CoinbaseAdapter(Coinbase coinbase, Clock clock) {
        this.coinbase = coinbase;
        this.clock = clock;
    }

    @Override
    public BigDecimal pollPrice(String currency) {

        try {

            var exchangeRates = this.coinbase.getExchangeRates();
            BigDecimal currentPrice = exchangeRates.get(EXCHANGE_RATE_PREFIX + currency.toLowerCase());

            logger.info("Received an exchange rate update: {}", currentPrice);

            return currentPrice;

        } catch (IOException | CoinbaseException e) {
            logger.error("Error while polling price");
            throw new ExchangeException("Error while polling price", e);
        }
    }

    @Override
    public OrderExecution placeOrder(Order order, String currency) {

        Money toOrder = Money.of(CurrencyUnit.of(currency), order.getAmount());

        try {
            Transfer transfer;

            if (order.getType() == OrderType.BUY) {
                transfer = coinbase.buy(toOrder);
            } else {
                transfer = coinbase.sell(toOrder);
            }

            OrderExecution orderExecution =
                    new OrderExecution(transfer.getBtc().getAmount(),
                                        transfer.getTotal().getAmount(),
                                        order.getType(),
                                        Instant.now(clock));

            logger.info("Executed an order: %s", orderExecution);

            return orderExecution;

        } catch (IOException | CoinbaseException e) {
            logger.error("Error while placing an order");
            throw new ExchangeException("Error while placing an order", e);
        }
    }

    private Coinbase coinbase;

    private Clock clock;

}
