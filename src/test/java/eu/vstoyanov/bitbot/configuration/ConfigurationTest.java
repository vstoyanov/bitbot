package eu.vstoyanov.bitbot.configuration;

import eu.vstoyanov.bitbot.Bitbot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Bitbot.class})
public class ConfigurationTest {

    @Autowired
    private Configuration configuration;

    @Test
    public void testGetFiatCurrency() {
        assertEquals(configuration.getFiatCurrency(), "CZK");
    }

    @Test
    public void testGetSleepBetweenCycles() {
        assertEquals(configuration.getSleepBetweenCycles().intValue(), 1000);
    }
}
