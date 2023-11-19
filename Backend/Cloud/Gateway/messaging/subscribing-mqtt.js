// receives alerts from edge 
// receives real time data from edge 
import { mqttClient } from "../config/mqtt-config.js";
import notificationLogic from "../logic/notification-logic.js";
import userManagementLogic from "../logic/user-management-logic.js";

const startListening = () => { 
    mqttClient.on('message', async (topic, message) => {
        try { 
            if (topic.startsWith('alerts/')) {
                console.log(`New message on topic ${topic} - ${message}`);
                await handleAlertMessage(topic, message);
            }
            else { 
                throw `Unhandled topic: ${topic}`;
            }
        }
        catch(error) { 
            console.log(error);
        } 
    });
}


const handleAlertMessage = async (topic, message) => { 
    let mqttToken = topic.split('/')[1];
    let messageJson;
    try { 
        messageJson = JSON.parse(message);
    }
    catch(error) { 
        throw "Message from MQTT is not in JSON format."
    }
    if (messageJson.message == undefined) { 
        throw "Message from MQTT does not have 'message' property."
    }

    let userResult = await userManagementLogic.getIdByMqttToken(mqttToken);
    if (userResult.status != 200) { 
        throw userResult.details
    }
    let notification = { 
        userId: userResult.details,
        message: messageJson.message
    }
    await notificationLogic.add(notification);
}

export default { 
    startListening
}