# Analytics Service

This service performs local analytics by interacting with EdgeX and is also responsible for managing devices and rules on the edge.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `PORT`: Port number for the service
- `EDGE_GATEWAY_URL`: URL for the Edge Gateway service
- `EDGEX_CORE_COMMAND_URL`: URL for the EdgeX Core Command service
- `EDGEX_RULES_ENGINE_URL`: URL for the EdgeX Rules Engine service
- `EDGEX_CORE_METADATA_URL`: URL for the EdgeX Core Metadata service
- `EDGE_MQTT_URL`: MQTT broker URL for edge communication
- `MQTT_TOPIC_RT_DATA`: MQTT topic for real-time data
- `KUIPER_TOPIC`: Kuiper topic for rules
- `KUIPER_STREAM_NAME`: Kuiper stream name
- `SENSOR_PROFILE`: Sensor profile name
- `SENSOR_DEVICE_URL`: Sensor device URL
- `SENSOR_DEVICE_PATH`: Sensor device API path
- `SENSOR_DEVICE_PORT`: Sensor device port
- `ACTUATOR_PROFILE`: Actuator profile name
- `ACTUATOR_DEVICE_URL`: Actuator device URL
- `ACTUATOR_DEVICE_PATH`: Actuator device API path
- `ACTUATOR_DEVICE_PORT`: Actuator device port
- `MONGO_DB_URI`: MongoDB connection URI for analytics data
- `DEVICE_SIMULATOR_ENABLED`: Flag indicating if the device simulator is enabled
- `DEVICE_SIMULATOR_URL`: URL for the device simulator

Example `.env` file:

```plaintext
PORT=5004
EDGE_GATEWAY_URL=http://edge-gateway:5001/api/device-management
EDGEX_CORE_COMMAND_URL=http://edgex-core-command:59882/api/v3
EDGEX_RULES_ENGINE_URL=http://edgex-kuiper:59720
EDGEX_CORE_METADATA_URL=http://edgex-core-metadata:59881/api/v3
EDGE_MQTT_URL=mqtt://edge-mqtt:1884
MQTT_TOPIC_RT_DATA=edgex-events
KUIPER_TOPIC=kuiper-rules
KUIPER_STREAM_NAME=SmartFarming
SENSOR_PROFILE=Humidity-Sensor
SENSOR_DEVICE_URL=device-simulator
SENSOR_DEVICE_PATH=api/device
SENSOR_DEVICE_PORT=6000
ACTUATOR_PROFILE=Water-Pump
ACTUATOR_DEVICE_URL=device-simulator
ACTUATOR_DEVICE_PATH=api/device/update/actuator
ACTUATOR_DEVICE_PORT=6000
MONGO_DB_URI=mongodb://edge-mongodb:27017/
DEVICE_SIMULATOR_ENABLED=true
DEVICE_SIMULATOR_URL=http://device-simulator:6000/api/device
