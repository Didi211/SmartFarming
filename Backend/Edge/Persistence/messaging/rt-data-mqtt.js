import { mqttClient } from '../config/mqtt-config.js';
import logic from '../logic/logic.js';


export const startListening = () => { 
    mqttClient.on('message', async (topic, message) => { 
        try { 
            if (topic.startsWith(`${process.env.MQTT_TOPIC_RT_DATA}/`)) {
                console.log(`New message on topic ${topic} - ${message}`);
                await handleRTDataMessage(topic, message);
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

const handleRTDataMessage = async (topic, message) => { 
    let sensorId = topic.split('/')[1];
    let messageJson;
    try { 
        messageJson = JSON.parse(message);
    }
    catch(error) { 
        throw "Message from MQTT is not in JSON format."
    }
    if (messageJson.reading == undefined) { 
        throw "Message from MQTT does not have 'reading' property."
    }
    // store in influxdb
    await logic.saveToDb(sensorId, messageJson.reading);
}
