package primes.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "primes")
public class Primes {

    private Integer initial;
    private List<Integer> primes;

    public Primes() { }

    public Primes(Integer maxValue, List<Integer> values) {

        this.initial = maxValue;
        this.primes = values;
    }

    @XmlElement(name = "initial")
    public Integer getInitial() {
        return this.initial;
    }

    public void setInitial(Integer initial) {
        this.initial = initial;
    }

    @XmlElement(name = "primes")
    public List<Integer> getPrimes() {
        return primes;
    }

    public void setPrimes(List<Integer> primes) {
        this.primes = primes;
    }

    @Override
    public String toString() {
        return String.format("initial: %d; primes [%s]", initial, primes.toString());
    }
}
