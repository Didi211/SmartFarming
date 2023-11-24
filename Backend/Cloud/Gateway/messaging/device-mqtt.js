// send device metadata to edge

import { mqttClient } from "../config/mqtt-config.js";
import { mqttTopicBuilder } from "../utils/path-builder.js";

const publishAddDevice = (token, device) => { 
    if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'devices', 'add');
    mqttClient.publish(topic, JSON.stringify({
        data: { 
            device: device
        }
    }));
}

const publishUpdateDevice = (token, id, device) => { 
    if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    console.log('publish update:',device)

    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'devices', 'update');
    mqttClient.publish(topic, JSON.stringify({
        data: { 
            id: id,
            device: device
        }
    }));
}

const publishRemoveDevice = (token, id) => { 
    if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'devices', 'remove');
    mqttClient.publish(topic, JSON.stringify({
        data: {
            id: id
        }
    }));
}

export default { 
    publishAddDevice,
    publishUpdateDevice,
    publishRemoveDevice,
   
}