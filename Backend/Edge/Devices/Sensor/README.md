# Sensor Simulator

This service simulates sensor devices on the edge.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `EDGEX_DEVICE_REST_URL`: URL for the EdgeX Device REST service
- `PORT`: Port number for the service

Example `.env` file:

```plaintext
EDGEX_DEVICE_REST_URL=http://edgex-device-rest:59986/api/v3
PORT=6000
