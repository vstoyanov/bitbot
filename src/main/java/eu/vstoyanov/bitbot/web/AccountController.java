package eu.vstoyanov.bitbot.web;

import eu.vstoyanov.bitbot.model.Account;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping(path = "balance", method = RequestMethod.GET)
    public Account getBalance() {
        return accountService.getAccount();
    }

    @RequestMapping(path = "orders", method = RequestMethod.GET)
    public List<OrderExecution> orders() {
        return accountService.getOrderExecutions();
    }

    @Autowired
    private AccountService accountService;
}