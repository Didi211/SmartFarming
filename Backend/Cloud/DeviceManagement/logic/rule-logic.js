import { Rule } from '../models/rule-model.js';
import dtoMapper from '../utils/dto-mapper.js';
import deviceLogic from './device-logic.js';


const getByDeviceId = async (deviceId) => { 
    let result = await Rule.findOne({
        $or: [
            {sensorId: deviceId},
            {actuatorId: deviceId}
        ]
    });
    if (!result) { 
        throw { 
            status: 400,
            message: "MongoDB error",
            details: `Rule for device with ID [${deviceId}] not found in database.`
        };
    }
    return dtoMapper.toRuleDto(result);
}

const addRule = async (rule) => { 
    let sensor, actuator;
    // check if device ids are from existing devices 
    try { 
         [sensor, actuator] = await Promise.all([
            deviceLogic.findById(rule.sensorId),
            deviceLogic.findById(rule.actuatorId)
        ]);
    }
    catch(error) { 
        throw {
            status: 400,
            message: "Invalid devices",
            details: `One or both devices are not created. Internal error: ${error.details}`
        }
    }

    if (rule.startTriggerLevel === rule.stopTriggerLevel) { 
        throw "START and STOP trigger level cannot have the same value.";
    }
        
    // check if devices are from the same tenant
    // add checking from jwt 
    if (sensor.userId != actuator.userId) { 
        throw {
            status: 400,
            message: "Invalid devices",
            details: `Devices are not belonging to the same user.`
        }
    }
    try { 
        let result = await Rule.create(rule);
        return dtoMapper.toRuleDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'MongoDB error',
            details: error
        };
    }
}

const updateRule = async (id, rule) => { 
    if (id != rule.id) { 
        throw { 
            status: 400,
            message: 'Forbidden update.',
            details: `Rule Id cannot be updated.`
        }
    }
    if (rule.startTriggerLevel === rule.stopTriggerLevel) { 
        throw "START and STOP trigger level cannot have the same value.";
    }
    let ruleModel = new Rule(rule);
    await ruleModel.validate();
    let result = await Rule.findByIdAndUpdate(id, {
        name: rule.name,
        startExpression: rule.startExpression,
        stopExpression: rule.stopExpression,
        startTriggerLevel: rule.startTriggerLevel,
        stopTriggerLevel: rule.stopTriggerLevel,
    });
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Rule with ID [${id}] not found in database.`
        }
    }
}

const removeRule = async (id) => { 
    try { 
        let result = await Rule.findByIdAndDelete(id);
        if (!result) { 
            throw `Rule with ID [${id}] not found in database.`;
        }
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'MongoDB error',
            details: error
        }
    }
}

export default { 
    getByDeviceId,
    addRule,
    updateRule,
    removeRule
}