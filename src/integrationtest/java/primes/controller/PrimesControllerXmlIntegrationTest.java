package primes.controller;

import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import primes.config.WebConfig;
import primes.domain.Primes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class PrimesControllerXmlIntegrationTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetPrimes20Xml() throws Exception {

        String result = restTemplate.getForObject(
                "http://localhost:8080/primes/20.xml", String.class);

        XmlPath xmlPath = XmlPath.from(result);

        System.out.println("Initial: [" + xmlPath.get("primes.initial") + "]");
        System.out.println("Primes: [" + xmlPath.getList("primes.primes") + "]");

        System.out.println(result);
    }

    @Test
    public void testGetPrimes51Xml() throws Exception {

        Primes primes = restTemplate.getForObject(
                "http://localhost:8080/primes/51.xml", Primes.class);

        System.out.println(String.format("XML result: Initial %,d. Values [%s]",
                primes.getInitial(), primes.getPrimes().toString()));
    }
}