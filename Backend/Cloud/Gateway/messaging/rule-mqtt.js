// send rules to edge 

import { mqttClient } from "../config/mqtt-config.js";
import { mqttTopicBuilder } from "../utils/path-builder.js";


const publishAddRule = (token, rule) => { 
    if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'rules', 'add');
    mqttClient.publish(topic, JSON.stringify({ 
        data: rule
    }));
}

const publishUpdateRule = (token, id, rule) => { 
    if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'rules', 'update');
    mqttClient.publish(topic, JSON.stringify({ 
        data: { 
            id: id,
            rule: rule
        }
    }));
}

const publishRemoveRule = (token, id) => { 
     if (!mqttClient.connected) {
        throw "MQTT client is not connected. Cannot publish message to the Edge."
    }
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_EDGE, token, 'rules', 'remove');
    mqttClient.publish(topic, JSON.stringify({ 
        data: id
    }));
}



export default { 

    publishAddRule,
    publishUpdateRule,
    publishRemoveRule
}