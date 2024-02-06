# Smart Farming Backend

This repository contains the backend microservices for the Smart Farming project. The backend is composed of multiple microservices responsible for handling data collection, processing, and management related to monitoring and controlling devices in gardening and agriculture using IoT technology.

## Technologies Used

- **Node.js:** Runtime environment for executing JavaScript code outside the browser, used for building backend applications.
- **Express.js:** Web framework for Node.js, used for building RESTful APIs.
- **MongoDB:** NoSQL database used for storing general application data.
- **InfluxDB:** Time-series database used for storing sensor data.
- **Firebase:** Authentication service used for user authentication.
- **EdgeX IoT Platform:** Open-source IoT platform used for device connectivity and data integration.

## Setup

Before running the microservices, make sure to configure the necessary environment variables either by including them in separate `.env` files or directly in the `docker-compose.yml` file.

To run the backend services using Docker Compose, follow these steps:

1. Ensure that all required environment variables are provided in the `.env` files with the names specified in the `docker-compose.yml` file.
2. Modify the `docker-compose.yml` file if necessary, including changes to environment variables directly in the file.
3. Run the following command to start the backend services:

```bash
docker compose up -d --build
```
## Viewing Environment Variables

To view the list of environment variables required for each microservice, please refer to the following services:

- **Cloud Services:**
  - Device Management: [Documentation](device_management_cloud_env_variables.md)
  - Gateway: [Documentation](gateway_cloud_env_variables.md)
  - Notification: [Documentation](notification_cloud_env_variables.md)
  - Sensor Data: [Documentation](sensor_data_cloud_env_variables.md)
  - User Management: [Documentation](user_management_cloud_env_variables.md)

- **Edge Services:**
  - Analytics: [Documentation](analytics_edge_env_variables.md)
  - Device Management: [Documentation](device_management_edge_env_variables.md)
  - Devices/Sensor: [Documentation](devices_sensor_edge_env_variables.md)
  - Gateway: [Documentation](gateway_edge_env_variables.md)
  - Persistence: [Documentation](persistence_edge_env_variables.md)
