     _______   __    __      __                   __     
    |       \ |  \  |  \    |  \                 |  \    
    | $$$$$$$\ \$$ _| $$_   | $$____    ______  _| $$_   
    | $$__/ $$|  \|   $$ \  | $$    \  /      \|   $$ \  
    | $$    $$| $$ \$$$$$$  | $$$$$$$\|  $$$$$$\\$$$$$$  
    | $$$$$$$\| $$  | $$ __ | $$  | $$| $$  | $$ | $$ __ 
    | $$__/ $$| $$  | $$|  \| $$__/ $$| $$__/ $$ | $$|  \
    | $$    $$| $$   \$$  $$| $$    $$ \$$    $$  \$$  $$
     \$$$$$$$  \$$    \$$$$  \$$$$$$$   \$$$$$$    \$$$$ 
                                                                                                               
# Bitbot

A simple low-frequency synchronous Bitcoin trading bot. So far, it is automated only at exchange-level and supports only
Coinbase. It uses the Coinbase API to query for BTC price taking **last price** and to place orders.

The biggest advantage are the massive historical data sets in the test data, which allow for a precise historical 
evaluation of any strategy.

So far there is only a dummy strategy provided.

## Usage

Explain how to start spring boot. Or come up with 

## Configuration

Configuration is located in configuration.properties:

 * trading.fiatCurrency - the fiat currency which to trade against btc
 * trading.sleepBetweenCycles - the sleep between cycles/ticks in ms
 * exchange.apiKey - so far only coinbase
 * exchange.apiSecret - so far only coinbase

## Design

The trading engine polls the Exchange for the rates, then provides them to the Strategy and submit any potential Orders
to the Exchange. The cycle repeats at a specified interval.

## TODO

1. Some more practical and real strategy - Donchian, Moving averages, Fading.
2. Arbitrage trading between different exchanges.
3. Make it more asynchronous and concurrent. Multiple strategies in the same VM?
4. Two tests 
 * infrastructure (TradingService) - almost done
 * strategy vs both holds 
5. Persistence - future
6, Docker file
7. License