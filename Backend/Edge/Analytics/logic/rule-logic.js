import { Rule } from '../models/rule-model.js';
import dtoMapper from '../utils/dto-mapper.js';

const addRule = async (rule) => { 
    try { 
        let ruleModel = new Rule(rule);
        ruleModel._id = rule.id;
        let result = await Rule.create(ruleModel);
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
        await Rule.findByIdAndDelete(id);
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
    addRule,
    updateRule,
    removeRule
}