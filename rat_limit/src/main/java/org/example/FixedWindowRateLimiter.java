package org.example;

import redis.clients.jedis.Jedis;

public class FixedWindowRateLimiter {
    private int limit;
    private int windowSize;
    private String key;

    public FixedWindowRateLimiter(String key, int limit, int windowSize) {
        this.key = key;
        this.limit = limit;
        this.windowSize = windowSize;
    }

    public boolean allowRequest() {
        Jedis jedis = RedisConnection.getConnection();
        long currentWindow = System.currentTimeMillis() / (windowSize * 1000);
        String windowKey = key + ":" + currentWindow;

        Long currentCount = jedis.incr(windowKey);

        if (currentCount == 1) {
            jedis.expire(windowKey, windowSize);
        }

        return currentCount <= limit;
    }
}
