# Edge Persistence Service

This service uses InfluxDB to retrieve periodic data from devices and synchronize it with the cloud.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service
- `INFLUX_DB_ORGANIZATION`: InfluxDB organization name
- `INFLUX_DB_BUCKET`: InfluxDB bucket for storing data
- `BUCKET_SENSOR_DATA_API_TOKEN`: API token for accessing the InfluxDB bucket
- `INFLUX_DB_URL`: URL for accessing the InfluxDB instance
- `EDGE_GATEWAY_URL`: URL for the Edge Gateway service
- `EDGE_MQTT_URL`: MQTT broker URL for edge communication
- `MQTT_TOPIC_RT_DATA`: MQTT topic for real-time data
- `SENSOR_PROFILE`: Sensor profile name

Example `.env` file:

```plaintext
PORT=5002
INFLUX_DB_ORGANIZATION=org
INFLUX_DB_BUCKET=local-sensor-data
BUCKET_SENSOR_DATA_API_TOKEN=YOUR_API_TOKEN
INFLUX_DB_URL=http://edge-influxdb:8086
EDGE_GATEWAY_URL=http://edge-gateway:5001/api/persistence
EDGE_MQTT_URL=mqtt://edge-mqtt:1884
MQTT_TOPIC_RT_DATA=edgex-events
SENSOR_PROFILE=Humidity-Sensor
