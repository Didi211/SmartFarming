# Smart Farming 

This application serves as the practical part of a bachelor's thesis and aims to monitor and control devices related to gardening and agriculture using IoT technology.

This repository consists of two parts: the backend composed of microservices and the frontend, which is an Android application. The backend handles data collection, processing, and management through microservices architecture, while the frontend provides a user interface for interacting with the application.

## Features

- Monitoring historical data from sensors
- Creation and management of sensors
- Creation and management of rules
- Utilization of background service for real-time data collection and processing of alarm notifications

## Installation

1. Clone this repository to your local system using `git clone`.
2. For detailed installation instructions and setup steps for both the backend and frontend, please refer to the following README.md files:

- [Backend](./Backend/README.md)
- [Frontend](./Frontend/README.md)

## Technologies Used

| Backend  | Frontend |
| ------------- | ------------- |
| Node.js  | Kotlin  |
| Express.js  | Jetpack Compose  |
| InfluxDB  | Retrofit |
| MongoDB  | Y Charts - Open Chart Library |
| Firebase and Firestore  | Hilt Dependency Injection |
| EdgeX Foundry - IoT Platform  |  |

## Screenshots
**Complete architecture of backend system**
<div align="center">
  <img width="839" alt="arhitektura-projekta" src="https://github.com/Didi211/SmartFarming/assets/82868612/898cef30-0710-4e01-879c-a4435a230daa">
</div>


**Mobile app**
| Home Screen | Graph Screen | Setting Screen | Filtered locations |
|:-:|:-:|:-:|:-:|
| <img src="https://github.com/Didi211/SmartFarming/assets/82868612/c0419a63-88ed-43ab-be9e-f814480f84ff" alt="Home Screen" width="250" height="auto"> | <img src="https://github.com/Didi211/SmartFarming/assets/82868612/a5eff66a-3ef5-4361-993f-008735de8d46" alt="Graph Screen" width="250" height="auto"> | <img src="https://github.com/Didi211/SmartFarming/assets/82868612/aa59fe64-6747-423d-a9ea-cf7f786b1d29" width="250" height="auto" alt="Setting Screen"> | <img src="https://github.com/Didi211/SmartFarming/assets/82868612/e3a7e9f9-aa8b-4986-ac6e-549d4dcd5299" alt="Rule Details" width="250" height="auto">
