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
  - Device Management: [Documentation](Cloud/DeviceManagement/README.md)
  - Gateway: [Documentation](Cloud/Gateway/README.md)
  - Notification: [Documentation](Cloud/Notification/README.md)
  - Sensor Data: [Documentation](Cloud/SensorData/README.md)
  - User Management: [Documentation](Cloud/UserManagement/README.md)

- **Edge Services:**
  - Analytics: [Documentation](Edge/Analytics/README.md)
  - Device Management: [Documentation](Edge/DeviceManagement/README.md)
  - Devices/Sensor: [Documentation](Edge/DevicesSensor/README.md)
  - Gateway: [Documentation](Edge/Gateway/README.md)
  - Persistence: [Documentation](Edge/Persistence/README.md)
