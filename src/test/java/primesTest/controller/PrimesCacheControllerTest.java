package primesTest.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.response.MockMvcResponse;
import com.jayway.restassured.path.xml.XmlPath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.web.context.WebApplicationContext;
import primes.config.WebConfig;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class PrimesCacheControllerTest
{
    private final Integer[] primes10 = new Integer[] { 2, 3, 5, 7 };
    private final Integer[] primes100 = new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
            37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void primes0Test()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/0").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(0)).
                body("primes", hasSize(0));
    }

    @Test
    public void primes2Test()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/2").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(2)).
                body("primes", hasSize(1)).
                body("primes", hasItem(2));
    }

    @Test
    public void primes10Test()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/10").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(10)).
                body("primes", hasSize(primes10.length)).
                body("primes", contains(primes10));
    }

    @Test
    public void primes100Test()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/100").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(100)).
                body("primes", hasSize(primes100.length)).
                body("primes", contains(primes100));
    }

    @Test
    public void primes10XmlTest()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/10.xml").
                then().
                statusCode(200).
                contentType(ContentType.XML).
                body("primes.initial", equalTo("10"));

        XmlPath xmlPath = RestAssuredMockMvc.given().
                when().
                get("/primesCache/10.xml").
                andReturn().xmlPath();

        int initial = xmlPath.getInt("primes.initial");
        Assert.assertEquals(10, initial);

        List<Integer> primes = xmlPath.getList("primes.primes", Integer.class);
        Assert.assertEquals(4, primes.size());
        Assert.assertTrue(primes.contains(3));
        Assert.assertTrue(primes.contains(5));
        Assert.assertTrue(primes.contains(2));
        Assert.assertTrue(primes.contains(7));
    }

    @Test
    public void primes0SieveTest()
    {
        MockMvcResponse response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int hitCount = response.path("hitCount");
        int missCount = response.path("missCount");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/0?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(0)).
                body("primes", hasSize(0));

        response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int newHitCount = response.path("hitCount");
        int newMissCount = response.path("missCount");

        Assert.assertEquals(0, newHitCount - hitCount);
        Assert.assertEquals(1, newMissCount - missCount);
    }

    @Test
    public void primes2SieveTest()
    {
        MockMvcResponse response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int hitCount = response.path("hitCount");
        int missCount = response.path("missCount");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/2?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(2)).
                body("primes", hasSize(1)).
                body("primes", hasItem(2));

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/2?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(2)).
                body("primes", hasSize(1)).
                body("primes", hasItem(2));

        response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int newHitCount = response.path("hitCount");
        int newMissCount = response.path("missCount");

        Assert.assertEquals(1, newHitCount - hitCount);
        Assert.assertEquals(1, newMissCount - missCount);
    }

    @Test
    public void primes10SieveTest()
    {
        MockMvcResponse response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int hitCount = response.path("hitCount");
        int missCount = response.path("missCount");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/10?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(10)).
                body("primes", hasSize(primes10.length)).
                body("primes", contains(primes10));

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/10?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(10)).
                body("primes", hasSize(primes10.length)).
                body("primes", contains(primes10));

        response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int newHitCount = response.path("hitCount");
        int newMissCount = response.path("missCount");

        Assert.assertEquals(1, newHitCount - hitCount);
        Assert.assertEquals(1, newMissCount - missCount);
    }

    @Test
    public void primes100SieveTest()
    {
        MockMvcResponse response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int hitCount = response.path("hitCount");
        int missCount = response.path("missCount");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/100?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(100)).
                body("primes", hasSize(primes100.length)).
                body("primes", contains(primes100));

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/100?opt=S").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("initial", equalTo(100)).
                body("primes", hasSize(primes100.length)).
                body("primes", contains(primes100));

        response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats?opt=S").
                then().extract().response();

        int newHitCount = response.path("hitCount");
        int newMissCount = response.path("missCount");

        Assert.assertEquals(1, newHitCount - hitCount);
        Assert.assertEquals(1, newMissCount - missCount);
    }

    @Test
    public void invalidOptionTest()
    {
        RestAssuredMockMvc.given().
                when().
                get("/primesCache/100?opt=invalid").
                then().
                statusCode(400);
    }

    @Test
    public void primesCacheStatsTest()
    {
        MockMvcResponse response = RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats").
                then().extract().response();

        int hitCount = response.path("hitCount");
        int missCount = response.path("missCount");
        int requestCount = response.path("requestCount");
        int cacheSize = response.path("cacheSize");

        RestAssuredMockMvc.given().when().
                get("/primesCache/1032");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("hitCount", equalTo(hitCount)).
                body("missCount", equalTo(missCount + 1)).
                body("requestCount", equalTo(requestCount + 1)).
                body("cacheSize", equalTo(cacheSize + 1));

        RestAssuredMockMvc.given().when().
                get("/primesCache/1032");

        RestAssuredMockMvc.given().
                when().
                get("/primesCache/cacheStats").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("hitCount", equalTo(hitCount + 1)).
                body("missCount", equalTo(missCount + 1)).
                body("requestCount", equalTo(requestCount + 2)).
                body("cacheSize", equalTo(cacheSize + 1));
    }
}
