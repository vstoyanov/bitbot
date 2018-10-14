package eu.vstoyanov.bitbot.test.exchange;

import eu.vstoyanov.bitbot.exchange.Exchange;
import eu.vstoyanov.bitbot.model.Order;
import eu.vstoyanov.bitbot.model.OrderExecution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;

import static eu.vstoyanov.bitbot.test.exchange.DataSet.Entry;

/**
 *  It is a mocked exchange which acts as a proxy for the dataset supplied behind it.
 *
 *  An interesting point to notice is that as {@link java.util.ListIterator} doesn't have a peek method, so instead of
 *  next()/peek() pair we use current()/next() in the same way, keeping in mind that next() is still the mutating one.
 *
 */
public class DatasetBackedExchange implements Exchange {

    public DatasetBackedExchange(DataSet dataSet, Clock clock) {

        this.dataSet = dataSet;
        this.clock = clock;

        this.current = dataSet.getNext();
        this.next = dataSet.getNext();
    }

    @Override
    public BigDecimal pollPrice(String currency) {
        return getCurrent().getWeightedPrice();
    }

    @Override
    public OrderExecution placeOrder(Order order, String currency) {

        return new OrderExecution(order.getAmount(),
                order.getAmount().multiply(getCurrent().getWeightedPrice()).setScale(10, RoundingMode.HALF_EVEN),
                order.getType(),
                Instant.now(clock));
    }

    public boolean hasNext() {
        return dataSet.hasNext();
    }

    /**
     * Here happens some magic
     * The method effects the dataset
     * @return
     */
    private Entry getCurrent() {

        long now = clock.instant().toEpochMilli();

        long currentDelta = Math.abs(now - current.getInstant().toEpochMilli());
        long nextDelta = Math.abs(now - next.getInstant().toEpochMilli());

        while (nextDelta < currentDelta) {
            rollOver();
            currentDelta = Math.abs(now - current.getInstant().toEpochMilli());
            nextDelta = Math.abs(now - next.getInstant().toEpochMilli());
        }

        return current;
    }

    private void rollOver() {
        this.current = this.next;
        this.next = dataSet.getNext();
    }

    private final DataSet dataSet;
    private final Clock clock;

    private Entry next;
    private Entry current;
}