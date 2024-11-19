document.getElementById('sendRequestButton').addEventListener('click', async () => {
    try {
        // Make a request to the server API
        const response = await fetch('http://localhost:4000/api');
        const message = await response.text();
        const rateLimit = response.headers.get('X-RateLimit-Limit');
        const remainingTokens = response.headers.get('X-RateLimit-Remaining');
        const resetTime = response.headers.get('X-RateLimit-Reset');

        // Update the UI with the response and rate limit info
        document.getElementById('responseMessage').innerText = message;
        document.getElementById('rateLimitInfo').innerText = `Rate Limit: ${rateLimit}, Remaining Tokens: ${remainingTokens}, Reset Time: ${resetTime || 'N/A'} ms`;
    } catch (error) {
        console.error('Error sending request:', error);
        document.getElementById('responseMessage').innerText = 'Error sending request.';
        document.getElementById('rateLimitInfo').innerText = '';
    }
});
