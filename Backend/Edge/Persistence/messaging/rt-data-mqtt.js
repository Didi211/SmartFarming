import { runMqttMessageChecking } from '../utils/mqtt-message-validation.js';
import { mqttClient } from '../config/mqtt-config.js';
import logic from '../logic/logic.js';


export const startListening = () => { 
    mqttClient.on('message', async (topic, message) => { 
        try { 
            if (topic.startsWith(`${process.env.MQTT_TOPIC_RT_DATA}`)) {
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
    let messageJson = JSON.parse(message);
    // store in influxdb
    await logic.saveToDb(messageJson.tags.mongoId, messageJson.readings[0].value);
}
