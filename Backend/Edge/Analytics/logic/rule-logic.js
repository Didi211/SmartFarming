import { Rule } from '../models/rule-model.js';
import { Device } from '../models/device-model.js'
import dtoMapper from '../utils/dto-mapper.js';
import { deviceSimulatorAxios } from '../config/axios-config.js';
import { regular as constants } from '../utils/constants.js'; 
import edgexLogic from './edgex-logic.js';

const addRule = async (rule) => { 
    try { 
        let ruleModel = new Rule(rule);
        ruleModel._id = rule.id;
        let ids = [ruleModel.sensorId, ruleModel.actuatorId]
        let devices = await Device.find({_id: { $in: ids}});
        if (devices.length !== 2) { 
            throw "One or both device for rule are not found in database."
        }
        let result = await Rule.create(ruleModel);
        let actuator = devices.find(x => x.type === constants.ACTUATOR);
        await edgexLogic.addRule(rule, actuator.name);

        if (process.env.DEVICE_SIMULATOR_ENABLED) { 
            // add actuator name in simulator
            let sensor = devices.find(device => device.type === constants.SENSOR);
            let actuator = devices.find(device => device.type === constants.ACTUATOR);
            await deviceSimulatorAxios.post(`/add/actuator`, JSON.stringify({
                id: sensor._id,
                actuatorName: actuator.name,
                pumpState: actuator.state === constants.ON 
                    ? true : false
            }));

        }
        return dtoMapper.toRuleDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'Error while adding Rule',
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
    
    await edgexLogic.updateRule(rule);
}

const removeRule = async (id) => { 
    try { 
        let removedRule = await Rule.findByIdAndDelete(id);
        
        await edgexLogic.removeRule(id);

        if (process.env.DEVICE_SIMULATOR_ENABLED) { 
            let actuator = await Device.findById(removedRule.actuatorId);
            let simulatorResult = await deviceSimulatorAxios.delete(`/remove/actuator/${actuator.name}`);
            if (simulatorResult.status !== 200) { 
                throw simulatorResult.data
            }
        }
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'Error while Removing Rule',
            details: error
        }
    }
}

export default { 
    addRule,
    updateRule,
    removeRule
}