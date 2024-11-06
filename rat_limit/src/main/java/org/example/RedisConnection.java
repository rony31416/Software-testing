package org.example;

import redis.clients.jedis.Jedis;

public class RedisConnection {
    private static Jedis jedis = null;

    static {
        jedis = new Jedis("localhost", 6379); // Adjust if Redis is on another host or port
    }

    public static Jedis getConnection() {
        return jedis;
    }
}
