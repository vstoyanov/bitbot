package eu.vstoyanov.bitbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderExecution {

    public OrderExecution(BigDecimal amountCrypto, BigDecimal amountFiat, OrderType orderType, Instant instant) {
        this.amountCrypto = amountCrypto;
        this.amountFiat = amountFiat;
        this.orderType = orderType;
        this.instant = instant;
    }

    public BigDecimal getAmountCrypto() {
        return amountCrypto;
    }

    public BigDecimal getAmountFiat() {
        return amountFiat;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Instant getInstant() {
        return instant;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private BigDecimal amountCrypto;
    private BigDecimal amountFiat;
    private OrderType orderType;
    private Instant instant;
}
