import { Device } from '../models/device-model.js';
import { Rule } from '../models/rule-model.js';
import dtoMapper from '../utils/dto-mapper.js';

const getAll = async (type, userId) => {
    if (type === undefined || type === null || type.toLowerCase() === 'undefined') {
        // get all 
        try { 
            let result = await Device.find({userId: userId});
            return dtoMapper.toDevicesDto(result);
        }
        catch(error) { 
            throw { 
                status: 500,
                message: 'MongoDB error',
                details: error
            };
        }
    }
    type = type.toUpperCase(); 
    if (type != 'SENSOR' && type != 'ACTUATOR') { 
        throw { 
            status: 400,
            message: 'Bad device type.',
            details: `${type} is not allowed. Only 'SENSOR' or 'ACTUATOR' are allowed.`
        }
    }
    // user validation done in gateway service
    try { 
        let result = await Device.find({userId: userId, type: type});
        return dtoMapper.toDevicesDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'MongoDB error',
            details: error
        };
    }
}

const findById = async (id) => { 
    try { 
        let result = await Device.findById(id);
        if (!result) { 
            throw { 
                status: 400,
                message: "MongoDB error",
                details: `Device with ID [${id}] not found in database.`
            }
        }
        return dtoMapper.toDeviceDto(result);
    }
    catch(error) { 
        throw { 
            status: error.status ?? 500,
            message: error.message ?? 'MongoDB error',
            details: error.details ?? error
        };
    }
}

const addDevice = async (device) => { 
    try { 
        let result = await Device.create(device);
        return dtoMapper.toDeviceDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'MongoDB error',
            details: error
        };
    }
}

const updateDevice = async (id, device) => { 
    if (id != device.id) { 
        throw { 
            status: 400,
            message: 'Forbidden update.',
            details: `Device Id cannot be updated.`
        }
    }
    let deviceModel = new Device(device);
    await deviceModel.validate();

    let deviceDb = await Device.findById(id);
    if (!deviceDb) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }
    if (deviceDb.name !== device.name) { 
        throw { 
            status: 400,
            message: 'Forbidden update.',
            details: `Device name cannot be updated.`
        }
    }
    deviceDb.status = device.status;
    deviceDb.unit = deviceDb.type == 'SENSOR' ? device.unit : null;
    // deviceDb.state = deviceDb.type == 'ACTUATOR' ? device.state : null
    deviceDb.markModified();
    await deviceDb.save();
}

const updateDeviceState = async (id, state) => { 
    let device = await Device.findById(id);
    device.state = state;
    await device.save();
}

const removeDevice = async (id) => { 
    let hasRule = await Rule.findOne({
        $or: [
            {sensorId: id},
            {actuatorId: id}
        ]
    });
    if (hasRule) { 
        throw { 
            status: 400,
            message: "MongoDB error",
            details: `Device with ID [${id}] has a rule. Delete rule first then device.`
        };
    }
    try { 
        let result = await Device.findByIdAndDelete(id);
        if (!result) { 
            throw "Device is already deleted."
        }
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
    addDevice,
    getAll,
    updateDevice,
    updateDeviceState,
    removeDevice,
    findById
}