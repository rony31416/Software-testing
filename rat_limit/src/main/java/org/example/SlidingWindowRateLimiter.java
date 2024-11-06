package org.example;

import redis.clients.jedis.Jedis;

public class SlidingWindowRateLimiter {
    private int limit;
    private int windowSize;
    private String key;

    public SlidingWindowRateLimiter(String key, int limit, int windowSize) {
        this.key = key;
        this.limit = limit;
        this.windowSize = windowSize;
    }

    public boolean allowRequest() {
        Jedis jedis = RedisConnection.getConnection();
        long currentTime = System.currentTimeMillis();
        String windowKey = key + ":" + (currentTime / (windowSize * 1000));

        jedis.zadd(key, currentTime, String.valueOf(currentTime));
        jedis.zremrangeByScore(key, 0, currentTime - windowSize * 1000);

        return jedis.zcard(key) <= limit;
    }
}
