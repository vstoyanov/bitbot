package eu.vstoyanov.bitbot.strategy;

import eu.vstoyanov.bitbot.model.Order;

import java.time.Instant;

public interface TradingStrategy {
    Order tick(Instant timestamp, double price);
}