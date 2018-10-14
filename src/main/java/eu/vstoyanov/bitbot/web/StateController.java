package eu.vstoyanov.bitbot.web;

import eu.vstoyanov.bitbot.core.TradingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
public class StateController {

    private static final Logger logger = LoggerFactory.getLogger(StateController.class);

    @RequestMapping(method = RequestMethod.GET)
    public Boolean getState() {
        return tradingEngine.isRunning();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String setState(@RequestParam("state") boolean state) {

        if (state == tradingEngine.isRunning()) {
            return "No action required. Already in that state";
        }

        if (state) {
            tradingEngine.start();
        } else {
            tradingEngine.stop();
        }

        return "OK";
    }

    @Autowired
    private TradingEngine tradingEngine;
}
