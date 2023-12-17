import { mqttClient } from '../config/mqtt-config.js';
import logic from '../logic/analytics-logic.js';
import { runMqttMessageChecking } from '../utils/mqtt-message-validation.js';

export const startListening = () => { 
    mqttClient.on('message', async (topic, message) => { 
        try { 
            if (topic === process.env.MQTT_TOPIC_RT_DATA) {
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
    // need to decide if i am only going to store new state in mongodb or call rule engine from here
    await logic.runRuleEngine("sensorId", messageJson.reading);
    // consider changing this mqtt to receives only kuiper actions to update mongodb
}