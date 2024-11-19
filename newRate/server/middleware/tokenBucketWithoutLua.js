const redis = require('redis');
const client = redis.createClient({
    url: 'redis://127.0.0.1:6379',
});

client.connect().catch(console.error);

const rateLimit = {
    capacity: 10, // Max tokens
    refillRate: 1, // Tokens per second
};

async function tokenBucketWithoutLua(req, res, next) {
    const currentTime = Date.now();
    const keyTokens = 'rateLimit:tokens';
    const keyTimestamp = 'rateLimit:lastRefillTimestamp';

    try {
        const tokens = await client.get(keyTokens) || rateLimit.capacity;
        const lastRefillTimestamp = await client.get(keyTimestamp) || currentTime;

        const elapsedTime = (currentTime - lastRefillTimestamp) / 1000;
        const updatedTokens = Math.min(
            rateLimit.capacity,
            parseFloat(tokens) + elapsedTime * rateLimit.refillRate
        );

        if (updatedTokens >= 1) {
            await client.set(keyTokens, updatedTokens - 1);
            await client.set(keyTimestamp, currentTime);
            res.setHeader('X-RateLimit-Limit', rateLimit.capacity);
            res.setHeader('X-RateLimit-Remaining', Math.floor(updatedTokens - 1));
            next();
        } else {
            const timeUntilNextToken = Math.ceil((1 - updatedTokens) / rateLimit.refillRate) * 1000;
            res.setHeader('X-RateLimit-Limit', rateLimit.capacity);
            res.setHeader('X-RateLimit-Remaining', 0);
            res.setHeader('X-RateLimit-Reset', timeUntilNextToken);
            res.status(429).send(`Rate limit exceeded. Try again in ${timeUntilNextToken} ms.`);
        }
    } catch (error) {
        console.error('Redis error:', error);
        res.status(500).send('Internal Server Error');
    }
}

module.exports = tokenBucketWithoutLua;
