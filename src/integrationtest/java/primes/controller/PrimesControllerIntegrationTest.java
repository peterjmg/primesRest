package primes.controller;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import primes.config.WebConfig;
import primes.domain.Primes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class PrimesControllerIntegrationTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetPrimes10() throws Exception {

        String result = restTemplate.getForObject(
                "http://localhost:8080/primes/10", String.class);

        JSONObject jsonObject = new JSONObject(result);

        System.out.println("Initial: [" + jsonObject.get("initial") + "]");
        System.out.println("Primes: [" + jsonObject.get("primes") + "]");

        System.out.println(result);
    }

    @Test
    public void testGetPrimes100() throws Exception {

        Primes primes = restTemplate.getForObject(
                "http://localhost:8080/primes/100.json", Primes.class);

        System.out.println(String.format("JSON result: Initial %,d. Values [%s]",
                primes.getInitial(), primes.getPrimes().toString()));
    }

    @Test
    public void testGetPrimes100UsingSieve() throws Exception {

        Primes primes = restTemplate.getForObject(
                "http://localhost:8080/primes/100.json?opt=S", Primes.class);

        System.out.println(String.format("JSON result: Initial %,d. Values [%s]",
                primes.getInitial(), primes.getPrimes().toString()));
    }

    @Test
    public void testGetPrimes1000UsingStream() throws Exception {

        Primes primes = restTemplate.getForObject(
                "http://localhost:8080/primes/1000.json?opt=DS", Primes.class);

        System.out.println(String.format("JSON result: Initial %,d. Values [%s]",
                primes.getInitial(), primes.getPrimes().toString()));
    }

    @Test
    public void testInvalidOption() throws Exception {

        try {
            restTemplate.getForObject("http://localhost:8080/primes/10.json?opt=OOO", Primes.class);
            throw new AssertionError("Invalid option test failed");
        } catch (HttpClientErrorException exception) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        }
    }
}