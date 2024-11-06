package org.example;

import redis.clients.jedis.Jedis;

public class LeakingBucketRateLimiter {
    private int capacity;
    private int leakRate;
    private String key;

    public LeakingBucketRateLimiter(String key, int capacity, int leakRate) {
        this.key = key;
        this.capacity = capacity;
        this.leakRate = leakRate;
    }

    public boolean allowRequest() {
        Jedis jedis = RedisConnection.getConnection();

        long now = System.currentTimeMillis();
        if (jedis.exists(key)) {
            long lastChecked = Long.parseLong(jedis.hget(key, "lastChecked"));
            int waterLevel = Integer.parseInt(jedis.hget(key, "waterLevel"));

            long elapsedTime = (now - lastChecked) / 1000;
            int leakedAmount = (int) (elapsedTime * leakRate);
            waterLevel = Math.max(0, waterLevel - leakedAmount);

            jedis.hset(key, "lastChecked", String.valueOf(now));
            jedis.hset(key, "waterLevel", String.valueOf(waterLevel + 1));

            return waterLevel < capacity;
        } else {
            jedis.hset(key, "lastChecked", String.valueOf(now));
            jedis.hset(key, "waterLevel", "1");
            return true;
        }
    }
}
