import { Device } from '../models/device-model.js';
import { Rule } from '../models/rule-model.js';
import constants from '../utils/constants.js';
import dtoMapper from '../utils/dto-mapper.js';

const addDevice = async (device) => { 
    try { 
        let deviceModel = new Device(device);
        deviceModel._id = device.id;
        let result = await Device.create(deviceModel);
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
    
    let result = await Device.findByIdAndUpdate(id, {
        name: device.name,
        // status: device.status, // updating directly from edge
        unit: deviceDb.type == 'SENSOR' ? device.unit : null,
        // state: deviceDb.type == 'ACTUATOR' ? device.state : null
    });
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }
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

const changeState = async (id, state) => { 
    if (state.toUpperCase() != constants.ON && state.toUpperCase() != constants.OFF) { 
        throw { 
            status: 400,
            message: 'Invalid STATE value',
            details: `State [${state}] is not valid, only [${constants.ON},${constants.OFF}] are valid.`
        }
    }
    let actuator = await Device.findOne({_id: id, type: constants.ACTUATOR});
    if (!actuator) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database. It is not created or it is sensor.`
        }
    }
    await Device.findByIdAndUpdate(id, {
        state: state,
    });
}

const changeStatus = async (id, status) => { 
    if (status.toUpperCase() != constants.ONLINE && status.toUpperCase() != constants.OFFLINE) { 
        throw { 
            status: 400,
            message: 'Invalid STATUS value',
            details: `Status [${status}] is not valid, only [${constants.ONLINE},${constants.OFFLINE}] are valid.`
        }
    }
    let result = await Device.findByIdAndUpdate(id, {
        status: status,
    });
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }
}

export default { 
    addDevice,
    updateDevice,
    removeDevice,
    changeState,
    changeStatus
}