import { mqttCloudClient } from '../config/mqtt-config.js';
import {regular as constants} from '../utils/constants.js';
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
            await handleDeviceUpdates(action, messageJson.data);
            break;
        }
        case constants.rules: { 
            await handleRuleUpdates(action, messageJson.data);
            break;
        }
        default: { 
            throw "Unknown update type."
        }
    }
}

const handleDeviceUpdates = async (action, data) => { 
    // call action
    switch (action.toUpperCase()) {
        case constants.add: { 
            // call add action
            await analyticsLogic.addDevice(data.device);
            break;
        }
        case constants.update: { 
            // call update action
            await analyticsLogic.updateDevice(data.id, data.device);
            break;
        }
        case constants.remove: { 
            // call remove action
            await analyticsLogic.removeDevice(data.id);
            break;
        }
        default:
            throw "Unknown action type."
    }
}

const handleRuleUpdates = async (action, data) => { 
    // call action
    switch (action.toUpperCase()) {
        case constants.add: { 
            // call add action
            await analyticsLogic.addRule(data);
            break;
        }
        case constants.update: { 
            // call update action
            await analyticsLogic.updateRule(data.id, data.rule);
            break;
        }
        case constants.remove: { 
            // call remove action
            await analyticsLogic.removeRule(data);
            break;
        }
        default:
            throw "Unknown action type."

    }
}