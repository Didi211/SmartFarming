# Edge Gateway Service

This service acts as the gateway between edge system and the cloud system.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service
- `USER_EMAIL`: Email address for setup Edge system with Cloud system
- `CLOUD_MQTT_URL`: MQTT broker URL for cloud communication
- `EDGE_MQTT_URL`: MQTT broker URL for edge communication
- `MQTT_TOPIC_RT_DATA_EDGE`: MQTT topic for real-time data from edge devices
- `MQTT_TOPIC_RT_DATA_CLOUD`: MQTT topic for real-time data from cloud
- `MQTT_TOPIC_ALERTS`: MQTT topic for alerts
- `MQTT_TOPIC_CLOUD_SUB`: MQTT topic for cloud subscription
- `SENSOR_PROFILE`: Sensor profile name 
- `ANALYTICS_SERVICE_URL`: URL for the Analytics service
- `CLOUD_GATEWAY_URL`: URL for the Cloud Gateway service

Example `.env` file:

```plaintext
PORT=5001
USER_EMAIL=dimitrije.mitic@email.com
CLOUD_MQTT_URL=mqtt://cloud-mqtt:1883
EDGE_MQTT_URL=mqtt://edge-mqtt:1884
MQTT_TOPIC_RT_DATA_EDGE=edgex-events
MQTT_TOPIC_RT_DATA_CLOUD=rt-data
MQTT_TOPIC_ALERTS=alerts
MQTT_TOPIC_CLOUD_SUB=edge
SENSOR_PROFILE=Humidity-Sensor
ANALYTICS_SERVICE_URL=http://edge-analytics:5004/api/updates
CLOUD_GATEWAY_URL=http://cloud-gateway:4001/api
