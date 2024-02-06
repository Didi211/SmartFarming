# Gateway Service

This service is responsible for managing communication between edge devices and the cloud services in the Smart Farming system. Additionally, the Cloud Gateway serves as the entry point for frontend clients.

## Environment Variables

Ensure the following environment variables are set before running the service:

- `DEVICE_MANAGEMENT_SERVICE_URL`: URL for the Device Management service in the cloud
- `NOTIFICATION_SERVICE_URL`: URL for the Notification service in the cloud
- `SENSOR_DATA_SERVICE_URL`: URL for the Sensor Data service in the cloud
- `USER_MANAGEMENT_SERVICE_URL`: URL for the User Management service in the cloud
- `MQTT_URL`: MQTT broker URL for cloud communication
- `MQTT_TOPIC_ALERTS`: MQTT topic from which gateway receives alerts from the edge
- `MQTT_TOPIC_EDGE`: MQTT topic for edge 
- `PORT`: Port number for the service

Example `.env` file:

```plaintext
DEVICE_MANAGEMENT_SERVICE_URL=http://cloud-device-management:3002/api/devices
NOTIFICATION_SERVICE_URL=http://cloud-notification:3001/api/notifications
SENSOR_DATA_SERVICE_URL=http://cloud-sensor-data:3003/api/sensor-data
USER_MANAGEMENT_SERVICE_URL=http://cloud-user-management:3000/api/users
MQTT_URL=mqtt://cloud-mqtt:1883
MQTT_TOPIC_ALERTS=alerts/+
MQTT_TOPIC_EDGE=edge
PORT=4001
