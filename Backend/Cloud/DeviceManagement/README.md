# Device Management Service

This service is responsible for managing devices and general data in the cloud within the Smart Farming system.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service (e.g., `3002`)
- `MONGO_DB_URI`: MongoDB connection URI for accessing device data

Example `.env` file:

```plaintext
PORT=3002
MONGO_DB_URI=YOUR_MONGODB_URI_HERE
