package primesTest.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import primes.config.WebConfig;
import primes.utils.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class PrimesGeneratorTest {

    @Test
    public void isPrimeTest()
    {
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(0));
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(1));
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(4));
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(63));
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(98));
        Assert.assertFalse(DivisionPrimesGenerator.IsPrime(527));

        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(2));
        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(3));
        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(7));
        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(19));
        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(97));
        Assert.assertTrue(DivisionPrimesGenerator.IsPrime(433));
    }

    @Test
    public void DivisionGenerateTest()
    {
        RunGenerateTest(new DivisionPrimesGenerator());
    }

    @Test
    public void SieveGenerateTest()
    {
        RunGenerateTest(new SievePrimesGenerator());
    }

    @Test
    public void DivisionStreamGenerateTest()
    {
        PrimesGenerator primesGenerator = new DivisionStreamPrimesGenerator();

        List<Integer> values = primesGenerator.generate(0);
        Assert.assertEquals(0, values.size());

        values = primesGenerator.generate(1);
        Assert.assertEquals(0, values.size());

        values = primesGenerator.generate(2);
        Assert.assertEquals(1, values.size());
        Assert.assertTrue(values.contains(2));

        values = primesGenerator.generate(3);
        Assert.assertEquals(2, values.size());
        Assert.assertTrue(values.contains(2));
        Assert.assertTrue(values.contains(3));

        values = primesGenerator.generate(30);
        Assert.assertEquals(10, values.size());

        values = primesGenerator.generate(30);
        Assert.assertEquals(10, values.size());
        values = primesGenerator.generate(30);
        Assert.assertEquals(10, values.size());
        values = primesGenerator.generate(30);
        Assert.assertEquals(10, values.size());
    }

    private void RunGenerateTest(PrimesGenerator primesGenerator)
    {
        List<Integer> values = primesGenerator.generate(0);
        Assert.assertEquals(0, values.size());

        values = primesGenerator.generate(1);
        Assert.assertEquals(0, values.size());

        values = primesGenerator.generate(2);
        Assert.assertEquals(1, values.size());
        Assert.assertTrue(values.contains(2));

        values = primesGenerator.generate(3);
        Assert.assertEquals(2, values.size());
        Assert.assertTrue(values.contains(2));
        Assert.assertTrue(values.contains(3));

        values = primesGenerator.generate(10);
        Assert.assertEquals(4, values.size());
        Assert.assertTrue(values.contains(2));
        Assert.assertTrue(values.contains(3));
        Assert.assertTrue(values.contains(5));
        Assert.assertTrue(values.contains(7));

        values = primesGenerator.generate(7);
        Assert.assertEquals(4, values.size());
        Assert.assertTrue(values.contains(2));
        Assert.assertTrue(values.contains(3));
        Assert.assertTrue(values.contains(5));
        Assert.assertTrue(values.contains(7));

        values = primesGenerator.generate(30);
        Assert.assertEquals(10, values.size());
        Assert.assertArrayEquals(new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}, values.toArray());

        values = primesGenerator.generate(1000000);
        Assert.assertEquals(78498, values.size());
    }
}