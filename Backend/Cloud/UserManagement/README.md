# User Management Service

This service is responsible for managing user authentication and authorization in the Smart Farming system.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service (e.g., `3000`)
- `FIREBASE_API_KEY`: Firebase API key
- `AUTH_DOMAIN`: Firebase authentication domain
- `PROJECT_ID`: Firebase project ID
- `STORAGE_BUCKET`: Firebase storage bucket
- `MESSAGING_SENDER_ID`: Firebase messaging sender ID
- `APP_ID`: Firebase app ID

Example `.env` file:

```plaintext
PORT=3000
FIREBASE_API_KEY=YOUR_FIREBASE_API_KEY
AUTH_DOMAIN=YOUR_AUTH_DOMAIN
PROJECT_ID=YOUR_PROJECT_ID
STORAGE_BUCKET=YOUR_STORAGE_BUCKET
MESSAGING_SENDER_ID=YOUR_MESSAGING_SENDER_ID
APP_ID=YOUR_APP_ID
