import deviceValidator from "../utils/device-validator.js";
import { deviceManagementAxios } from "../axios-config.js";
import userValidator from "../utils/user-validator.js";

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

const add = async (device) => { 
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
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const update = async (id, device) => { 
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
        // propagate the call to the edge 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const remove = async (id) => { 
    let response = await deviceManagementAxios.delete(`/${id}`);
    if (response.status == 200) { 
        // propagate the call to the edge 
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
    remove
}