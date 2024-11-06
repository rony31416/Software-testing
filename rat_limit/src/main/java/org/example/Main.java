package org.example;

public class Main {
    public static void main(String[] args) {
        TokenBucketRateLimiter tokenLimiter = new TokenBucketRateLimiter("tokenBucket", 10, 5, 1);
        System.out.println("Token Bucket: " + tokenLimiter.allowRequest());

        LeakingBucketRateLimiter leakingLimiter = new LeakingBucketRateLimiter("leakingBucket", 10, 1);
        System.out.println("Leaking Bucket: " + leakingLimiter.allowRequest());

        FixedWindowRateLimiter fixedWindowLimiter = new FixedWindowRateLimiter("fixedWindow", 10, 60);
        System.out.println("Fixed Window: " + fixedWindowLimiter.allowRequest());

        SlidingWindowRateLimiter slidingLimiter = new SlidingWindowRateLimiter("slidingWindow", 10, 60);
        System.out.println("Sliding Window: " + slidingLimiter.allowRequest());
    }
}
