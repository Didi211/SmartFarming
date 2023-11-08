import edgeGatewayService from "../services/edge-gateway-service.js";
import mockPersistanceService from "../services/mock/mock-persistance-service.js";
import persistenceService from "../services/persistence-service.js";
import deviceValidator from "../utils/device-validator.js";

const getAllDevices = async (type) => { 
    type = type?.toUpperCase();
    return persistenceService.getDevices(type);
}

const get = async (id) => { 
    return persistenceService.getDevice(id);
}

const add = async (device) => { 
    let result = deviceValidator.validate(device);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    result = persistenceService.addDevice(device);
    if (result) { 
        // propagate the call to the edge 
        edgeGatewayService.addDevice(result);
    }   
    return result;
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
    result = await persistenceService.updateDevice(device);
    // other services will handle error and throw exceptions if necesesary
    if (result) { 
        // propagate call to the edge
        edgeGatewayService.updateDevice(result);
    }
    return result;
}

const remove = async (id) => { 
    let result = await persistenceService.removeDevice(id); // throws ex if object does not exist
    if (result) { 
        // propagate call to the edge
        edgeGatewayService.removeDevice(id);
    }
}

export default { 
    getAllDevices,
    get,
    add,
    update,
    remove
}