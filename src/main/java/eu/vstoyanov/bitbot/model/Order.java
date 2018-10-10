package eu.vstoyanov.bitbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class Order {

    public Order(OrderType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public OrderType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private final OrderType type;

    private final BigDecimal amount;
}
