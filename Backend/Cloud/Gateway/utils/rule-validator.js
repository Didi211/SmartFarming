
const validateForAdd = (rule) => { 
    let result = "";
    try { 
        if (rule.sensorId == undefined || rule.sensorId == "") { 
            throw "Rule must have sensor ID."
        }
        if (rule.actuatorId == undefined || rule.actuatorId == "") { 
            throw "Rule must have actuator ID."
        }
        if (rule.action == undefined) { 
            throw "Rule action is required."
        }
        let actionUpperCase = rule.action.toUpperCase();
        if (actionUpperCase != 'START' && actionUpperCase != 'STOP') { 
            throw "Rule action must be either START or STOP."
        }
        if (rule.triggerLevel == undefined) { 
            throw "Rule trigger level is required."
        }
        if (!Number.isInteger(rule.triggerLevel) ) { 
            throw "Rule trigger level must be number."
        }
        if (rule.expression == undefined) { 
            throw "Rule expression is required."
        }
        if (rule.expression != '>' && rule.expression != '<') { 
            throw "Rule expression must be either '>' or '<'."
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
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