import { mqttClient } from '../config/mqtt-config.js';
import deviceLogic from '../logic/device-logic.js';
import ruleLogic from '../logic/rule-logic.js';
import { runMqttMessageChecking } from '../utils/mqtt-message-validation.js';
import { regular as constants } from '../utils/constants.js';

export const startListening = () => { 
    mqttClient.on('message', async (topic, message) => { 
        try { 
            if (topic === process.env.KUIPER_TOPIC) {
                // runMqttMessageChecking(message);
                console.log(`New message on topic ${topic} - ${message}`);
                await handleKuiperUpdateMessage(message);
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

const handleKuiperUpdateMessage = async (message) => { 
    let messageJson = JSON.parse(message)[0]; // kuiper sends an array
    let splits = messageJson.ruleId.split('-'); // "65874b0caee1a646941cefd5-START"
    let ruleId = splits[0]; 
    let state = splits[1] === 'START' 
        ? constants.ON
        : constants.OFF;

    let rule = await ruleLogic.getRule(ruleId);
    await deviceLogic.changeState(rule.actuatorId, state);
    console.log(`Changed pump state for rule ${rule.name} to ${state}`);
}