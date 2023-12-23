import deviceValidator from "../utils/device-validator.js";
import { deviceManagementAxios } from "../config/axios-config.js";
import userValidator from "../utils/user-validator.js";
import deviceMqtt from "../messaging/device-mqtt.js";
import userManagementLogic from "./user-management-logic.js";
import constants from "../utils/constants.js";

// add propagating to the edge 

const getAllDevices = async (userId, type) => { 
    await userValidator.isUserExisting(userId);
    let response = await deviceManagementAxios.get(`/user/${userId}?type=${type}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const get = async (id) => { 
    let response = await deviceManagementAxios.get(`/${id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const add = async (device, email) => { 
    let validationResult = deviceValidator.validate(device);
    if (validationResult != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: validationResult
        };
    }
    await userValidator.isUserExisting(device.userId);

    let response = await deviceManagementAxios.post(`/`, JSON.stringify(device));
    if (response.status == 200) { 
        // propagate the call to the edge 
        let token = (await userManagementLogic.fetchMqttToken(email)).details;
        deviceMqtt.publishAddDevice(token, JSON.parse(response.data).details);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const update = async (id, device, email, updateEdge = true) => { 
    let result = deviceValidator.validateForUpdate(id, device);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    await userValidator.isUserExisting(device.userId);

    let response = await deviceManagementAxios.put(`/${id}`, JSON.stringify(device));
    if (response.status == 200) { 
        if (updateEdge) { 
            // propagate the call to the edge 
            let token = (await userManagementLogic.fetchMqttToken(email)).details;
            deviceMqtt.publishUpdateDevice(token, id, device);
        }
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const updateState = async (id, state) => { 
    if (state !== constants.STATE_ON && state !== constants.STATE_OFF) { 
        throw `State must be one of the [${constants.STATE_ON}, ${constants.STATE_OFF}]`;
    }

    let response = await deviceManagementAxios.put(`/${id}/state`, JSON.stringify({
        state: state
    }));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}


const remove = async (id, email) => { 
    let response = await deviceManagementAxios.delete(`/${id}`);
    if (response.status == 200) { 
        // propagate the call to the edge 
        let token = (await userManagementLogic.fetchMqttToken(email)).details;
        deviceMqtt.publishRemoveDevice(token, id);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}



export default { 
    getAllDevices,
    get,
    add,
    update,
    updateState,
    remove
}