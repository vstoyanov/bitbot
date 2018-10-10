package eu.vstoyanov.bitbot.service;

import eu.vstoyanov.bitbot.model.Account;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.model.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes care of the account balance and the order history
 *
 * Could be extended with some sort of persistence. Also could retrieve initial balances from the exchange and support
 * multiple exchanges
 *
 */
@Component
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public void registerOrder(OrderExecution orderExecution) {

        if (orderExecution.getOrderType() == OrderType.BUY) {

            account.setCryptoBalance(
                    account.getCryptoBalance().add(orderExecution.getAmountCrypto()));

            account.setFiatBalance(
                    account.getFiatBalance().subtract(orderExecution.getAmountFiat()));

        } else {

            account.setCryptoBalance(
                    account.getCryptoBalance().subtract(orderExecution.getAmountCrypto()));

            account.setFiatBalance(
                    account.getFiatBalance().add(orderExecution.getAmountFiat()));

        }

        orderExecutions.add(orderExecution);

    }

    public Account getAccount() {
        return account;
    }

    public List<OrderExecution> getOrderExecutions() {
        return orderExecutions;
    }

    private Account account;

    private List<OrderExecution> orderExecutions = new ArrayList<>();
}
