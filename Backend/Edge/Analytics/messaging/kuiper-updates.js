import { mqttClient } from '../config/mqtt-config.js';
import deviceLogic from '../logic/device-logic.js';
import ruleLogic from '../logic/rule-logic.js';
import { runMqttMessageChecking } from '../utils/mqtt-message-validation.js';
import { regular as constants } from '../utils/constants.js';
import { edgeGatewayAxios } from '../config/axios-config.js';

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
    let actuator = await deviceLogic.getDeviceById(rule.actuatorId);
    if (actuator.state !== state) { 
        await Promise.all([
            deviceLogic.changeState(rule.actuatorId, state),
            edgeGatewayAxios.put(`/actuator/update/state`, JSON.stringify({
                deviceId: rule.actuatorId,
                state: state
            }))
        ])
        console.log(`Changed pump state for actuator ${actuator.name} to ${state}`);
    }
    else { 
        console.log(`Pump state for actuator ${actuator.name} is already set to ${state}`);

    }
}