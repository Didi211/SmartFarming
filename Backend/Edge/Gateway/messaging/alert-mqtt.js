import { mqttCloudClient } from "../config/mqtt-config.js";
import { mqttTopicBuilder } from "../utils/path-builder.js";
import { MqttTokenManager } from "../utils/mqtt-token-manager.js";

export const publishAlert = (message) => { 
    if (!mqttCloudClient.connected) { 
        throw "MQTT client is not connected. Cannot publish message to the Cloud."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_ALERTS, MqttTokenManager.Token());
    mqttCloudClient.publish(topic, JSON.stringify({
        message: message
    }));
}
