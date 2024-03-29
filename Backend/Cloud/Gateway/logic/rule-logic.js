import { deviceManagementAxios } from "../config/axios-config.js";
import ruleValidator from "../utils/rule-validator.js";
import ruleMqtt from "../messaging/rule-mqtt.js";
import userManagementLogic from "./user-management-logic.js";

const getRuleById = async (id) => { 
    let response = await deviceManagementAxios.get(`/rule/${id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const getRuleFromDeviceId = async (id) => { 
    let response = await deviceManagementAxios.get(`/${id}/rule`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const getRulesForUser = async (id) => { 
    let response = await deviceManagementAxios.get(`/rule/user/${id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const add = async (rule, email) => { 
    let  validationResult = ruleValidator.validateForAdd(rule);
    if (validationResult != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: validationResult
        };
    }
    
    let response = await deviceManagementAxios.post(`/rule`, JSON.stringify(rule));
    if (response.status == 200) { 
        // propagate to the edge 
        let token = (await userManagementLogic.fetchMqttToken(email)).details;
        ruleMqtt.publishAddRule(token, JSON.parse(response.data).details);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const update = async (id, rule, email) => { 
    let result = ruleValidator.validateForUpdate(id, rule);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    let response = await deviceManagementAxios.put(`/rule/${id}`, JSON.stringify(rule));
    if (response.status == 200) { 
        // propagate to the edge 
        let token = (await userManagementLogic.fetchMqttToken(email)).details;
        ruleMqtt.publishUpdateRule(token, id, rule);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const remove = async (id, email) => { 
    let response = await deviceManagementAxios.delete(`/rule/${id}`);
    if (response.status == 200) { 
        // propagate the call to the edge 
        let token = (await userManagementLogic.fetchMqttToken(email)).details;
        ruleMqtt.publishRemoveRule(token, id);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

export default { 
    getRuleById,
    getRuleFromDeviceId,
    getRulesForUser,
    add,
    update,
    remove
}