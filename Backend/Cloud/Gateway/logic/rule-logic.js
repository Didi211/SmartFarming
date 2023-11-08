import edgeGatewayService from "../services/edge-gateway-service.js";
import mockPersistanceService from "../services/mock/mock-persistance-service.js"
import persistenceService from "../services/persistence-service.js";
import ruleValidator from "../utils/rule-validator.js";

const getRuleFromDeviceId = async (id) => { 
    return persistenceService.getRuleFromDeviceId(id);
}

const add = async (rule) => { 
    let  result = ruleValidator.validateForAdd(rule);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    result = await persistenceService.addRule(rule);
    if (result) { 
        edgeGatewayService.addRule(result);
    }
    return result;
}

const update = async (id, rule) => { 
    // update rule - only trigger level or name can be updated
    // cannot reconnect different devices 
    // will be checked on persistence service

    let result = ruleValidator.validateForUpdate(id, rule);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    result = await persistenceService.updateRule(rule)
    if (result) { 
        // propagate call to the edge
        edgeGatewayService.updateRule(result);
    }
    return result;
}

const remove = async (id) => { 
    let result = await persistenceService.removeRule(id); // throws ex if object does not exist
    if (result) { 
        // propagate call to the edge
        edgeGatewayService.removeRule(id);
    }
}

export default { 
    getRuleFromDeviceId,
    add,
    update,
    remove
}