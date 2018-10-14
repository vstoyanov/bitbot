package eu.vstoyanov.bitbot.it;

import eu.vstoyanov.bitbot.configuration.BitbotConfiguration;
import eu.vstoyanov.bitbot.model.Account;
import eu.vstoyanov.bitbot.model.OrderExecution;
import eu.vstoyanov.bitbot.test.MockScheduledExecutorService;
import eu.vstoyanov.bitbot.test.MutableClock;
import eu.vstoyanov.bitbot.test.exchange.DataSet;
import eu.vstoyanov.bitbot.test.exchange.DatasetBackedExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@EnableAutoConfiguration
public class BitbotIt {

    private static final Logger logger = LoggerFactory.getLogger(BitbotIt.class);
    private static final String LOCALHOST_URL_HTTP = "http://localhost:";

    private static final String STATE_ENDPOINT = "/state";
    private static final String ACCOUNT_BALANCE_ENDPOINT = "/account/balance";
    private static final String ORDER_LIST_ENDPOINT = "/account/orders";

    static final Integer RESPONSE_TIME = 15;

    @SpringBootConfiguration
    @ComponentScan(basePackages = "eu.vstoyanov.bitbot",
            excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "eu.vstoyanov.bitbot.Bitbot"))
    static class IntegrationTestConfig {

        @Bean
        @Profile("test")
        public DatasetBackedExchange datasetBackedExchange(DataSet dataSet, Clock clock) {
            return new DatasetBackedExchange(dataSet, clock);
        }

        @Bean
        public Clock clock(DataSet dataSet) {
            Instant startOfTest = dataSet.getNext().getInstant();
            return new MutableClock(startOfTest);
        }

        @Bean
        public ScheduledExecutorService scheduledExecutorService() {
            return new MockScheduledExecutorService();
        }

        @Bean
        public DataSet dataSet() {
            return DataSet.JPY;
        }
    }

    @Before
    public void initialize() {
    }

    @Test
    public void testJpyAllTime() {

        String bitbotUrl = LOCALHOST_URL_HTTP + this.port;

        ResponseEntity<Boolean> responseStatus = restTemplate.getForEntity(bitbotUrl + STATE_ENDPOINT, Boolean.class);

        assertEquals(false, responseStatus.getBody());


        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.put("state", Collections.singletonList(Boolean.TRUE));
        ResponseEntity<String> response = restTemplate.postForEntity(bitbotUrl + STATE_ENDPOINT, request, String.class);

        assertEquals("OK", response.getBody());


        responseStatus = restTemplate.getForEntity(bitbotUrl + STATE_ENDPOINT, Boolean.class);

        assertEquals(true, responseStatus.getBody());



        while(exchange.hasNext()) {

            mockScheduledExecutorService.forceRun();

            mockClock.rollForward(
                    Duration.ofMillis(configuration.getSleepBetweenCycles()));
        }

        request.put("state", Collections.singletonList(Boolean.FALSE));
        response = restTemplate.postForEntity(bitbotUrl + STATE_ENDPOINT, request, String.class);

        assertEquals("OK", response.getBody());


        responseStatus = restTemplate.getForEntity(bitbotUrl + STATE_ENDPOINT, Boolean.class);

        assertEquals(false, responseStatus.getBody());


        ResponseEntity<Account> account =
                restTemplate.getForEntity(bitbotUrl + ACCOUNT_BALANCE_ENDPOINT, Account.class);

        assertEquals(4.26708, account.getBody().getCryptoBalance().doubleValue(), 0.0001);
        assertEquals(0, account.getBody().getFiatBalance().doubleValue(), 0.0001);


        ResponseEntity<List<OrderExecution>> orders =
                restTemplate.exchange(
                        bitbotUrl + ORDER_LIST_ENDPOINT,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<OrderExecution>>(){});

        assertEquals(591, orders.getBody().size());
    }

    @Autowired
    private MockScheduledExecutorService mockScheduledExecutorService;

    @Autowired
    private MutableClock mockClock;

    @Autowired
    @Qualifier("datasetBackedExchange")
    private DatasetBackedExchange exchange;

    @LocalServerPort
    private int port;

    @Autowired
    private BitbotConfiguration configuration;

    private RestTemplate restTemplate = new RestTemplate();
}
