# Edge Device Management Service

This service utilizes EdgeX to check device status health and synchronize it with the cloud.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service
- `EDGEX_CORE_METADATA_URL`: URL for the EdgeX Core Metadata service
- `EDGE_MQTT_URL`: MQTT broker URL for edge communication
- `MQTT_TOPIC_EDGE`: MQTT topic for edge communication

Example `.env` file:

```plaintext
PORT=5001
EDGEX_CORE_METADATA_URL=http://edgex-core-metadata:59881/api/v3
EDGE_MQTT_URL=mqtt://edge-mqtt:1884
MQTT_TOPIC_EDGE=edge
