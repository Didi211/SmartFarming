import { mqttCloudClient } from '../config/mqtt-config.js';
import constants from '../utils/constants.js';
import analyticsLogic from '../logic/analytics-logic.js';

export const startListening = () => { 
    mqttCloudClient.on('message', async (topic, message) => { 
        try { 
            if (topic.startsWith(`${process.env.MQTT_TOPIC_CLOUD_SUB}/`)) {
                console.log(`New message on topic ${topic} - ${message}`);
                await handleCloudUpdateMessage(topic, message);
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

const handleCloudUpdateMessage = async (topic, message) => { 
    let splits = topic.split('/');
    let updateType = splits[2]; // edge/token/(rule or device)/crud
    let action = splits[3];
    let messageJson;
    try { 
        messageJson = JSON.parse(message);
    }
    catch(error) { 
        throw "Message from MQTT is not in JSON format."
    }
    // if (messageJson.reading == undefined) { 
    //     throw "Message from MQTT does not have 'reading' property."
    // }
    switch(updateType.toUpperCase()) { 
        case constants.devices: { 
            handleDeviceUpdates(action, messageJson);
            break;
        }
        case constants.rules: { 
            handleRuleUpdates(action, messageJson);
            break;
        }
        default: { 
            throw "Unknown update type."
        }
    }
}

const handleDeviceUpdates = async (action, message) => { 
    // call action
    switch (action.toUpperCase()) {
        case constants.add: { 
            // call add action
            await analyticsLogic.addDevice(message);
            break;
        }
        case constants.update: { 
            // call update action
            await analyticsLogic.updateDevice(message);
            break;
        }
        case constants.remove: { 
            // call remove action
            await analyticsLogic.removeDevice(message);
            break;
        }
        default:
            throw "Unknown action type."
    }
}

const handleRuleUpdates = async (action, message) => { 
    // call action
    switch (action.toUpperCase()) {
        case constants.add: { 
            // call add action
            await analyticsLogic.addRule(message);
            break;
        }
        case constants.update: { 
            // call update action
            await analyticsLogic.updateRule(message);
            break;
        }
        case constants.remove: { 
            // call remove action
            await analyticsLogic.removeRule(message);
            break;
        }
        default:
            throw "Unknown action type."

    }
}