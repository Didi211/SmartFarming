# Sensor Data Service

This service is responsible for managing sensor data in the Smart Farming system.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service (e.g., `3003`)
- `INFLUX_DB_ORGANIZATION`: InfluxDB organization name
- `INFLUX_DB_BUCKET`: InfluxDB bucket for sensor data
- `BUCKET_SENSOR_DATA_API_TOKEN`: API token for accessing the InfluxDB bucket
- `INFLUX_DB_URL`: URL for accessing the InfluxDB instance

Example `.env` file:

```plaintext
PORT=3003
INFLUX_DB_ORGANIZATION=org
INFLUX_DB_BUCKET=sensor-data
BUCKET_SENSOR_DATA_API_TOKEN=YOUR_API_TOKEN
INFLUX_DB_URL=http://cloud-influxdb:8086
