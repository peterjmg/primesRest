package primes.domain;

public class PrimesCacheStats {

    private long hitCount;
    private long missCount;
    private long requestCount;
    private long cacheSize;

    // Empty construct required for JSON
    public PrimesCacheStats() { }

    public PrimesCacheStats(long hitCount, long missCount, long requestCount, long cacheSize) {

        this.hitCount = hitCount;
        this.missCount = missCount;
        this.requestCount = requestCount;
        this.cacheSize = cacheSize;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public long getMissCount() {
        return missCount;
    }

    public void setMissCount(long missCount) {
        this.missCount = missCount;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public String toString() {
        return String.format("hitCount: %d; missCount: %d, requestCount: %d, size: %d",
                hitCount, missCount, requestCount, cacheSize);
    }
}
