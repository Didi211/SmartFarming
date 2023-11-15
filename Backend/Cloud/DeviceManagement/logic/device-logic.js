import { Device } from '../models/device-model.js';
import dtoMapper from '../utils/dto-mapper.js';

const getAll = async (type, userId) => { 
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
    let result = await Device.findByIdAndUpdate(id, {
        name: device.name,
        status: device.status,
        unit: device.unit ?? null,
        state: device.state ?? null
    });
    console.log(result);
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }
}

const removeDevice = async (id) => { 
    try { 
        await Device.findByIdAndDelete(id);
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
    removeDevice,
    findById
}