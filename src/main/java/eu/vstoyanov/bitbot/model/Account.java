package eu.vstoyanov.bitbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class Account {

    public Account(BigDecimal cryptoBalance, BigDecimal fiatBalance) {
        this.cryptoBalance = cryptoBalance;
        this.fiatBalance = fiatBalance;
    }

    public BigDecimal getCryptoBalance() {
        return cryptoBalance;
    }

    public void setCryptoBalance(BigDecimal cryptoBalance) {
        this.cryptoBalance = cryptoBalance;
    }

    public BigDecimal getFiatBalance() {
        return fiatBalance;
    }

    public void setFiatBalance(BigDecimal fiatBalance) {
        this.fiatBalance = fiatBalance;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private BigDecimal cryptoBalance;
    private BigDecimal fiatBalance;
}
