# Cloud Notification Service

This service is responsible for managing notifications in the Smart Farming system.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service (e.g., `3001`)
- `MONGO_DB_URI`: MongoDB connection URI for accessing notification data

Example `.env` file:

```plaintext
PORT=3001
MONGO_DB_URI=mongodb+srv://miticd99:6Dxi1D2Vajzulgj1@cluster0.1sivlqa.mongodb.net/?retryWrites=true&w=majority
