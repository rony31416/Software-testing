const redis = require('redis');
const client = redis.createClient({
    url: 'redis://127.0.0.1:6379',
});

client.connect().catch(console.error);

const rateLimit = {
    capacity: 10, // Max tokens
    refillRate: 1, // Tokens per second
};

const luaScript = `
    local keyTokens = KEYS[1]
    local keyTimestamp = KEYS[2]
    local capacity = tonumber(ARGV[1])
    local refillRate = tonumber(ARGV[2])
    local currentTime = tonumber(ARGV[3])
    local tokensToConsume = tonumber(ARGV[4])

    local tokens = tonumber(redis.call('get', keyTokens)) or capacity
    local lastRefillTimestamp = tonumber(redis.call('get', keyTimestamp)) or currentTime

    local elapsedTime = (currentTime - lastRefillTimestamp) / 1000
    tokens = math.min(capacity, tokens + (elapsedTime * refillRate))

    if tokens >= tokensToConsume then
        tokens = tokens - tokensToConsume
        redis.call('set', keyTokens, tostring(tokens))
        redis.call('set', keyTimestamp, tostring(currentTime))
        return {1, tokens}
    else
        local timeUntilNextToken = math.ceil(1000 * ((1 - tokens) / refillRate))
        return {0, tokens, timeUntilNextToken}
    end
`;

async function tokenBucketWithLua(req, res, next) {
    const currentTime = Date.now();
    const keyTokens = 'rateLimit:tokens';
    const keyTimestamp = 'rateLimit:lastRefillTimestamp';

    try {
        const result = await client.eval(luaScript, {
            keys: [keyTokens, keyTimestamp],
            arguments: [
                rateLimit.capacity.toString(),
                rateLimit.refillRate.toString(),
                currentTime.toString(),
                '1', // Tokens to consume per request
            ],
        });

        const allowed = result[0];
        const remainingTokens = result[1];
        const timeUntilNextToken = result[2] || 0;

        res.setHeader('X-RateLimit-Limit', rateLimit.capacity);
        res.setHeader('X-RateLimit-Remaining', remainingTokens);

        if (allowed === 1) {
            next();
        } else {
            res.setHeader('X-RateLimit-Reset', timeUntilNextToken);
            res.status(429).send(`Rate limit exceeded. Try again in ${timeUntilNextToken} ms.`);
        }
    } catch (error) {
        console.error('Redis error:', error);
        res.status(500).send('Internal Server Error');
    }
}

module.exports = tokenBucketWithLua;
