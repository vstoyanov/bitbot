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

    @RequestMapping(name = "/balance", method = RequestMethod.GET)
    public Account getBalance() {
        return accountService.getAccount();
    }

    @RequestMapping(name = "/orders", method = RequestMethod.POST)
    public List<OrderExecution> setState() {
        return accountService.getOrderExecutions();
    }

    @Autowired
    private AccountService accountService;
}
