const express = require('express');
const cors = require('cors');
const tokenBucketMiddleware = require('./middleware/tokenBucket'); // Middleware that communicates with rate-limiter service
const abc = require('./middleware/abc')
const app = express();
const PORT = 4000;

// CORS options
const corsOptions = {
    exposedHeaders: ['X-RateLimit-Limit', 'X-RateLimit-Remaining', 'X-RateLimit-Reset'],
};
app.use(cors(corsOptions));

// Apply token bucket middleware
app.use(tokenBucketMiddleware);

// Example API endpoint
app.get('/api', (req, res) => {
    const remainingTokens = res.locals.remainingTokens; // Passed from middleware
    res.send(`Hello, Rony! You have ${remainingTokens} requests left this second.`);
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
