import mockPersistanceService from "../services/mock/mock-persistance-service.js"
import ruleValidator from "../utils/rule-validator.js";

const getRuleFromDeviceId = async (id) => { 
    return mockPersistanceService.getRuleFromDeviceId(id);
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
    result = await mockPersistanceService.addRule(rule);
    if (result) { 
        console.log("Updating edge system.")
        // propagate the call to the edge 
            // to analytics
            // to edgex
    }
    return result;
}

const update = async (id, rule) => { 

    // update rule - only trigger level or name can be updated
    // cannot reconnect different devices 
    // prapagate call to the edge
        // to analytics

    let result = ruleValidator.validateForUpdate(id, rule);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    result = await mockPersistanceService.updateRule(rule)
    if (result) { 
        // propagate call to the edge
                // to analytics
                // to edgex
    }
    return result;
}

const remove = async (id) => { 
    let result = await mockPersistanceService.removeRule(id); // throws ex if object does not exist
    if (result) { 
        console.log('Updating edge system.');
        // propagate call to the edge
                    // to analytics
                    // to edgex
    }
}

export default { 
    getRuleFromDeviceId,
    add,
    update,
    remove
}