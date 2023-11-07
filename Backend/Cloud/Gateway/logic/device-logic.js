import mockPersistanceService from "../services/mock/mock-persistance-service.js";
import deviceValidator from "../utils/device-validator.js";

const getAllDevices = async (type) => { 
    type = type.toUpperCase();
    return mockPersistanceService.getAllDevices(type);
}

const get = async (id) => { 
    return mockPersistanceService.getDevice(id);
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
    result = mockPersistanceService.addDevice(device);
    if (result) { 
        console.log("Updating edge system.")
        // propagate the call to the edge 
            // to analytics
            // to edgex
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
    result = await mockPersistanceService.updateDevice(device)
    if (result) { 
        // propagate call to the edge
                // to analytics
                // to edgex
    }
    return result;
}

const remove = async (id) => { 
    let result = await mockPersistanceService.removeDevice(id); // throws ex if object does not exist
    if (result) { 
        console.log('Updating edge system.');
        // propagate call to the edge
                    // to analytics
                    // to edgex
    }
}

export default { 
    getAllDevices,
    get,
    add,
    update,
    remove
}