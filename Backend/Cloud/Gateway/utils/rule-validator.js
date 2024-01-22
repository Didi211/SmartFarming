
const validateForAdd = (rule) => { 
    let result = "";
    try { 
        if (rule.userId == undefined || rule.userId == "") { 
            throw "Rule must have user ID."
        }
        if (rule.sensorId == undefined || rule.sensorId == "") { 
            throw "Rule must have sensor ID."
        }
        if (rule.actuatorId == undefined || rule.actuatorId == "") { 
            throw "Rule must have actuator ID."
        }
        if (rule.startTriggerLevel == undefined) { 
            throw "Rule start trigger level is required."
        }
        if (!Number.isInteger(rule.startTriggerLevel) ) { 
            throw "Rule start trigger level must be number."
        }
        if (rule.stopTriggerLevel == undefined) { 
            throw "Rule stop trigger level is required."
        }
        if (!Number.isInteger(rule.stopTriggerLevel) ) { 
            throw "Rule stop trigger level must be number."
        }
        if (rule.startExpression == undefined) { 
            throw "Rule start expression is required."
        }
        if (rule.stopExpression == undefined) { 
            throw "Rule stop start expression is required."
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
}

let validateLogic = (rule) => { 
    if (rule.startTriggerLevel <= 0 ) { 
        throw "Rule start trigger level must be larger than 0."
    }
    if (rule.stopTriggerLevel <= 0) { 
        throw "Rule stop trigger level must be larger than 0."
    }
    if (rule.startTriggerLevel > 100) { 
        throw "Rule start trigger level can be maximum 100."
    }
    if (rule.stopTriggerLevel > 100) { 
        throw "Rule stop trigger level can be maximum 100."
    }
    if (rule.startTriggerLevel == rule.stopTriggerLevel) { 
        throw "Start and stop value cannot be the same."
    }

    

}

const validateForUpdate = (id, rule) => { 
    let result = "";
    try { 
        if (rule.id == undefined || rule.id == "") { 
            throw "Rule must have sensor ID."
        }
        if (id != rule.id) { 
            throw "Rule ID cannot be updated."
        }
        result = validateForAdd(rule);
        if (result != "") { 
            throw result
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
}
export default { 
    validateForAdd,
    validateForUpdate
}