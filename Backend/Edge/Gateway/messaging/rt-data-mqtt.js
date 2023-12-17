import { mqttCloudClient, mqttEdgeClient } from '../config/mqtt-config.js';
import { mqttTopicBuilder } from '../utils/path-builder.js';
import { MqttTokenManager } from '../utils/mqtt-token-manager.js';
import { runMqttMessageChecking } from '../utils/mqtt-message-validation.js';

export const startListening = () => { 
    mqttEdgeClient.on('message', async (topic, message) => { 
        try { 
            if (topic.startsWith(`${process.env.MQTT_TOPIC_RT_DATA_EDGE}`)) {
                runMqttMessageChecking(message);
                console.log(`New message on topic ${topic} - ${message}`);
                await handleRTDataMessage(message);
            }
            else { 
                throw `Unhandled topic: ${topic}`;
            }
        }
        catch(error) { 
            console.log(error);
        }
    })
}

const handleRTDataMessage = async (message) => { 
    // let sensorId = topic.split('/')[1];
    let messageJson = JSON.parse(message);
   
    // publish message to cloud mqtt 
    let token = MqttTokenManager.Token();
    let topic = mqttTopicBuilder(process.env.MQTT_TOPIC_RT_DATA_CLOUD, token);
    mqttCloudClient.publish(topic, JSON.stringify({
        sensorId: messageJson.tags.mongoId,
        reading: messageJson.readings[0].value
    }));
    console.log('Published ', {
        sensorId: messageJson.tags.mongoId,
        reading: messageJson.readings[0].value
    })
}
