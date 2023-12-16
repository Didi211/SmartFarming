// receives alerts from edge 

import { mqttClient } from "../config/mqtt-config.js";
import deviceLogic from "../logic/device-logic.js";
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
    if (messageJson.metadata != undefined) {
        if (messageJson.metadata.deviceId == undefined) {
            throw "Message from MQTT does not have 'metadata.deviceId' property."
        } 
        if (messageJson.metadata.status == undefined) {
            throw "Message from MQTT does not have 'metadata.status' property."
        } 
    }
    else { 
        throw "Message from MQTT does not have 'metadata' property."
    }
    let userResult = await userManagementLogic.getIdByMqttToken(mqttToken);
    if (userResult.status != 200) { 
        throw userResult.details
    }
    let notification = { 
        userId: userResult.details,
        message: messageJson.message,
        deviceId: messageJson.metadata.deviceId,
        deviceStatus: messageJson.metadata.status
    }
    
    let notificationPromise = notificationLogic.add(notification);
    let devicePromise = deviceLogic.get(messageJson.metadata.deviceId);
    let userPromise = userManagementLogic.get(userResult.details);
    await Promise.all([notificationPromise, devicePromise, userPromise]);

    // call device management service and update device
    let userEmail = (await userPromise).details.email;
    let device = (await devicePromise).details;
    device.status = messageJson.metadata.status;
    await deviceLogic.update(device.id, device, userEmail);

}

export default { 
    startListening
}