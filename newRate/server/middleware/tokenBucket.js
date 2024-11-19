const axios = require('axios');

async function tokenBucket(req, res, next) {
    try {
        const response = await axios.get('http://localhost:5000/rate-limit');
        const { allowed, remainingTokens, reset } = response.data;

        res.setHeader('X-RateLimit-Limit', 10); // Hardcoded capacity for now

        if (allowed) {
            res.setHeader('X-RateLimit-Remaining', remainingTokens);
            res.locals.remainingTokens = remainingTokens;
            next();
        } else {
            res.setHeader('X-RateLimit-Remaining', 0);
            res.setHeader('X-RateLimit-Reset', reset);
            res.status(429).send(`Too Many Requests - Rate limit exceeded. Try again in ${reset} ms`);
        }
    } catch (err) {
        console.error('Error communicating with rate-limiter service:', err);
        res.status(500).send('Internal Server Error');
    }
}

module.exports = tokenBucket;
