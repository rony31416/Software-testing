package org.example;

public class TokenBucketTest {
    public static void main(String[] args) {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter("user:1234", 10, 5, 2); // 10 tokens max, 5 tokens per 2 seconds

        // Simulate multiple requests
        for (int i = 0; i < 20; i++) {
            boolean allowed = rateLimiter.allowRequest();
            System.out.println("Request " + (i + 1) + " allowed: " + allowed);
            try {
                Thread.sleep(500); // Simulate a delay of 500ms between requests
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
