//package org.example;
//
//import redis.clients.jedis.Jedis;
//
//public class TokenBucketRateLimiter {
//    private int capacity;
//    private int tokensPerInterval;
//    private int interval;
//    private String key;
//
//    public TokenBucketRateLimiter(String key, int capacity, int tokensPerInterval, int interval) {
//        this.key = key;
//        this.capacity = capacity;
//        this.tokensPerInterval = tokensPerInterval;
//        this.interval = interval;
//    }
//
//    public boolean allowRequest() {
//        Jedis jedis = RedisConnection.getConnection();
//
//        if (jedis.exists(key)) {
//            long lastRefill = Long.parseLong(jedis.hget(key, "lastRefillTime"));
//            int tokens = Integer.parseInt(jedis.hget(key, "tokens"));
//
//            long now = System.currentTimeMillis();
//            long elapsedTime = (now - lastRefill) / 1000;
//
//            int newTokens = (int) (elapsedTime / interval * tokensPerInterval);
//            tokens = Math.min(tokens + newTokens, capacity);
//
//            jedis.hset(key, "lastRefillTime", String.valueOf(now));
//            jedis.hset(key, "tokens", String.valueOf(tokens - 1));
//
//            return tokens > 0;
//        } else {
//            jedis.hset(key, "tokens", String.valueOf(capacity - 1));
//            jedis.hset(key, "lastRefillTime", String.valueOf(System.currentTimeMillis()));
//            return true;
//        }
//    }
//}


package org.example;

import redis.clients.jedis.Jedis;

public class TokenBucketRateLimiter {
    private int capacity;
    private int tokensPerInterval;
    private int interval; // in seconds
    private String key;

    public TokenBucketRateLimiter(String key, int capacity, int tokensPerInterval, int interval) {
        this.key = key;
        this.capacity = capacity;
        this.tokensPerInterval = tokensPerInterval;
        this.interval = interval;
    }

    public boolean allowRequest() {
        Jedis jedis = RedisConnection.getConnection();

        try {
            if (jedis.exists(key)) {
                long lastRefill = Long.parseLong(jedis.hget(key, "lastRefillTime"));
                int tokens = Integer.parseInt(jedis.hget(key, "tokens"));

                long now = System.currentTimeMillis();
                long elapsedTime = (now - lastRefill) / 1000; // time in seconds

                // Refill the tokens based on elapsed time
                int newTokens = (int) (elapsedTime / interval) * tokensPerInterval;
                tokens = Math.min(tokens + newTokens, capacity); // Don't exceed capacity

                // Update Redis with new token count and last refill time
                jedis.hset(key, "lastRefillTime", String.valueOf(now));
                jedis.hset(key, "tokens", String.valueOf(tokens));

                // Allow request if there are tokens available
                if (tokens > 0) {
                    // Decrease token count after allowing the request
                    jedis.hset(key, "tokens", String.valueOf(tokens - 1));
                    return true;
                } else {
                    return false; // No tokens available, block request
                }
            } else {
                // First request, initialize the token bucket
                jedis.hset(key, "tokens", String.valueOf(capacity - 1)); // Initially set to capacity-1
                jedis.hset(key, "lastRefillTime", String.valueOf(System.currentTimeMillis()));
                return true;
            }
        } finally {
            jedis.close(); // Close the Jedis connection to avoid resource leakage
        }
    }
}
