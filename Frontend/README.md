# Smart Farming Android Application

This Android application serves as the frontend for the Smart Farming project. It provides a user interface for interacting with the backend services to monitor and control devices related to gardening and agriculture using IoT technology. The application is built using Kotlin programming language and utilizes Jetpack Compose for UI development.

## Setup

Before building and running the application, make sure to set the API URL and MQTT URL to point to the backend server. These configurations can be adjusted in the `build.gradle.kts` file at the module level.

```kotlin
android {
    ...
    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://your-backend-api-url.com\"")
            buildConfigField("String", "MQTT_URL", "\"tcp://your-backend-mqtt-url.com\"")
        }
    }
}
