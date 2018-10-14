package eu.vstoyanov.bitbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderExecution {

    public OrderExecution() {}

    public OrderExecution(BigDecimal amountCrypto, BigDecimal amountFiat, OrderType orderType, Instant instant) {
        this.amountCrypto = amountCrypto;
        this.amountFiat = amountFiat;
        this.orderType = orderType;
        this.instant = instant;
    }

    public BigDecimal getAmountCrypto() {
        return amountCrypto;
    }

    public void setAmountCrypto(BigDecimal amountCrypto) {
        this.amountCrypto = amountCrypto;
    }

    public BigDecimal getAmountFiat() {
        return amountFiat;
    }

    public void setAmountFiat(BigDecimal amountFiat) {
        this.amountFiat = amountFiat;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
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
